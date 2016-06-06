package de.haw.rnp.chat.server.accesslayer;

public interface ITransportServicesForIncomingPeerAdapter {

    public void recieveFrameAsBytes(byte[] bytes);

}
