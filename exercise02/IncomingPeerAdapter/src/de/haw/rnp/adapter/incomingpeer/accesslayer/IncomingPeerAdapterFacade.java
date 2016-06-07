package de.haw.rnp.adapter.incomingpeer.accesslayer;

import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.adapter.incomingpeer.businesslogiclayer.IncomingPeerAdapterBusinessLogic;
import de.haw.rnp.util.AddressType;

public class IncomingPeerAdapterFacade implements IIncomingPeerAdapterServices {

    private IncomingPeerAdapterBusinessLogic businessLogic;

    public IncomingPeerAdapterFacade(ITransportServicesForIncomingPeerAdapter transportServices){
        businessLogic = new IncomingPeerAdapterBusinessLogic(transportServices);
    }

    @Override
    public void startServer(AddressType address) {
        businessLogic.startServer(address);
    }

    @Override
    public void stopServer() {
        businessLogic.stopServer();
    }

    @Override
    public void startQueueWorker() {
        businessLogic.startQueueWorker();
    }

    @Override
    public void stopQueueWorker() {
        businessLogic.stopQueueWorker();
    }
}
