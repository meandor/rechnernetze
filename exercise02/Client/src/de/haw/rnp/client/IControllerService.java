package de.haw.rnp.client;

import de.haw.rnp.client.model.Message;
import de.haw.rnp.client.model.User;
import de.haw.rnp.util.AddressType;

import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Interface for the Controller.
 */
public interface IControllerService {

    public void startServer(AddressType localAddress);

    public void sendLogin(String username, AddressType recipient);

    public void sendMessage();

    public void sendLogout();

}
