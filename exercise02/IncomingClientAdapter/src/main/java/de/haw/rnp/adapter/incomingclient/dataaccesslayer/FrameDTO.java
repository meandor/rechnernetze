package de.haw.rnp.adapter.incomingclient.dataaccesslayer;

import de.haw.rnp.util.enumerations.MessageType;
import de.haw.rnp.util.AddressType;

import java.util.ArrayList;
import java.util.Collection;

public class FrameDTO {

    private AddressType sender;
    private AddressType recipient;
    private int version;
    private MessageType messageType;
    private int length;
    private Collection<FieldDTO> fields;

    public FrameDTO(AddressType sender, AddressType recipient, int version, MessageType messageType, int length){
        this.version  = version;
        this.messageType = messageType;
        this.length = length;
        this.sender = sender;
        this.recipient = recipient;
        this.fields = new ArrayList<>();
    }

    public void addFieldDTO(FieldDTO... field){
        for(FieldDTO f : field){
            fields.add(f);
        }
    }

    public AddressType getSender() {
        return sender;
    }

    public AddressType getRecipient() {
        return recipient;
    }

    public int getVersion() {
        return version;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public int getLength() {
        return length;
    }

    public Collection<FieldDTO> getFields() {
        return fields;
    }
}
