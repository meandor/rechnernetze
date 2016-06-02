package de.haw.rnp.chat.server.dataaccesslayer.entities;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;

public class User {

    private InetAddress ip;
    private int port;
    private String username;
    Collection<Message> messages;

    public User (InetAddress ip, int port){
        this.ip = ip;
        this.port = port;
        this.username = "";
        this.messages = new ArrayList<>();
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(String sender, String recipient, String text){
        messages.add(new Message(sender, recipient, text));
    }
}
