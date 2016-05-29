package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

public class ServerAwaitConnectionsTask implements Runnable {

    private Node serverNode;

    public ServerAwaitConnectionsTask(Node serverNode) {
        this.serverNode = serverNode;
    }

    @Override
    public void run() {
        this.serverNode.awaitConnections();
    }
}
