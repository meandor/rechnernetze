package de.haw.rnp.chat.model;

import de.haw.rnp.chat.networkmanager.Node;

/**
 * This class represents a User, containing a name and a clientNode.
 */
public class User {
    private String name;
    private Node clientNode;
    private Node serverNode;

    /**
     * @param name the name of the user
     * @param clientNode  the clientNode attached to a user
     */

    public User(String name, Node clientNode){
        this.setName(name);
        this.setClientNode(clientNode);
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

    @Override
    public String toString(){
        return "Name: " + this.name + " Node: " + this.clientNode;
    }
}
