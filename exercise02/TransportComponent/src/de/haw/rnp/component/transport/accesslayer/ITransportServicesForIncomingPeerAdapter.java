package de.haw.rnp.component.transport.accesslayer;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;

import java.util.Collection;

public interface ITransportServicesForIncomingPeerAdapter {

    Frame receiveFrameAsBytes(byte[] bytes);

    void propagatePeer(Frame frame, Collection<UserDTO> recipients);

    void propagateLogout(Frame frame, Collection<UserDTO> recipients);

    boolean checkLocal(Frame frame);

}
