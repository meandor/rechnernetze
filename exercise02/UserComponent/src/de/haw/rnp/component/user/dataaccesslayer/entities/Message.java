package de.haw.rnp.component.user.dataaccesslayer.entities;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class Message {
    private String sender;
    private String recipient;
    private boolean isSend;
    private String message;
    private LocalDateTime dateTime;

    public Message(String sender, String recipient, String message){
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
        isSend = false;
        dateTime = LocalDateTime.now();
    }

    public boolean isSend() {
        return isSend;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public byte[] getMessageAsBytes(){
        return message.getBytes(StandardCharsets.US_ASCII);
    }
}
