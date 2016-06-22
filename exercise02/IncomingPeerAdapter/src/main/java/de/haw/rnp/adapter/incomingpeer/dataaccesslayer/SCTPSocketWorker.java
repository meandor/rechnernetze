package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import de.haw.rnp.util.ChatUtil;

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
        ByteBuffer buf = ByteBuffer.allocateDirect(ChatUtil.BYTEBUFFER_SIZE);
        try {
            this.messageInfo = clientSocket.receive(buf, null, null);
            if (messageInfo != null) {
                buf.flip();
                if (buf.remaining() > 0) {
                    byte[] data = new byte[buf.remaining()];
                    buf.get(data, 0, buf.remaining());
                    if (readBytes == null) {
                        readBytes = data;
                    } else {
                        System.arraycopy(data, 0, readBytes, readBytes.length, data.length);
                    }
                    queue.offer(this.readBytes);
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
