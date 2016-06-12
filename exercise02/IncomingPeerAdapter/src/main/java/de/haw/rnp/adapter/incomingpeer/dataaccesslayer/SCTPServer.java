package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;


import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;

/**
 * SCTP Implementation of the Server.
 *
 * @author Daniel Schruhl<danielschruhl@gmail.com>
 */
public class SCTPServer extends Server {

    private SctpServerChannel channel;

    public SCTPServer(int port, BlockingQueue<byte[]> queue) {
        super(port, queue);
    }

    @Override
    public void run() {
        synchronized (this) {
            runningThread = Thread.currentThread();
            isRunning = true;
        }

        this.openServerSocket();

        while (isRunning()) {
            SctpChannel clientSocket;
            try {
                clientSocket = channel.accept();
            } catch (IOException e) {
                if (!isRunning()) {
                    System.out.println("Server Stopped.");
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            this.threadPool.execute(new SCTPSocketWorker(clientSocket, incomingConnections));
        }
        this.threadPool.shutdown();
        System.out.println("Server Stopped.");
    }

    @Override
    public synchronized void stop() {
        isRunning = false;
        try {
            this.channel.close();
        } catch (IOException e) {
            throw new RuntimeException("can't close server", e);
        }
    }

    @Override
    protected synchronized void openServerSocket() {
        try {
            channel = SctpServerChannel.open();
            InetSocketAddress serverAddress = new InetSocketAddress(this.port);
            channel.bind(serverAddress);
            System.out.println("Started SCTP-Server");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open provided port", e);
        }
    }
}
