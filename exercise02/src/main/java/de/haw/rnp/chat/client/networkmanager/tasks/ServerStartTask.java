package de.haw.rnp.chat.client.networkmanager.tasks;

import de.haw.rnp.chat.client.controller.IControllerService;
import de.haw.rnp.chat.client.networkmanager.Node;

import java.util.concurrent.Callable;

/**
 * Task for starting a server. Returns true if started.
 */
public class ServerStartTask extends GeneralTask implements Callable<Boolean> {

    public ServerStartTask(Node node, IControllerService controller) {
        super(node);
        this.controller = controller;
    }

    @Override
    public Boolean call() throws Exception {
        return this.node.startServerNode(this.controller);
    }
}