package de.haw.rnp.adapter.outgoingpeer.businesslogiclayer;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import de.haw.rnp.adapter.outgoingpeer.accesslayer.IOutgoingPeerAdapterServices;
import de.haw.rnp.util.AddressType;
import de.haw.rnp.util.ChatUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class OutgoingPeerAdapterBusinessLogic implements IOutgoingPeerAdapterServices {

    @Override
    public boolean sendData(AddressType address, byte[] data, boolean isTCP) {
        if (isTCP) {
            try {
                Socket socket = new Socket(address.getIp(), address.getPort());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.write(data);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            InetSocketAddress serverAddr = new InetSocketAddress(address.getIp(), address.getPort());
            try {
                SctpChannel sc = SctpChannel.open(serverAddr, 0, 0);
                MessageInfo messageInfo = MessageInfo.createOutgoing(serverAddr, 0);
                ByteBuffer buf = ByteBuffer.allocateDirect(ChatUtil.BYTEBUFFER_SIZE);
                buf.put(data);
                buf.flip();
                System.out.println("Send bytebuffer limit: " + buf.limit());
                sc.send(buf, messageInfo);
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
