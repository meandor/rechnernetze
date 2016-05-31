package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.controller.IControllerService;
import de.haw.rnp.chat.networkmanager.Node;

/**
 * Tasks are used for doing concurrency related work with a thread pool.
 *
 * Each task wraps a Node that specifies the action to be done for the task.
 */
public abstract class GeneralTask {

    protected Node node;
    protected boolean stopped;
    protected IControllerService controller;

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
