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
 * Handles the outgoing messages between two peers
 *
 * @author Daniel Schruhl <danielschruhl@gmail.com>
 */
public class OutgoingChatProtocolMessageHandler implements OutgoingMessageHandler {

    private NodeFactory factory;
    private ExecutorService executor;

    public OutgoingChatProtocolMessageHandler(Controller controller) {
        this.factory = new TCPNodeFactory();
        this.executor = Executors.newCachedThreadPool();
    }

    public NodeFactory getFactory() {
        return factory;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public User login(InetAddress activePeerHostName, int activePeerPort, String loginName, InetAddress loginHostName, int loginPort) {
        // Create a server node for the user
        Node serverNode = this.factory.createNode(loginHostName, loginPort);
        // Start the server node
        ServerStartTask task = new ServerStartTask(serverNode);
        Future<Boolean> serverStarted = this.executor.submit(task);
        try {
            if (serverStarted.get()) {
                ServerAwaitConnectionsTask task2 = new ServerAwaitConnectionsTask(serverNode);
                this.executor.execute(task2);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            //TODO: Server stop task
            return null;
        }

        // Establish Connection to the active Peer
        Node clientNode = this.initialConnect(activePeerHostName, activePeerPort);
        LoginMessage loginMessage = new LoginMessage(loginHostName, loginPort, loginName, loginHostName, loginPort);
        try {
            clientNode.getOut().write(loginMessage.getFullMessage());
            ClientCloseTask closeClient = new ClientCloseTask(clientNode);
            this.executor.execute(closeClient);
            User user = new User(loginName, loginPort, loginHostName);
            user.setServerNode(serverNode);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void logout(InetAddress activePeerHostName, int activePeerPort, InetAddress logoutHostName, int logoutPort) {

    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void sendName(InetAddress activePeerHostName, int activePeerPort, String name, InetAddress nameHostName, int namePort) {

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
