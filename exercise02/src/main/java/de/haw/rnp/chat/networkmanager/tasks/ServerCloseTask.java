package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

import java.util.concurrent.Callable;

public class ServerCloseTask extends GeneralTask implements Callable<Boolean> {

    public ServerCloseTask(Node node) {
        super(node);
    }

    @Override
    public Boolean call() throws Exception {
        return this.node.stopServerNode();
    }
}