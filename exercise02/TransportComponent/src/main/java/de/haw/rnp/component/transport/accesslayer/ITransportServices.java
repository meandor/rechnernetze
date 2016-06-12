package de.haw.rnp.component.transport.accesslayer;

import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.util.AddressType;

/**
 * Transport Service Interface. This interface defines all available Transport Service interactions.
 */
public interface ITransportServices {

    /**
     * Sends a Text Message
     * @param frame
     */
    void sendMessage(Frame frame);

    void sendLogin(Frame frame);

    void sendLogout(Frame frame);

    void sendUsername(Frame frame);

    void setLocal(AddressType address, String name);

}
