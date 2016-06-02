package de.haw.rnp.chat.client.networkmanager.tasks;

import de.haw.rnp.chat.client.networkmanager.Node;

import java.util.concurrent.Callable;

/**
 * Task for closing a client connection. Returns true if closed.
 */
public class ClientCloseTask extends GeneralTask implements Callable<Boolean> {

    public ClientCloseTask(Node node) {
        super(node);
    }

    @Override
    public Boolean call() throws Exception {
        return node.stopClientNode();
    }
}
