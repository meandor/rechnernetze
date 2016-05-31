package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.IControllerService;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * A Node can be a server or a client.
 * <p>
 * A Node is equal to a Peer. Each Node can be a server or a client.
 */
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

    /**
     * Returns true if client node was started.
     *
     * @return true if started
     */
    public abstract boolean startClientNode();

    /**
     * Returns true if client node was stopped.
     *
     * @return true if stopped
     */
    public abstract boolean stopClientNode();

    /**
     * Returns true if server node was started.
     *
     * @param controller IControllerService controller
     * @return true if started
     */
    public abstract boolean startServerNode(IControllerService controller);

    /**
     * Returns true if server node was stopped.
     *
     * @return true if stopped
     */
    public abstract boolean stopServerNode();

    /**
     * Reads the input from a client connection.
     */
    public abstract void readServerInput();

    /**
     * Waits for connections to the server.
     */
    public abstract void awaitConnections();

    /**
     * Returns all incoming active client connections.
     *
     * @return BlockingQueue with all incoming active client connections
     */
    public abstract BlockingQueue<Socket> getIncomingSockets();
}
