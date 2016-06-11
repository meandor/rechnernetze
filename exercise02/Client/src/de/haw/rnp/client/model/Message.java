package de.haw.rnp.client.model;

import java.util.List;

/**
 * This class represents a message.
 */
public class Message {
    private String text;
    private User sender;
    private List<User> receiver;

    public Message(String text, User sender, List<User> receiver) {
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<User> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<User> receiver) {
        this.receiver = receiver;
    }

    public boolean addReceiver(User u) {
        return this.receiver.add(u);
    }

    public boolean removeReceiver(User u) {
        return this.receiver.remove(u);
    }

    @Override
    public String toString() {
        return text;
    }
}
