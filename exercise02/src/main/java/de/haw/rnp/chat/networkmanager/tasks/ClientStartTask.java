package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

public class ClientStartTask implements Runnable {

    private Node node;

    public ClientStartTask(Node node) {
        this.node = node;
    }

    @Override
    public void run() {
        this.node.startClientNode();
    }
}
