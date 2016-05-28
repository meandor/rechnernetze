package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Controller {
    private BlockingQueue messageQueue;
    private User loggedInUser;
    private static Controller controller = new Controller();
    private List<User> userList;

    private Controller() {
        this.messageQueue = new LinkedBlockingQueue();
        this.userList = new ArrayList<>();
    }

    public static Controller getInstance() {
        return controller;
    }

    public BlockingQueue getMessageQueue() {
        return messageQueue;
    }

    public boolean addMessage(Message message) {
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
}
