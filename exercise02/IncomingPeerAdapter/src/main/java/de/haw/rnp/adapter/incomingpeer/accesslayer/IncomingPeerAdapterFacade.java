package de.haw.rnp.adapter.incomingpeer.accesslayer;

import de.haw.rnp.adapter.incomingpeer.businesslogiclayer.IncomingPeerAdapterBusinessLogic;
import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServicesForInPeer;
import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.util.AddressType;

public class IncomingPeerAdapterFacade implements IIncomingPeerAdapterServices {

    private IncomingPeerAdapterBusinessLogic businessLogic;

    public IncomingPeerAdapterFacade(ITransportServicesForIncomingPeerAdapter transportServices,
                                     IOutClientAdapterServicesForInPeer outClientAdapterServices){
        businessLogic = new IncomingPeerAdapterBusinessLogic(transportServices, outClientAdapterServices);
    }

    @Override
    public void startServer(AddressType address, boolean TCP) {
        businessLogic.startServer(address, TCP);
    }

    @Override
    public void stopServer() {
        businessLogic.stopServer();
    }

    @Override
    public void startQueueWorker(boolean isTCP) {
        businessLogic.startQueueWorker(isTCP);
    }

    @Override
    public void stopQueueWorker() {
        businessLogic.stopQueueWorker();
    }
}
