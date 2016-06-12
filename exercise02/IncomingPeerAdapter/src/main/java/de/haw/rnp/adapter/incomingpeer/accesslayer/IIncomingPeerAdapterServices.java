package de.haw.rnp.adapter.incomingpeer.accesslayer;

import de.haw.rnp.util.AddressType;

public interface IIncomingPeerAdapterServices {

    void startServer(AddressType address);

    void stopServer();

    void startQueueWorker();

    void stopQueueWorker();

}
