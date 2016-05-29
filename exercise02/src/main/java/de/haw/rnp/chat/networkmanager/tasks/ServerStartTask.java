package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

import java.util.concurrent.Callable;

public class ServerStartTask implements Callable<Boolean> {

    private Node node;

    public ServerStartTask(Node node) {
        this.node = node;
    }

    @Override
    public Boolean call() throws Exception {
        return this.node.startServerNode();
    }
}
