package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.Node;
import de.haw.rnp.chat.networkmanager.OutgoingChatProtocolMessageHandler;
import de.haw.rnp.chat.networkmanager.OutgoingMessageHandler;
import de.haw.rnp.chat.networkmanager.tasks.ServerAwaitConnectionsTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerCloseTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerReadTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerStartTask;
import de.haw.rnp.chat.view.IView;
import de.haw.rnp.chat.view.ViewController;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * This class represents the Controller of the mvc pattern. The controller is a singleton and has a thread pool.
 */
public class Controller implements IControllerService {

    private User loggedInUser;
    private Node server;
    private IView view;
    private ExecutorService executor;
    private ServerAwaitConnectionsTask waitingConnection;
    private BlockingQueue<User> userList;
    private BlockingQueue<Message> messageQueue;
    static private Controller controller;
    private OutgoingMessageHandler outgoingMessageHandler;

    static public Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    private Controller() {
        this.messageQueue = new LinkedBlockingQueue<>();
        this.userList = new LinkedBlockingQueue<>();
        /*try {
            User u = new User("asd", 8080, InetAddress.getByName("10.0.0.1"));
            this.userList.add(u);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
        this.executor = Executors.newCachedThreadPool();
        this.outgoingMessageHandler = new OutgoingChatProtocolMessageHandler(this);
    }

    /**
     * Starts the view
     *
     * @param stage Stage for the view (JavaFx)
     */
    public void startView(Stage stage) {
        this.view = new ViewController(stage, this);
        stage.setOnCloseRequest(t -> {
            if (server != null) {
                ServerCloseTask closeTask = new ServerCloseTask(this.server);
                Future<Boolean> closed = this.outgoingMessageHandler.getExecutor().submit(closeTask);
                this.waitingConnection.stop();
                this.executor.shutdownNow();
            }
            Platform.exit();
        });
    }

    @Override
    public boolean addMessageToQueue(Message message) {
        if (this.messageQueue.offer(message)) {
            this.view.appendMessage(message.getSender().getName(), message.getText());
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(User u) {
        return userList.offer(u);
    }

    @Override
    public boolean removeUser(User u) {
        return userList.remove(u);
    }

    @Override
    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public boolean login(String userName, InetAddress address, InetAddress localAddress, int port, int localport) {
        this.loggedInUser = this.outgoingMessageHandler.login(address, port, userName, localAddress, localport);
        this.userList.offer(this.loggedInUser);
        return (this.loggedInUser != null);
    }

    @Override
    public void logout() {
        User activePeer = null;
        try {
            activePeer = userList.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (activePeer != null) {
            InetAddress activePeerHostName = activePeer.getHostName();
            int activePeerPort = activePeer.getPort();
            outgoingMessageHandler.logout(activePeerHostName, activePeerPort, this.loggedInUser.getHostName(), this.loggedInUser.getPort());
        }
    }

    @Override
    public boolean sendMessage(String recipient, String message) {
        ArrayList<User> usersList = new ArrayList<>();
        String[] recipients = recipient.split(",");
        for (String u : recipients) {
            User user = getUserByName(u);
            if (user != null) {
                usersList.add(user);
            }
        }
        if (usersList.size() > 0) {
            Message ms = new Message(message, loggedInUser, usersList);
            outgoingMessageHandler.sendMessage(ms);
            return true;
        }
        return false;
    }

    @Override
    public boolean startServer(InetAddress hostName, int port) {
        this.server = this.outgoingMessageHandler.getFactory().createNode(hostName, port);
        ServerStartTask startServerTask = new ServerStartTask(server, this);
        Future<Boolean> serverStarted = this.executor.submit(startServerTask);
        try {
            if (serverStarted.get()) {
                ServerReadTask readTask = new ServerReadTask(server);
                this.executor.submit(readTask);
                this.waitingConnection = new ServerAwaitConnectionsTask(server);
                this.executor.execute(this.waitingConnection);
                return true;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public BlockingQueue<User> getUserList() {
        return userList;
    }

    public BlockingQueue getMessageQueue() {
        return messageQueue;
    }

    /**
     * Finds the user by the given username
     *
     * @param userName String username of the user
     * @return User if any found that matches the username
     */
    private User getUserByName(String userName) {
        for (User user : userList) {
            if (user.getName().equals(userName))
                return user;
        }
        return null;
    }
}
