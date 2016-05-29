package de.haw.rnp.chat.model;

import de.haw.rnp.chat.networkmanager.Node;

import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents a User, containing a name and a clientNode.
 */
public class User {
    private String name;
    private Node clientNode;
    private Node serverNode;
    private InetAddress hostName;
    private int port;

    /**
     * @param name       the name of the user
     * @param clientNode the clientNode attached to a user
     */

    public User(String name, Node clientNode, InetAddress hostName, int port) {
        this.setName(name);
        this.setClientNode(clientNode);
        this.port = port;
        this.hostName = hostName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getClientNode() {
        return clientNode;
    }

    public void setClientNode(Node clientNode) {
        this.clientNode = clientNode;
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
        return "Name: " + this.name + " Node: " + this.clientNode;
    }
}
