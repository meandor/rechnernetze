package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Interface for handling outgoing protocol messages.
 */
public interface OutgoingMessageHandler {

    /**
     * Login a User with the given parameter.
     *
     * @param activePeerHostName client hostname of the active peer
     * @param activePeerPort     client port of the active peer
     * @param loginName          name of the user
     * @param loginHostName      hostname of the user
     * @param loginPort          port of the user (server)
     * @return newly logged in User
     */
    User login(InetAddress activePeerHostName, int activePeerPort, String loginName, InetAddress loginHostName, int loginPort);

    /**
     * Logs a User out.
     *
     * @param activePeerHostName client hostname of the active peer
     * @param activePeerPort     client port of the active peer
     * @param logoutHostName     hostname of the user logging out
     * @param logoutPort         port of the user logging out
     */
    void logout(InetAddress activePeerHostName, int activePeerPort, InetAddress logoutHostName, int logoutPort);

    /**
     * Sending the message to the recipients
     *
     * @param message Message containing the actual text, receiver and sender
     */
    void sendMessage(Message message);

    /**
     * Sends the myName message to the supplied active peer.
     *
     * @param recipients   List of all recipients for my name message
     * @param name         name to be send
     * @param nameHostName hostname of the users name
     * @param namePort     port of the users name
     */
    void sendName(List<User> recipients, String name, InetAddress nameHostName, int namePort);

    /**
     * Establishes a client connection to an active peer.
     *
     * @param hostName hostname to the active peer
     * @param port     port of the active peer
     * @return Node with the client connection to the active peer
     */
    Node initialConnect(InetAddress hostName, int port);

    /**
     * Returns the factory.
     *
     * @return NodeFactory
     */
    NodeFactory getFactory();

    /**
     * Returns the Executor.
     *
     * @return ExecutorService
     */
    ExecutorService getExecutor();
}
