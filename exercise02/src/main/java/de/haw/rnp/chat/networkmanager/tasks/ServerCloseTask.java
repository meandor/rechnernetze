package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

import java.util.concurrent.Callable;

/**
 * Task for closing a server. Returns true if server was closed.
 */
public class ServerCloseTask extends GeneralTask implements Callable<Boolean> {

    public ServerCloseTask(Node node) {
        super(node);
    }

    @Override
    public Boolean call() throws Exception {
        return this.node.stopServerNode();
    }
}
