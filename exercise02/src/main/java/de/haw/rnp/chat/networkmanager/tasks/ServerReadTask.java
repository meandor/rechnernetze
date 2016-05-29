package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

/**
 * Created by daniel on 29.05.16.
 */
public class ServerReadTask implements Runnable {
    private Node server;

    public ServerReadTask(Node server) {
        this.server = server;
    }

    @Override
    public void run() {
        this.server.readServerInput();
    }
}
