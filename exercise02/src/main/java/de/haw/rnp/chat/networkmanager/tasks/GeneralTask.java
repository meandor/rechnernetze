package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

public abstract class GeneralTask {

    protected Node node;
    protected boolean stopped;

    public GeneralTask(Node node) {
        this.node = node;
        this.stopped = false;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stop() {
        this.stopped = true;
    }
}
