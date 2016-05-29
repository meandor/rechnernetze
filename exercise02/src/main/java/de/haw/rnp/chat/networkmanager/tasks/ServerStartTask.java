package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

import java.util.concurrent.Callable;

public class ServerStartTask extends GeneralTask implements Callable<Boolean> {

    public ServerStartTask(Node node) {
        super(node);
    }

    @Override
    public Boolean call() throws Exception {
        return this.node.startServerNode();
    }
}
