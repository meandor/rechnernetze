package de.haw.rnp.chat.networkmanager.model;

import java.net.InetAddress;

/**
 * Protocol Message for the sending a name.
 */
public class MyNameMessage extends ChatProtocolMessage {

    public MyNameMessage(InetAddress senderIP, int senderPort, String name) {
        super(((byte) 0x04), senderIP, senderPort, 1);
        byte[] namefield = this.nameField(name);
        this.addField(namefield);
    }
}
