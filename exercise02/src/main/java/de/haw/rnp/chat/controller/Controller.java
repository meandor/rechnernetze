package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.Node;
import de.haw.rnp.chat.networkmanager.OutgoingChatProtocolMessageHandler;
import de.haw.rnp.chat.networkmanager.OutgoingMessageHandler;
import de.haw.rnp.chat.networkmanager.tasks.GeneralTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerAwaitConnectionsTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerCloseTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerStartTask;
import de.haw.rnp.chat.view.IView;
import de.haw.rnp.chat.view.ViewController;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class Controller implements IControllerService {
    private BlockingQueue messageQueue;
    private User loggedInUser;
    private Node server;
    private ServerAwaitConnectionsTask waitingConnection;
    private List<User> userList;
    private IView view;
    private OutgoingMessageHandler outgoingMessageHandler;

    public Controller(Stage stage) {

        this.messageQueue = new LinkedBlockingQueue();
        this.userList = new ArrayList<>();
        this.view = new ViewController(stage, this);
        this.outgoingMessageHandler = new OutgoingChatProtocolMessageHandler(this);

        stage.setOnCloseRequest(t -> {
            ServerCloseTask closeTask = new ServerCloseTask(this.server);
            Future<Boolean> closed = this.outgoingMessageHandler.getExecutor().submit(closeTask);
            try {
                if (closed.get()) {
                    this.outgoingMessageHandler.getExecutor().shutdownNow();
                    Platform.exit();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public BlockingQueue getMessageQueue() {
        return messageQueue;
    }

    @Override
    public boolean addMessageToQueue(Message message) {
        return this.messageQueue.add(message);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public String login(String userName, InetAddress address, InetAddress localAddress, int port, int localport) {
        this.loggedInUser = this.outgoingMessageHandler.login(address, port, userName, localAddress, localport);
        if (this.loggedInUser == null) {
            return "";
        }
        return userName;
    }

    @Override
    public void logout() {
        InetAddress activePeerHostName = userList.get(0).getHostName();
        int activePeerPort = userList.get(0).getPort();
        outgoingMessageHandler.logout(activePeerHostName, activePeerPort, loggedInUser.getHostName(), loggedInUser.getPort());
    }

    @Override
    public boolean sendMessage(String recipient, String message) {
        User user = getUserByName(recipient);
        if (user == null) {
            return false;
        }
        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        Message ms = new Message(message, loggedInUser, userList);
        outgoingMessageHandler.sendMessage(ms);
        return true;
    }

    @Override
    public boolean startServer(InetAddress hostName, int port) {
        this.server = this.outgoingMessageHandler.getFactory().createNode(hostName, port);
        ServerStartTask startServerTask = new ServerStartTask(server);
        Future<Boolean> serverStarted = this.outgoingMessageHandler.getExecutor().submit(startServerTask);
        try {
            if (serverStarted.get()) {
                waitingConnection = new ServerAwaitConnectionsTask(server);
                this.outgoingMessageHandler.getExecutor().execute(waitingConnection);
                return true;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User getUserByName(String userName) {
        for (User user : userList) {
            if (user.getName().equals(userName))
                return user;
        }
        return null;
    }
}
