package de.haw.rnp.chat.client.networkmanager;

import de.haw.rnp.chat.client.controller.IControllerService;
import de.haw.rnp.chat.client.model.Message;
import de.haw.rnp.chat.client.model.User;
import de.haw.rnp.chat.client.networkmanager.model.*;
import de.haw.rnp.chat.client.networkmanager.tasks.ClientCloseTask;
import de.haw.rnp.chat.client.networkmanager.tasks.ClientStartTask;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Handles the outgoing messages between two peers.
 *
 * @author Daniel Schruhl <danielschruhl@gmail.com>
 */
public class OutgoingChatProtocolMessageHandler implements OutgoingMessageHandler {

    private NodeFactory factory;
    private ExecutorService executor;

    public OutgoingChatProtocolMessageHandler(IControllerService controller) {
        this.factory = new TCPNodeFactory();
        this.executor = controller.getExecutor();
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

        // Establish Connection to the active peer
        Node clientNode = this.initialConnect(activePeerHostName, activePeerPort);
        LoginMessage loginMessage = new LoginMessage(loginHostName, loginPort, loginName, loginHostName, loginPort);
        try {
            // Sending the message to the active peer
            clientNode.getOut().write(loginMessage.getFullMessage());
            ClientCloseTask closeClient = new ClientCloseTask(clientNode);
            // Closing the connection to the active peer
            Future<Boolean> clientClosed = this.executor.submit(closeClient);
            if (clientClosed.get()) {
                User user = new User(loginName, loginPort, loginHostName);
                user.setServerNode(serverNode);
                return user;
            }
            return null;
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void logout(InetAddress activePeerHostName, int activePeerPort, InetAddress logoutHostName, int logoutPort) {
        // Establish Connection to the active peer
        //Node clientNode = this.initialConnect(activePeerHostName, activePeerPort);
        LogoutMessage logoutMessage = new LogoutMessage(logoutHostName, logoutPort, logoutHostName, logoutPort);
        this.sendChatProtocolMessage(logoutMessage, activePeerHostName, activePeerPort);
    }

    @Override
    public void sendMessage(Message message) {
        InetAddress senderHostName = message.getSender().getHostName();
        int senderPort = message.getSender().getPort();
        TextMessage textMessage = new TextMessage(senderHostName, senderPort, message.getText(), message.getReceiver());
        for (User u : message.getReceiver()) {
            InetAddress activePeerHostName = u.getHostName();
            int activePeerPort = u.getPort();
            //Node clientNode = this.initialConnect(activePeerHostName, activePeerPort);
            this.sendChatProtocolMessage(textMessage, activePeerHostName, activePeerPort);
        }
    }

    @Override
    public void sendName(List<User> recipients, String name, InetAddress nameHostName, int namePort) {
        MyNameMessage myNameMessage = new MyNameMessage(nameHostName, namePort, name);
        for (User u : recipients) {
            InetAddress activePeerHostName = u.getHostName();
            int activePeerPort = u.getPort();
            // Establish Connection to the active peer
            //Node clientNode = this.initialConnect(activePeerHostName, activePeerPort);
            this.sendChatProtocolMessage(myNameMessage, activePeerHostName, activePeerPort);
        }
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

    /**
     * Actually sending a message via client Socket
     *
     * @param message  ChatProtocolMessage to be send
     */
    private void sendChatProtocolMessage(ChatProtocolMessage message, InetAddress activePeerHostName, int activePeerPort) {
        Node receiver = this.initialConnect(activePeerHostName, activePeerPort);
        if (receiver != null) {
            try {
                System.out.println("SENDING MESSAGE::: " + message.toString());
                receiver.getOut().write(message.getFullMessage());
                //ClientCloseTask closeClient = new ClientCloseTask(receiver);
                // Closing the connection to the active peer
                //this.executor.submit(closeClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}