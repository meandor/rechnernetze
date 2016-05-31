package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.IControllerService;
import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.model.LoginMessage;
import de.haw.rnp.chat.networkmanager.model.LogoutMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Queue;

public class IncomingChatProtocolMessageHandler implements IncomingMessageHandler {

    private IControllerService controller;

    public IncomingChatProtocolMessageHandler(IControllerService controller) {
        this.controller = controller;
    }

    @Override
    public void processMessage(byte[] byteStream) {
        //parse message and forward to the proper function
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
            }
        }
    }

    @Override
    public void setUserName(InetAddress hostName, int port, String userName) {
        User u = this.findUser(hostName, port);
        if (u != null) {
            u.setName(userName);
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
