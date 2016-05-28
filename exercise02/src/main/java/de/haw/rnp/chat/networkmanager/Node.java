package de.haw.rnp.chat.networkmanager;

import java.io.OutputStream;
import java.net.InetAddress;

public abstract class Node {
    protected int port;
    protected InetAddress hostName;
    protected OutputStream out;

    public Node(int port, InetAddress hostName) {
        this.port = port;
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getHostName() {
        return hostName;
    }

    public void setHostName(InetAddress hostName) {
        this.hostName = hostName;
    }

    public OutputStream getOut() {
        return out;
    }

    public abstract void startClientNode();

    public abstract void stopClientNode();

    public abstract void startServerNode();

    public abstract void stopServerNode();
}
