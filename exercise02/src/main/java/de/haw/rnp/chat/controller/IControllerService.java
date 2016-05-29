package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;

import java.net.InetAddress;

public interface IControllerService {
    public String login(String userName, InetAddress address, int port);
    public void logout();
    public boolean sendMessage(String message, String username);
    public boolean addMessageToQueue(Message message);
}
