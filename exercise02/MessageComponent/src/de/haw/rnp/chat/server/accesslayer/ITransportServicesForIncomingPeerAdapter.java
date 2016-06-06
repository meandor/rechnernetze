package de.haw.rnp.chat.server.accesslayer;

import de.haw.rnp.chat.server.dataaccesslayer.entities.Frame;

public interface ITransportServicesForIncomingPeerAdapter {

    public Frame recieveFrameAsBytes(byte[] bytes);

}
