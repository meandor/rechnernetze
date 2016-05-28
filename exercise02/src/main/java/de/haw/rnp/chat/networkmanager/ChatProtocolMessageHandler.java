package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.Controller;
import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;

import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.List;

/**
 * Handles all protocol messages that are forwarded between the peers.
 *
 * @author Daniel Schruhl <danielschruhl@gmail.com>
 */
public class ChatProtocolMessageHandler implements MessageHandler {

    private Controller controller;

    public ChatProtocolMessageHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void processMessage(byte[] protocolMessage) {
        byte version = protocolMessage[0];
        byte messageType = protocolMessage[1];

        /*if (messageType == 0x01) {
            String name = "";
            int port = 0;
            String hostname = "";
            InetAddress hostName = new Inet4Address(hostname);
            this.controller.setLoggedInUser(this.login(name, hostName, port));
        }*/
    }

    @Override
    public User login(String name, InetAddress hostName, int port) {
        return null;
    }

    @Override
    public void logout(User user) {

    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void receiveMessage(Message message) {
        this.controller.addMessage(message);
    }

    @Override
    public void sendName(User user, List recipient) {

    }

    @Override
    public void changeName(String name, InetAddress hostName, int port) {

    }

    @Override
    public User initialConnect(InetAddress hostName, int port) {
        return null;
    }
}
