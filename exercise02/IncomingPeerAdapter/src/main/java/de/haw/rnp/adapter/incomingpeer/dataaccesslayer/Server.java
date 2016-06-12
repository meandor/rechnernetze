package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class can open a server and accept all incoming connections.
 */
abstract public class Server implements Runnable {

    protected int port;
    protected boolean isRunning;
    protected Thread runningThread;
    protected ExecutorService threadPool;
    protected BlockingQueue<byte[]> incomingConnections;

    /**
     * Constructs the server with the given parameters and starts the thread ThreadPool.
     *
     * @param port                int port number
     * @param incomingConnections all incoming connection
     */
    public Server(int port, BlockingQueue<byte[]> incomingConnections) {
        this.port = port;
        this.incomingConnections = incomingConnections;
        threadPool = Executors.newCachedThreadPool();
    }

    /**
     * Handling the incoming connections here.
     */
    @Override
    abstract public void run();

    /**
     * Checks if the server is still running.
     *
     * @return @code{true} if still running, false otherwise
     */
    protected synchronized boolean isRunning() {
        return isRunning;
    }

    /**
     * Stops the server.
     */
    abstract public void stop();

    /**
     * Starts the actual Server.
     */
    abstract protected void openServerSocket();
}
