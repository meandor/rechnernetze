package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.IControllerService;
import de.haw.rnp.chat.model.User;

import java.net.InetAddress;
import java.util.List;

public class IncomingChatProtocolMessageHandler implements IncomingMessageHandler {

    private IControllerService controller;

    public IncomingChatProtocolMessageHandler(IControllerService controller) {
        this.controller = controller;
    }

    @Override
    public void processMessage(byte[] byteStream) {

    }

    @Override
    public void login(InetAddress hostName, int port, String userName) {

    }

    @Override
    public void setUserName(InetAddress hostName, int port, String userName) {

    }

    @Override
    public void logout(InetAddress hostName, int port) {

    }

    @Override
    public void propagate(byte[] message, List<User> users) {

    }

    @Override
    public void receiveMessage(String text, List<User> users) {

    }
}
