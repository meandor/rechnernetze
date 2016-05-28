package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

/**
 * Created by daniel on 28.05.16.
 */
public class ServerStartTask implements Runnable {

    private Node node;

    public ServerStartTask(Node node) {
        this.node = node;
    }

    @Override
    public void run() {
        this.node.startServerNode();
    }
}
