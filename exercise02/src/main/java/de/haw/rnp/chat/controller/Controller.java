package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;
import de.haw.rnp.chat.networkmanager.ChatProtocolMessageHandler;
import de.haw.rnp.chat.networkmanager.MessageHandler;
import de.haw.rnp.chat.view.IView;
import de.haw.rnp.chat.view.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static javafx.application.Application.launch;

public class Controller implements IControllerService{
    private BlockingQueue messageQueue;
    private User loggedInUser;
    private List<User> userList;
    private IView view;
    private MessageHandler messageHandler;

    public Controller(Stage stage) {
        this.messageQueue = new LinkedBlockingQueue();
        this.userList = new ArrayList<>();
        this.view = new ViewController(stage, this);
        this.messageHandler = new ChatProtocolMessageHandler(this);
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
    public String login(String userName, InetAddress address, int port) {
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean sendMessage(String message, String username) {
        User user = getUserByName(username);
        if(user.equals(null))
            return false;
        //sendMessage
        return true;
    }

    private User getUserByName(String userName){
        for(User user : userList){
            if(user.getName().equals(userName))
                return user;
        }
        return null;
    }
}
