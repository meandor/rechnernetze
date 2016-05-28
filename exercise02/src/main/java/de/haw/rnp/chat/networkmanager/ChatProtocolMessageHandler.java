package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.Controller;
import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.tasks.ClientStartTask;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handles all protocol messages that are forwarded between the peers.
 *
 * @author Daniel Schruhl <danielschruhl@gmail.com>
 */
public class ChatProtocolMessageHandler implements MessageHandler {

    private Controller controller;
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
    public User login(String name, InetAddress hostName, int port) {
        return null;
    }

    @Override
    public void logout(User user) {

    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void receiveMessage(Message message) {
        this.controller.addMessage(message);
    }

    @Override
    public void sendName(User user, List recipient) {

    }

    @Override
    public void changeName(String name, InetAddress hostName, int port) {

    }

    @Override
    public User initialConnect(InetAddress hostName, int port) {
        User user = null;
        try {
            if (hostName.isReachable(2000)) {
                Node node = this.factory.createNode(hostName, port);
                ClientStartTask task = new ClientStartTask(node);
                this.executor.execute(task);
                user = new User("", node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
