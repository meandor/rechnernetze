package de.haw.rnp.chat.client.networkmanager.tasks;

import de.haw.rnp.chat.client.networkmanager.Node;

import java.util.concurrent.Callable;

/**
 * Task for starting a client. Returns true if client was started.
 */
public class ClientStartTask extends GeneralTask implements Callable<Boolean> {

    public ClientStartTask(Node node) {
        super(node);
    }

    @Override
    public Boolean call() throws Exception {
        return this.node.startClientNode();
    }
}
