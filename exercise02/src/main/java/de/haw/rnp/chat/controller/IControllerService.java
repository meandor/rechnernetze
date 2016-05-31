package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;

public interface IControllerService {
    boolean login(String userName, InetAddress address, InetAddress localAddress, int port, int localport);
    void logout();
    boolean sendMessage(String recipient, String message);
    boolean startServer(InetAddress hostName, int port);
    boolean addMessageToQueue(Message message);
    boolean addUser(User u);
    User getLoggedInUser();
    void setLoggedInUser(User u);
    ExecutorService getExecutor();
}
