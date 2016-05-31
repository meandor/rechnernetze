package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.Controller;
import de.haw.rnp.chat.controller.IControllerService;
import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.model.LoginMessage;
import de.haw.rnp.chat.networkmanager.model.LogoutMessage;
import de.haw.rnp.chat.networkmanager.tasks.ClientCloseTask;
import de.haw.rnp.chat.networkmanager.tasks.ClientStartTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerAwaitConnectionsTask;
import de.haw.rnp.chat.networkmanager.tasks.ServerStartTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Handles all protocol messages that are forwarded between the peers.
 *
 * @author Daniel Schruhl <danielschruhl@gmail.com>
 */
public class ChatProtocolMessageHandler implements MessageHandler {

    private IControllerService controller;
    private TCPNodeFactory factory;
    private ExecutorService executor;

    public ChatProtocolMessageHandler(Controller controller) {
        this.controller = controller;
        this.factory = new TCPNodeFactory();
        this.executor = Executors.newCachedThreadPool();
    }

    public TCPNodeFactory getFactory() {
        return factory;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public void processMessage(byte[] protocolMessage) {
        byte version = protocolMessage[0];
        byte messageType = protocolMessage[1];

        /*if (messageType == 0x01) {
            String name = "";
            int port = 0;
            String hostname = "";
            InetAddress hostName = new Inet4Address(hostname);
            this.controller.setLoggedInUser(this.login(name, hostName, port));
        }*/
    }

    @Override
    public User login(Node clientNode, String loginName, InetAddress loginHostName, int loginPort) {
        User user = new User(loginName, clientNode, loginHostName, loginPort); //clientnode: my connection to the other peer
        Node serverNode = null; //me
        try {
            serverNode = this.factory.createNode(InetAddress.getLocalHost(), loginPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        user.setServerNode(serverNode);
        ServerStartTask task = new ServerStartTask(serverNode);
        Future<Boolean> serverStarted = this.executor.submit(task);
        try {
            if (serverStarted.get()) {
                ServerAwaitConnectionsTask task2 = new ServerAwaitConnectionsTask(serverNode);
                this.executor.execute(task2);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        LoginMessage loginMessage = new LoginMessage(serverNode.getHostName(), loginPort, loginName, serverNode.getHostName(), loginPort);
        try {
            user.getClientNode().getOut().write(loginMessage.getFullMessage());
            ClientCloseTask closeClient = new ClientCloseTask(user.getClientNode());
            this.executor.execute(closeClient);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void logout(User logoutUser, User recipient) {
        InetAddress hostName = logoutUser.getHostName();
        int port = logoutUser.getPort();
        LogoutMessage logoutMessage = new LogoutMessage(hostName, port, hostName, port);
        logoutUser.setClientNode(this.initialConnect(recipient.getHostName(), recipient.getPort()));
        try {
            logoutUser.getClientNode().getOut().write(logoutMessage.getFullMessage());
            ClientCloseTask task = new ClientCloseTask(logoutUser.getClientNode());
            this.executor.execute(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void receiveMessage(Message message) {
        this.controller.addMessageToQueue(message);
    }

    @Override
    public void sendName(User user, List recipient) {

    }

    @Override
    public void changeName(String name, InetAddress hostName, int port) {

    }

    @Override
    public Node initialConnect(InetAddress hostName, int port) {
        Node clientNode = null;
        try {
            clientNode = this.factory.createNode(hostName, port);
            ClientStartTask task = new ClientStartTask(clientNode);
            Future<Boolean> clientStarted = this.executor.submit(task);
            if (clientStarted.get()) {
                return clientNode;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return clientNode;
    }
}
