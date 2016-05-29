package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

public abstract class GeneralTask {

    protected Node node;

    public GeneralTask(Node node) {
        this.node = node;
    }
}
