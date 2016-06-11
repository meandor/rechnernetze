package de.haw.rnp.util;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressType that = (AddressType) o;

        if (getPort() != that.getPort()) return false;
        return getIp().equals(that.getIp());

    }

    @Override
    public int hashCode() {
        int result = getIp().hashCode();
        result = 31 * result + getPort();
        return result;
    }
}
