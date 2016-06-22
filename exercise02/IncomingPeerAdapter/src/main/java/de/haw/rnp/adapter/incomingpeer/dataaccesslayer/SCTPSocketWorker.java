package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;

/**
 * Creates a SCTP protocol worker.
 *
 * @author Daniel Schruhl<danielschruhl@gmail.com>
 */
public class SCTPSocketWorker extends SocketWorker {

    private SctpChannel clientSocket;
    private MessageInfo messageInfo;
    private byte[] readBytes;

    public SCTPSocketWorker(SctpChannel clientSocket, BlockingQueue<byte[]> queue) {
        super(queue);
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        ByteBuffer buf = ByteBuffer.allocateDirect(512);
        messageInfo = null;
        if (clientSocket.isBlocking()) {
            try {
                clientSocket.configureBlocking(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            messageInfo = clientSocket.receive(buf, null, null);
            if (messageInfo != null && messageInfo.bytes() > 0) {
                byte[] data = new byte[messageInfo.bytes()];
                buf.position(0);
                buf.get(data);
                queue.offer(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
