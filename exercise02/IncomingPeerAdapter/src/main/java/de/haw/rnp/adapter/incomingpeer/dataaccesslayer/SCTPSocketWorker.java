package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import com.sun.nio.sctp.SctpChannel;

import java.util.concurrent.BlockingQueue;

/**
 * Creates a SCTP protocol worker.
 *
 * @author Daniel Schruhl<danielschruhl@gmail.com>
 */
public class SCTPSocketWorker extends SocketWorker {

    private SctpChannel clientSocket;

    public SCTPSocketWorker(SctpChannel clientSocket, BlockingQueue<byte[]> queue) {
        super(queue);
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

    }
}
