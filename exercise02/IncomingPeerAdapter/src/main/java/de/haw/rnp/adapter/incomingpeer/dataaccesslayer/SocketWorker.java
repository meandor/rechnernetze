package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import java.util.concurrent.BlockingQueue;

/**
 * Starts reading data from all connected incoming connections.
 */
abstract class SocketWorker implements Runnable {

    protected BlockingQueue<byte[]> queue;

    public SocketWorker(BlockingQueue<byte[]> queue) {
        this.queue = queue;
    }

    /**
     * Reads the input from the incoming connections here.
     */
    @Override
    abstract public void run();
}
