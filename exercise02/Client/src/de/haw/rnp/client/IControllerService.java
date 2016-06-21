package de.haw.rnp.client;

import de.haw.rnp.util.AddressType;

import java.util.ArrayList;

/**
 * Interface for the Controller.
 */
public interface IControllerService {

    boolean startServer(AddressType localAddress, boolean TCP);

    boolean sendLogin(AddressType recipient, boolean isTCP);

    boolean sendMessage(String message, ArrayList<AddressType> recipients, boolean isTCP);

    void sendLogout(boolean isTCP);

    //public void sendUsername(String username);

}
