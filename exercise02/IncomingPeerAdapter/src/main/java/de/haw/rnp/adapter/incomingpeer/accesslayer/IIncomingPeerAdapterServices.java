package de.haw.rnp.adapter.incomingpeer.accesslayer;

import de.haw.rnp.util.AddressType;

public interface IIncomingPeerAdapterServices {

    void startServer(AddressType address, boolean TCP);

    void stopServer();

    void startQueueWorker(boolean isTCP);

    void stopQueueWorker();

}
