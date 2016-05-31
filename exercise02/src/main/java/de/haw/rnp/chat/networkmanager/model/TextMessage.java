package de.haw.rnp.chat.networkmanager.model;

import de.haw.rnp.chat.model.User;

import java.net.InetAddress;
import java.util.List;

/**
 * Protocol Message for an actual text message.
 */
public class TextMessage extends ChatProtocolMessage {

    public TextMessage(InetAddress senderIP, int senderPort, String text, List<User> userList) {
        super(((byte) 0x03), senderIP, senderPort, 2);
        byte[] textField = this.textField(text);
        this.addField(textField);
        byte[] userListField = this.userListField(userList);
        this.addField(userListField);
    }
}
