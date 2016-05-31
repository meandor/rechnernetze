package de.haw.rnp.chat.networkmanager.model;

import java.net.InetAddress;

public class ChatUser {
    private InetAddress ip;
    private int port;

    public ChatUser(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
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
}
