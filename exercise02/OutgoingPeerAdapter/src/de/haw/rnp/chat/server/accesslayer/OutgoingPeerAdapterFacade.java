package de.haw.rnp.chat.server.accesslayer;

import de.haw.rnp.chat.server.businesslogiclayer.OutgoingPeerAdapterBusinessLogic;
import de.haw.rnp.chat.util.AddressType;

public class OutgoingPeerAdapterFacade implements IOutgoingPeerAdapterServices{

    private OutgoingPeerAdapterBusinessLogic businessLogic;

    public OutgoingPeerAdapterFacade(){
        businessLogic = new OutgoingPeerAdapterBusinessLogic();
    }

    @Override
    public boolean sendData(AddressType address, byte[] data) {
        return businessLogic.sendData(address, data);
    }
}
