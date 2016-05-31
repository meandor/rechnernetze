package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;

import java.net.InetAddress;

public interface IControllerService {
    String login(String userName, InetAddress address, InetAddress localAddress, int port, int localport);
    void logout();
    boolean sendMessage(String recipient, String message);
    boolean startServer(InetAddress hostName, int port);
    boolean addMessageToQueue(Message message);
}
