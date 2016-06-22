package de.haw.rnp.adapter.incomingpeer.businesslogiclayer;

import de.haw.rnp.adapter.incomingpeer.accesslayer.IIncomingPeerAdapterServices;
import de.haw.rnp.adapter.incomingpeer.dataaccesslayer.QueueWorker;
import de.haw.rnp.adapter.incomingpeer.dataaccesslayer.SCTPServer;
import de.haw.rnp.adapter.incomingpeer.dataaccesslayer.Server;
import de.haw.rnp.adapter.incomingpeer.dataaccesslayer.TCPServer;
import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServicesForInPeer;
import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
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
        this.queue = new LinkedBlockingQueue<>(20);
    }

    @Override
    public void startServer(AddressType address, boolean TCP) {
        if (TCP) {
            server = new TCPServer(address.getPort(), queue);
        } else {
            server = new SCTPServer(address.getPort(), queue);
        }
        new Thread(server).start();
    }

    @Override
    public void stopServer() {
        if(server != null)
            server.stop();
    }

    @Override
    public void startQueueWorker(boolean isTCP) {
        queueWorker = new QueueWorker(transportServices, outClientAdapterServices, queue, isTCP);
        new Thread(queueWorker).start();
    }

    @Override
    public void stopQueueWorker() {
        if(queueWorker != null)
            queueWorker.stop();
    }
}
