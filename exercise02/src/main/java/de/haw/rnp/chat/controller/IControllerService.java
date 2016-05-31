package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.Message;
import de.haw.rnp.chat.model.User;

import java.net.InetAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Interface for the Controller.
 */
public interface IControllerService {

    /**
     * Login process for a user.
     *
     * @param userName     String username
     * @param address      InetAddress of the active peer the request is sent to
     * @param localAddress InetAddress of the user loggin in
     * @param port         int port of the active peer
     * @param localport    InetAddress of the user loggin in
     * @return @code{true} if sucessfully logged in, @code{false} otherwise
     */
    boolean login(String userName, InetAddress address, InetAddress localAddress, int port, int localport);

    /**
     * Sends a logout request for the currently logged in user.
     */
    void logout();

    /**
     * Sends a message to a String of recipients, split by ",".
     *
     * @param recipient String of the receiving users
     * @param message   String of the actual message
     * @return @code{true} if successfully send, @code{false} otherwise
     */
    boolean sendMessage(String recipient, String message);

    /**
     * Starts the server and begins the reading incoming messages process.
     *
     * @param hostName InetAddress of the server
     * @param port     int port of the server
     * @return @code{true} if successfully started, @code{false} otherwise
     */
    boolean startServer(InetAddress hostName, int port);

    /**
     * Adds a Message to the Queue
     *
     * @param message Message actual Message
     * @return @code{true} if successfully added, @code{false} otherwise
     */
    boolean addMessageToQueue(Message message);

    /**
     * Adds a User to the userlist
     *
     * @param u User to be added
     * @return @code{true} if successfully added, @code{false} otherwise
     */
    boolean addUser(User u);

    /**
     * Removes a User from the userlist
     *
     * @param u User to be removed
     * @return @code{true} if successfully removed, @code{false} otherwise
     */
    boolean removeUser(User u);

    /**
     * Returns the currently logged in User.
     *
     * @return currently logged in User
     */
    User getLoggedInUser();

    /**
     * Sets the currently logged in User.
     *
     * @param u User to be set
     */
    void setLoggedInUser(User u);

    /**
     * Returns the User list with all available User.
     *
     * @return BlockingQueue of all availabe user
     */
    BlockingQueue<User> getUserList();

    /**
     * Returns the threadpool
     *
     * @return treadpool
     */
    ExecutorService getExecutor();
}
