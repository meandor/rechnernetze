package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

import java.util.concurrent.Callable;

public class ClientStartTask implements Callable {

    private Node node;

    public ClientStartTask(Node node) {
        this.node = node;
    }

    @Override
    public Object call() throws Exception {
        return this.node.startClientNode();
    }
}
