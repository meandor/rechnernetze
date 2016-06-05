package de.haw.rnp.chat.server.businesslogiclayer;

import de.haw.rnp.chat.server.accesslayer.IOutgoingPeerAdapterServices;
import de.haw.rnp.chat.util.AddressType;

public class OutgoingPeerAdapterBusinessLogic implements IOutgoingPeerAdapterServices{

    @Override
    public boolean sendData(AddressType address, byte[] data) {
        return false;
    }
}
