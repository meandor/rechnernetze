package de.haw.rnp.chat.networkmanager.tasks;

import de.haw.rnp.chat.networkmanager.Node;

import java.io.IOException;

/**
 * Task for handling incoming messages.
 */
public class ServerReadTask extends GeneralTask implements Runnable {

    public ServerReadTask(Node node) {
        super(node);
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.node.readServerInput();
            } catch (InterruptedException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
