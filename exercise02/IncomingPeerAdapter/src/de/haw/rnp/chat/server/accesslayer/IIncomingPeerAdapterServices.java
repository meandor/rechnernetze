package de.haw.rnp.chat.server.accesslayer;

import de.haw.rnp.chat.util.AddressType;

public interface IIncomingPeerAdapterServices {

    public void startServer(AddressType address);

    public void stopServer();

    public void startQueueWorker();

    public void stopQueueWorker();

}
