package de.haw.rnp.adapter.outgoingpeer.businesslogiclayer;

import de.haw.rnp.adapter.outgoingpeer.accesslayer.IOutgoingPeerAdapterServices;
import de.haw.rnp.util.AddressType;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OutgoingPeerAdapterBusinessLogic implements IOutgoingPeerAdapterServices{

    @Override
    public boolean sendData(AddressType address, byte[] data) {
        try {
            Socket socket = new Socket(address.getIp(), address.getPort());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.write(data);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
