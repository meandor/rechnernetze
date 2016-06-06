package de.haw.rnp.chat.server.businesslogiclayer;

import de.haw.rnp.chat.server.accesslayer.IIncomingPeerAdapterServices;
import de.haw.rnp.chat.server.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.chat.util.AddressType;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IncomingPeerAdapterBusinessLogic implements IIncomingPeerAdapterServices {

    private ITransportServicesForIncomingPeerAdapter transportServices;
    private BlockingQueue queue;

    public IncomingPeerAdapterBusinessLogic(ITransportServicesForIncomingPeerAdapter transportServices){
        this.transportServices = transportServices;
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void startServer(AddressType address) {

    }

    @Override
    public void startQueueWorker() {

    }
}
