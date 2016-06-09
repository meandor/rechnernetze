package de.haw.rnp.client;

import de.haw.rnp.client.model.Message;
import de.haw.rnp.client.model.User;
import de.haw.rnp.util.AddressType;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Interface for the Controller.
 */
public interface IControllerService {

    public boolean startServer(AddressType localAddress);

    public boolean sendLogin(AddressType recipient);

    public boolean sendMessage(String message, ArrayList<AddressType> recipients);

    public void sendLogout();

    //public void sendUsername(String username);

}
