package de.haw.rnp.chat.networkmanager.model;

import java.net.InetAddress;

/**
 * Protocol Message for send a logout message.
 */
public class LogoutMessage extends ChatProtocolMessage {

    public LogoutMessage(InetAddress senderHostName, int senderPort, InetAddress logoutHostName, int logoutPort) {
        super(((byte) 0x02), senderHostName, senderPort, 2);
        byte[] ipField = this.IPField(logoutHostName);
        this.addField(ipField);
        byte[] portField = this.portField(logoutPort);
        this.addField(portField);
    }
}
