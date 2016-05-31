package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

/**
 * Task for handling incoming messages.
 */
public class ServerReadTask extends GeneralTask implements Runnable {

    public ServerReadTask(Node node) {
        super(node);
    }

    @Override
    public void run() {
        while (!this.isStopped()) {
            this.node.readServerInput();
            if (Thread.interrupted()) {
                break;
            }
        }
    }
}
