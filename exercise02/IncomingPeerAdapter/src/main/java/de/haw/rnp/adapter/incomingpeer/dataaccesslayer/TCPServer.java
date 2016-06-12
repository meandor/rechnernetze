package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * TCP Server Implementation.
 */
public class TCPServer extends Server {

    private ServerSocket serverSocket;

    public TCPServer(int port, BlockingQueue<byte[]> queue) {
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
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (!isRunning()) {
                    System.out.println("Server Stopped.");
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            this.threadPool.execute(new TCPSocketWorker(clientSocket, incomingConnections));
        }
        this.threadPool.shutdown();
        System.out.println("Server Stopped.");
    }

    @Override
    public synchronized void stop() {
        isRunning = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("can't close server", e);
        }
    }

    public synchronized void openServerSocket() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Starting TCP Server");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open provided port", e);
        }
    }
}
