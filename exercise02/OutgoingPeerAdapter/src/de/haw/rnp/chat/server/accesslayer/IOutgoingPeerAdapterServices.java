package de.haw.rnp.chat.server.accesslayer;

import de.haw.rnp.chat.util.AddressType;

public interface IOutgoingPeerAdapterServices {
    public boolean sendData(AddressType address, byte[] data);
}
