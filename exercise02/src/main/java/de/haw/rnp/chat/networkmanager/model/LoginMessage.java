package de.haw.rnp.chat.networkmanager.model;

import java.net.InetAddress;

/**
 * Protocol Message for the login.
 */
public class LoginMessage extends ChatProtocolMessage {

    public LoginMessage(InetAddress senderHostName, int senderPort, String loginUserName, InetAddress loginUserHostName, int loginUserPort) {
        super(((byte) 0x01), senderHostName, senderPort, 3);
        byte[] ipField = this.IPField(loginUserHostName);
        this.addField(ipField);
        byte[] portField = this.portField(loginUserPort);
        this.addField(portField);
        byte[] nameByte = this.nameField(loginUserName);
        this.addField(nameByte);
    }
}
