package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private int port;
    private ServerSocket serverSocket;
    private boolean isRunning;
    private Thread runningThread;
    private ExecutorService threadPool;
    private BlockingQueue<byte[]> queue;

    public Server(int port, BlockingQueue<byte[]> queue){
        this.port = port;
        this.queue = queue;
        threadPool = Executors.newCachedThreadPool();
    }

    @Override
    public void run(){
        synchronized(this){
            runningThread = Thread.currentThread();
            isRunning = true;
        }

        openServerSocket();

        while(isRunning()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(!isRunning()) {
                    System.out.println("Server Stopped.");break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            this.threadPool.execute(new SocketWorker(clientSocket, queue));
        }
        this.threadPool.shutdown();
        System.out.println("Server Stopped.") ;
    }

    private synchronized boolean isRunning() {
        return isRunning;
    }

    public synchronized void stop(){
        isRunning = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("can't close server", e);
        }
    }

    private void openServerSocket() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open provided port", e);
        }
    }


}
