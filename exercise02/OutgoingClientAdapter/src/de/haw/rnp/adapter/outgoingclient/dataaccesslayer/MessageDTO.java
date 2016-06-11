package de.haw.rnp.adapter.outgoingclient.dataaccesslayer;

import de.haw.rnp.util.AddressType;

public class MessageDTO {

    private AddressType sender;
    private String message;

    public MessageDTO(AddressType sender, String message){
        this.sender = sender;
        this.message = message;
    }

    public AddressType getSender() {
        return sender;
    }

    public void setSender(AddressType sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
