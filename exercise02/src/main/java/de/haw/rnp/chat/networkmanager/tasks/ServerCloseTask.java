package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

public class ServerCloseTask extends GeneralTask implements Runnable {

    public ServerCloseTask(Node node) {
        super(node);
    }

    @Override
    public void run() {
        this.node.stopServerNode();
    }
}
