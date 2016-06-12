package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Socket worker for TCP connections.
 *
 * @author Daniel Schruhl<danielschruhl@gmail.com>
 */
public class TCPSocketWorker extends SocketWorker {

    private Socket clientSocket;

    public TCPSocketWorker(Socket clientSocket, BlockingQueue<byte[]> queue) {
        super(queue);
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            byte[] tmp = IOUtils.toByteArray(input);
            input.close();
            queue.offer(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
