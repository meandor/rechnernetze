package de.haw.rnp.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class represents an AddressType.
 * <p>
 * Each AdressType has an IP and a port.
 */
public class AddressType {

    private InetAddress ip;
    private int port;

    /**
     * Constructs the AddressType.
     *
     * @param ip   InetAddress with the IP
     * @param port int port number
     */
    public AddressType(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Constructs the AddressType.
     *
     * @param ip   String of the IP
     * @param port int port number
     */
    public AddressType(String ip, int port) {
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

        return getPort() == that.getPort() && getIp().equals(that.getIp());

    }

    @Override
    public int hashCode() {
        int result = getIp().hashCode();
        result = 31 * result + getPort();
        return result;
    }
}
