package de.haw.rnp.chat.server.dataaccesslayer.entities;

import de.haw.rnp.chat.server.dataaccesslayer.enumerations.MessageType;

import java.util.Collection;

public class Frame {
    private int version;
    private MessageType messageType;
    private int length;
    private Collection<Field> fields;

    public Frame(int version, MessageType message, int length){
        this.version  = version;
        this.messageType = messageType;
        this.length = length;
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
}
