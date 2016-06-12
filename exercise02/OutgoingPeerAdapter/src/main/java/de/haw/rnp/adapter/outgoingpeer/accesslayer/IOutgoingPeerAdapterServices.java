package de.haw.rnp.adapter.outgoingpeer.accesslayer;

import de.haw.rnp.util.AddressType;

public interface IOutgoingPeerAdapterServices {
    boolean sendData(AddressType address, byte[] data);
}
