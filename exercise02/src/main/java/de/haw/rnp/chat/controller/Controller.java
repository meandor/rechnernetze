package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.OutgoingChatProtocolMessageHandler;
import de.haw.rnp.chat.networkmanager.OutgoingMessageHandler;
import de.haw.rnp.chat.view.IView;
import de.haw.rnp.chat.view.ViewController;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Controller implements IControllerService {
    private BlockingQueue messageQueue;
    private User loggedInUser;
    private List<User> userList;
    private IView view;
    private OutgoingMessageHandler outgoingMessageHandler;

    public Controller(Stage stage) {
        this.messageQueue = new LinkedBlockingQueue();
        this.userList = new ArrayList<>();
        this.view = new ViewController(stage, this);
        this.outgoingMessageHandler = new OutgoingChatProtocolMessageHandler(this);
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

    private User getUserByName(String userName) {
        for (User user : userList) {
            if (user.getName().equals(userName))
                return user;
        }
        return null;
    }
}
