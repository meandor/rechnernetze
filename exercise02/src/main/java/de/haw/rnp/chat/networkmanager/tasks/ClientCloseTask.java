package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

import java.util.concurrent.Callable;

/**
 * Created by daniel on 29.05.2016.
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
