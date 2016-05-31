package de.haw.rnp.chat.networkmanager.model;

import java.net.InetAddress;

public class MyNameMessage extends ChatProtocolMessage {

    public MyNameMessage(InetAddress senderIP, int senderPort, String name) {
        super(((byte) 0x04), senderIP, senderPort, 1);
        byte[] namefield = this.nameField(name);
        this.addField(namefield);
    }
}
