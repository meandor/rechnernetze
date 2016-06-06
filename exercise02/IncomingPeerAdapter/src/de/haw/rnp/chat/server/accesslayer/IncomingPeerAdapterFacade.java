package de.haw.rnp.chat.server.accesslayer;

import de.haw.rnp.chat.server.businesslogiclayer.IncomingPeerAdapterBusinessLogic;
import de.haw.rnp.chat.util.AddressType;

public class IncomingPeerAdapterFacade implements IIncomingPeerAdapterServices {

    private IncomingPeerAdapterBusinessLogic businessLogic;

    public IncomingPeerAdapterFacade(ITransportServicesForIncomingPeerAdapter transportServices){
        businessLogic = new IncomingPeerAdapterBusinessLogic(transportServices);
    }

    @Override
    public void startServer(AddressType address) {

    }

    @Override
    public void startQueueWorker() {

    }
}
