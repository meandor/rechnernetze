package de.haw.rnp.component.transport.accesslayer;

import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.util.AddressType;

/**
 * Transport Service Interface. This interface defines all available Transport Service interactions.
 */
public interface ITransportServices {

    /**
     * Sends a Text Message.
     * @param frame Frame containing more info
     */
    void sendMessage(Frame frame);

    /**
     * Sends a Login Message.
     * @param frame Frame containing required info
     */
    void sendLogin(Frame frame);

    /**
     * Sends a Logout Message.
     * @param frame Frame containing required info
     */
    void sendLogout(Frame frame);

    /**
     * Sends a Username Message.
     * @param frame Frame containing required info
     */
    void sendUsername(Frame frame);

    /**
     * TODO: Sebastian
     * @param address
     * @param name
     */
    void setLocal(AddressType address, String name);

}
