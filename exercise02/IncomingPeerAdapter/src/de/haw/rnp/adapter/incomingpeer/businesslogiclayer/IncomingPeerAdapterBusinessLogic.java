package de.haw.rnp.adapter.incomingpeer.businesslogiclayer;

import de.haw.rnp.adapter.incomingpeer.accesslayer.IIncomingPeerAdapterServices;
import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServices;
import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServicesForInPeer;
import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.adapter.incomingpeer.dataaccesslayer.QueueWorker;
import de.haw.rnp.adapter.incomingpeer.dataaccesslayer.Server;
import de.haw.rnp.util.AddressType;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IncomingPeerAdapterBusinessLogic implements IIncomingPeerAdapterServices {

    private ITransportServicesForIncomingPeerAdapter transportServices;
    private IOutClientAdapterServicesForInPeer outClientAdapterServices;
    private BlockingQueue<byte[]> queue;
    private Server server;
    private QueueWorker queueWorker;

    public IncomingPeerAdapterBusinessLogic(ITransportServicesForIncomingPeerAdapter transportServices,
                                            IOutClientAdapterServicesForInPeer outClientAdapterServices){
        this.outClientAdapterServices = outClientAdapterServices;
        this.transportServices = transportServices;
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void startServer(AddressType address) {
        server = new Server(address.getPort(), queue);
        new Thread(server).start();
    }

    @Override
    public void stopServer() {
        if(server != null)
            server.stop();
    }

    @Override
    public void startQueueWorker() {
        queueWorker = new QueueWorker(transportServices, outClientAdapterServices, queue);
        new Thread(queueWorker).start();
    }

    @Override
    public void stopQueueWorker() {
        if(queueWorker != null)
            queueWorker.stop();
    }
}
