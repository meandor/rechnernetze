package de.haw.rnp.component.transport.accesslayer;

import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;

public interface ITransportServicesForIncomingPeerAdapter {

    Frame receiveFrameAsBytes(byte[] bytes);

}
