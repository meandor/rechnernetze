package de.haw.rnp.chat.model;

/**
 * This class represents a User, containing a name and a node.
 */
public class User {
    private String name;
    private Node node;

    /**
     * @param name the name of the user
     * @param node  the node attached to a user
     */

    public User(String name, Node node){
        this.setName(name);
        this.setNode(node);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString(){
        return "Name: " + this.name + " Node: " + this.node;
    }
}
