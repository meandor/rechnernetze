package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;

import java.net.InetAddress;
import java.util.List;

/**
 * Interface for handling protocol messages.
 */
public interface MessageHandler {

    void processMessage(byte[] protocolMessage);

    User login(Node clientNode, String loginName, int loginPort);

    void logout(User user);

    void sendMessage(Message message);

    void receiveMessage(Message message);

    void sendName(User user, List recipient);

    void changeName(String name, InetAddress hostName, int port);

    Node initialConnect(InetAddress hostName, int port);
}
