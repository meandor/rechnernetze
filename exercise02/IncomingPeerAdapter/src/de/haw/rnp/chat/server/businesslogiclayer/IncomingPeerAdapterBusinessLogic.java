package de.haw.rnp.chat.server.businesslogiclayer;

import de.haw.rnp.chat.server.accesslayer.IIncomingPeerAdapterServices;
import de.haw.rnp.chat.server.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.chat.server.dataaccesslayer.QueueWorker;
import de.haw.rnp.chat.server.dataaccesslayer.Server;
import de.haw.rnp.chat.util.AddressType;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IncomingPeerAdapterBusinessLogic implements IIncomingPeerAdapterServices {

    private ITransportServicesForIncomingPeerAdapter transportServices;
    private BlockingQueue<byte[]> queue;
    private Server server;
    private QueueWorker queueWorker;

    public IncomingPeerAdapterBusinessLogic(ITransportServicesForIncomingPeerAdapter transportServices){
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
        server.stop();
    }

    @Override
    public void startQueueWorker() {
        queueWorker = new QueueWorker(transportServices, queue);
        new Thread(queueWorker).start();
    }

    @Override
    public void stopQueueWorker() {
        queueWorker.stop();
    }
}
