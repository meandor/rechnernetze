package de.haw.rnp.chat.model;

import de.haw.rnp.chat.networkmanager.Node;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents a User. Every User can have clients attached to it.
 */
public class User {

    private String name;
    private int port;
    private InetAddress hostName;
    private Node serverNode;
    private List<Node> clientNodes;

    /**
     * Constructs the User with the given parameters.
     *
     * @param name     name of the user
     * @param port     port of the user
     * @param hostName InetAddress of the user
     */
    public User(String name, int port, InetAddress hostName) {
        this.name = name;
        this.port = port;
        this.hostName = hostName;
        this.clientNodes = new ArrayList<>();
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

    public boolean addClientNode(Node n) {
        return this.clientNodes.add(n);
    }

    public boolean removeClientNode(Node n) {
        return this.clientNodes.remove(n);
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
        return this.name + " (" + this.hostName.getHostName() + ":" + this.port + ")";
    }
}
