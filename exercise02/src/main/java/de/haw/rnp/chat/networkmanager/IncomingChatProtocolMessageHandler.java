package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.IControllerService;
import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.model.LoginMessage;
import de.haw.rnp.chat.networkmanager.model.LogoutMessage;
import de.haw.rnp.chat.networkmanager.model.MyNameMessage;
import de.haw.rnp.chat.networkmanager.model.ProtocolMessage;
import util.MessageType;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IncomingChatProtocolMessageHandler implements IncomingMessageHandler {

    private IControllerService controller;

    public IncomingChatProtocolMessageHandler(IControllerService controller) {
        this.controller = controller;
    }

    @Override
    public void processMessage(byte[] byteStream) {
        ProtocolMessage message = new ProtocolMessage(byteStream);
        switch (message.getMessageType()) {
            case Login:
                this.login(message.getSenderIp(), message.getSenderPort(), message.getFieldUsername());
                break;
            case Logout:
                this.logout(message.getSenderIp(), message.getSenderPort());
                break;
            case TextMessage:
                User sender = findUser(message.getSenderIp(), message.getSenderPort());
                List<User> users = new ArrayList<>();
                HashMap<InetAddress, Integer> map = message.byteArrayToUserList();
                for (Map.Entry<InetAddress, Integer> pair : map.entrySet()) {
                    users.add(findUser(pair.getKey(), pair.getValue()));
                }
                this.receiveMessage(message.getFieldText(), sender, users);
                break;
            case MyName:
                this.setUserName(message.getSenderIp(), message.getSenderPort(), message.getFieldUsername());
                break;
        }
    }

    @Override
    public void login(InetAddress hostName, int port, String userName) {
        User u = this.findUser(hostName, port);
        if (u == null) {
            u = new User(userName, port, hostName);
            this.controller.addUser(u);
            User loggedInUser = this.controller.getLoggedInUser();
            if (loggedInUser != null) {
                LoginMessage message = new LoginMessage(loggedInUser.getHostName(), loggedInUser.getPort(), userName, hostName, port);
                this.propagate(message.getFullMessage(), controller.getUserList());
                MyNameMessage myNameMessage = new MyNameMessage(loggedInUser.getHostName(), loggedInUser.getPort(), userName);
                BlockingQueue<User> receiver = new LinkedBlockingQueue<>();
                receiver.offer(u);
                this.propagate(myNameMessage.getFullMessage(), receiver);

            }
        }
    }

    @Override
    public void setUserName(InetAddress hostName, int port, String userName) {
        User u = this.findUser(hostName, port);
        if (u != null) {
            u.setName(userName);
        } else {
            u = new User(userName, port, hostName);
            this.controller.addUser(u);
        }
    }

    @Override
    public void logout(InetAddress hostName, int port) {
        User u = this.findUser(hostName, port);
        if (u != null) {
            this.controller.removeUser(u);
            User loggedInUser = this.controller.getLoggedInUser();
            if (loggedInUser != null) {
                LogoutMessage message = new LogoutMessage(loggedInUser.getHostName(), loggedInUser.getPort(), hostName, port);
                this.propagate(message.getFullMessage(), controller.getUserList());
            }
        }
    }

    @Override
    public void propagate(byte[] message, Queue<User> users) {
        OutgoingMessageHandler handler = new OutgoingChatProtocolMessageHandler(this.controller);
        for (User u : users) {
            Node node = handler.initialConnect(u.getHostName(), u.getPort());
            try {
                node.getOut().write(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void receiveMessage(String text, User sender, List<User> users) {
        Message m = new Message(text, sender, users);
        this.controller.addMessageToQueue(m);
    }

    /**
     * Returns a User if one can be found with the given parameters.
     *
     * @param hostName InetAddress of the User
     * @param port     int port number of the user
     * @return User if one is found or null
     */
    private User findUser(InetAddress hostName, int port) {
        for (User u : this.controller.getUserList()) {
            if (u.getPort() == port && u.getHostName() == hostName) {
                return u;
            }
        }
        return null;
    }
}
