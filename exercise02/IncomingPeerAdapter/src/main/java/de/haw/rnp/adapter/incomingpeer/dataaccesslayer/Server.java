package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract public class Server implements Runnable {

    protected int port;
    protected boolean isRunning;
    protected Thread runningThread;
    protected ExecutorService threadPool;
    protected BlockingQueue<byte[]> queue;

    public Server(int port, BlockingQueue<byte[]> queue) {
        this.port = port;
        this.queue = queue;
        threadPool = Executors.newCachedThreadPool();
    }

    @Override
    abstract public void run();

    protected synchronized boolean isRunning() {
        return isRunning;
    }

    abstract public void stop();

    abstract protected void openServerSocket();
}
