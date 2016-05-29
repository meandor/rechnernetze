package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

/**
 * Created by daniel on 29.05.2016.
 */
public class ClientCloseTask extends GeneralTask implements Runnable {

    public ClientCloseTask(Node node) {
        super(node);
    }

    @Override
    public void run() {
        node.stopClientNode();
    }
}
