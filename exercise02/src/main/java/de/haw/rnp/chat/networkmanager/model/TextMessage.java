package de.haw.rnp.chat.networkmanager.model;

import java.net.InetAddress;
import java.util.List;

public class TextMessage extends ChatProtocolMessage {

    public TextMessage(InetAddress senderIP, int senderPort, String text, List<ChatUser> userList) {
        super(((byte) 0x03), senderIP, senderPort, 2);
        byte[] textField = this.textField(text);
        this.addField(textField);
        byte[] userListField = this.userListField(userList);
        this.addField(userListField);
    }
}