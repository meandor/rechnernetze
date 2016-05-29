package de.haw.rnp.chat.model;

import de.haw.rnp.chat.networkmanager.Node;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents a User, containing a name and a clientNode.
 */
public class User {
    private String name;
    private Node clientNode;
    private BlockingQueue<Node> incomingNodes;
    private Node serverNode;

    /**
     * @param name the name of the user
     * @param clientNode  the clientNode attached to a user
     */

    public User(String name, Node clientNode){
        this.setName(name);
        this.setClientNode(clientNode);
        this.incomingNodes = new LinkedBlockingQueue<>();
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

    public boolean addIncomingClientNode(Node n) {
        return this.incomingNodes.offer(n);
    }

    public BlockingQueue<Node> getIncomingNodes() {
        return incomingNodes;
    }

    @Override
    public String toString(){
        return "Name: " + this.name + " Node: " + this.clientNode;
    }
}
