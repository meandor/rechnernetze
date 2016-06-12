package de.haw.rnp.adapter.outgoingpeer.accesslayer;

import de.haw.rnp.adapter.outgoingpeer.businesslogiclayer.OutgoingPeerAdapterBusinessLogic;
import de.haw.rnp.util.AddressType;

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
