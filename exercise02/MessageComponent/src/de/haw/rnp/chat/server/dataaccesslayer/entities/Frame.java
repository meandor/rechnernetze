package de.haw.rnp.chat.server.dataaccesslayer.entities;

import de.haw.rnp.chat.server.dataaccesslayer.enumerations.MessageType;
import de.haw.rnp.chat.util.ChatUtil;

import java.util.Collection;

public class Frame {
    private User sender;
    private User recipient;
    private int version;
    private MessageType messageType;
    private int length;
    private Collection<Field> fields;

    public Frame(User sender, User recipient, int version, MessageType messageType, int length){
        this.version  = version;
        this.messageType = messageType;
        this.length = length;
        this.sender = sender;
        this.recipient = recipient;
    }

    public void addField(Field field){
        fields.add(field);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Collection<Field> getFields() {
        return fields;
    }

    public void setFields(Collection<Field> fields) {
        this.fields = fields;
    }

    public byte[] getFrameAsBytes(){
        byte[] result = new byte[0];
        byte[] reserved = new byte[]{0x0, 0x0};

        //building the header
        ChatUtil.concat(result,
                        ChatUtil.intToOneByteArray(version),
                        messageType.getCodeAsArray(),
                        reserved,
                        sender.getAddressAsBytes(),
                        sender.getPortAsBytes(),
                        ChatUtil.intToByteArray(length));

        //adding fields
        for(Field field : fields){
            ChatUtil.concat(result, field.getFieldAsBytes());
        }

        return result;
    }
}
