package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SocketWorker implements Runnable{

    private Socket clientSocket;
    private BlockingQueue<byte[]> queue;

    public SocketWorker(Socket clientSocket, BlockingQueue<byte[]> queue) {
        this.clientSocket = clientSocket;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            byte[] tmp = IOUtils.toByteArray(input);
            input.close();
            queue.offer(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
