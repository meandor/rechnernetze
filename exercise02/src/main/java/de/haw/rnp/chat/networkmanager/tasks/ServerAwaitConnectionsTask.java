package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

/**
 * Task for handling incoming connections.
 */
public class ServerAwaitConnectionsTask extends GeneralTask implements Runnable {

    public ServerAwaitConnectionsTask(Node node) {
        super(node);
    }

    @Override
    public void run() {
        while (!this.isStopped()) {
            this.node.awaitConnections();
            if (Thread.interrupted()) {
                break;
            }
        }
    }
}
