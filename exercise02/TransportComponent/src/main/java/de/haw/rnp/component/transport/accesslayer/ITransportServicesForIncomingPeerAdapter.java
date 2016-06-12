package de.haw.rnp.component.transport.accesslayer;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;

import java.util.Collection;

/**
 * Interface for the IncomingPeers and the TransportServices.
 * <p>
 * Defines the available functions provided by the TransportService for the Incoming Peers.
 */
public interface ITransportServicesForIncomingPeerAdapter {

    /**
     * Receives a Frame as a byte stream.
     *
     * @param bytes byte[] incoming byte stream
     * @return Frame representation of the bytes
     */
    Frame receiveFrameAsBytes(byte[] bytes);

    /**
     * Propagate the Frame to all the recipients
     *
     * @param frame      Frame with info
     * @param recipients Collection<UserDTO> with all recipients
     */
    void propagatePeer(Frame frame, Collection<UserDTO> recipients);

    /**
     * Propagate a Logout to all the recipients
     *
     * @param frame      Frame with info
     * @param recipients Collection<UserDTO> with all recipients
     */
    void propagateLogout(Frame frame, Collection<UserDTO> recipients);

    /**
     * TODO: Sebastian
     *
     * @param frame
     * @return
     */
    boolean checkLocal(Frame frame);
}
