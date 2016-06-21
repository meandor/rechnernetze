package de.haw.rnp.client;

import de.haw.rnp.util.AddressType;

import java.util.ArrayList;

/**
 * Interface for the Controller.
 */
public interface IControllerService {

    /**
     * Starts a server.
     *
     * @param localAddress AddressType address of the server
     * @param TCP          boolean indicates if the server is using TCP or SCTP
     * @return @code{true] if server is sucessfully created, @code{false} otherwise
     */
    boolean startServer(AddressType localAddress, boolean TCP);

    /**
     * Sends a login message.
     *
     * @param recipient AddressType address of the receiver
     * @param isTCP     boolean indicates if the server is using TCP or SCTP
     * @return @code{true} if sucessfully send, @code{false} otherwise
     */
    boolean sendLogin(AddressType recipient, boolean isTCP);

    /**
     * Sends an actual Message
     *
     * @param message    String text of the message
     * @param recipients List of all recipients
     * @param isTCP      boolean indicates if the server is using TCP or SCTP
     * @return @code{true} if sucessfully send, @code{false} otherwise
     */
    boolean sendMessage(String message, ArrayList<AddressType> recipients, boolean isTCP);

    /**
     * Sends a logout Message.
     *
     * @param isTCP boolean indicates if the server is using TCP or SCTP
     */
    void sendLogout(boolean isTCP);

    //public void sendUsername(String username);

}
