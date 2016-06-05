package de.haw.rnp.chat.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddressType {

    private InetAddress ip;
    private int port;

    public AddressType(InetAddress ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public AddressType(String ip, int port){
        try {
            this.ip = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
