package de.haw.rnp.client;

import de.haw.rnp.util.AddressType;

import java.util.ArrayList;

/**
 * Interface for the Controller.
 */
public interface IControllerService {

    public boolean startServer(AddressType localAddress, boolean TCP);

    public boolean sendLogin(AddressType recipient);

    public boolean sendMessage(String message, ArrayList<AddressType> recipients);

    public void sendLogout();

    //public void sendUsername(String username);

}
