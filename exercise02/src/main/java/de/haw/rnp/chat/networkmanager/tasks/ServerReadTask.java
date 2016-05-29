package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

public class ServerReadTask extends GeneralTask implements Runnable {

    public ServerReadTask(Node node) {
        super(node);
    }

    @Override
    public void run() {
        this.node.readServerInput();
    }
}
