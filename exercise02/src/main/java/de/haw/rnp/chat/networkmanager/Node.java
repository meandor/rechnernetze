package de.haw.rnp.chat.networkmanager;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public abstract class Node {
    protected int port;
    protected InetAddress hostName;
    protected OutputStream out;
    protected InputStream in;

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

    public InputStream getIn() {
        return in;
    }

    public abstract boolean startClientNode();

    public abstract void stopClientNode();

    public abstract boolean startServerNode();

    public abstract void stopServerNode();

    public abstract void readServerInput();

    public abstract void awaitConnections();

    public abstract BlockingQueue<Socket> getIncomingSockets();
}
