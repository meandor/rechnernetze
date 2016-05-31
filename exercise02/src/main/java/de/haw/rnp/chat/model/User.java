package de.haw.rnp.chat.model;

import de.haw.rnp.chat.networkmanager.Node;

import java.net.InetAddress;

/**
 * This class represents a User.
 */
public class User {

    private String name;
    private int port;
    private InetAddress hostName;
    private Node serverNode;

    /**
     * Constructs the User with the given parameters.
     *
     * @param name     name of the user
     * @param port     port of the user
     */
    public User(String name, int port, InetAddress hostName) {
        this.name = name;
        this.port = port;
        this.hostName = hostName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getServerNode() {
        return serverNode;
    }

    public void setServerNode(Node serverNode) {
        this.serverNode = serverNode;
    }

    public InetAddress getHostName() {
        return hostName;
    }

    public void setHostName(InetAddress hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.hostName.getHostAddress() + ":" + this.port + ")";
    }
}
