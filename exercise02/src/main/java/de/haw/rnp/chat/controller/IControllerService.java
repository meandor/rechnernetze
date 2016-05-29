package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;

import java.net.InetAddress;

public interface IControllerService {
    public String login(String userName, InetAddress address, InetAddress localAddress, int port, int localport);
    public void logout();
    public boolean sendMessage(String recipient, String message);
    public boolean addMessageToQueue(Message message);
}
