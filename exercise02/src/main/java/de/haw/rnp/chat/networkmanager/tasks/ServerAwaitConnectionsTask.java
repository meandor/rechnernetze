package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

public class ServerAwaitConnectionsTask extends GeneralTask implements Runnable {

    public ServerAwaitConnectionsTask(Node node) {
        super(node);
    }

    @Override
    public void run() {
        while (!this.stopped) {
            this.node.awaitConnections();
            if (Thread.interrupted()) {
                break;
            }
        }
    }
}
