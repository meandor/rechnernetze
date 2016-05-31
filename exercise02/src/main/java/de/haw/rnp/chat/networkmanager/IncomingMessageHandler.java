package de.haw.rnp.chat.networkmanager;

import de.haw.rnp.chat.controller.IControllerService;
import de.haw.rnp.chat.model.User;

import java.net.InetAddress;
import java.util.List;
import java.util.Queue;

/**
 * Interface for handling incoming protocol messages.
 */
public interface IncomingMessageHandler {

    /**
     * Processes the message and parses it and forwards it to the corresponding method.
     *
     * @param byteStream byte[] with the raw input stream
     */
    void processMessage(byte[] byteStream);

    /**
     * Login message for telling the system new User are available.
     * <p>
     * The message is propagated to all known clients.
     *
     * @param hostName InetAddress of the user loggin in
     * @param port     int port number of the user loggin in
     * @param userName String username of the user loggin in
     */
    void login(InetAddress hostName, int port, String userName);

    /**
     * Changes or sets the username of a User.
     *
     * @param hostName InetAdddress of the User to be changed
     * @param port     int port number of the User to be changed
     * @param userName String new name for the user
     */
    void setUserName(InetAddress hostName, int port, String userName);

    /**
     * Logout of the given User
     *
     * @param hostName InetAddress of the User
     * @param port     int port Number of the User
     */
    void logout(InetAddress hostName, int port);

    /**
     * Forwards the message to all known Users.
     * <p>
     * The common header is changed to the correct sender port and IP.
     *
     * @param message byte[] message to be forwarded
     * @param users   List of all known users
     */
    void propagate(byte[] message, Queue<User> users);

    /**
     * Receives an actual text message.
     *
     * @param text  String text that is received
     * @param users List all user that message is meant for
     */
    void receiveMessage(String text, User sender, List<User> users);
}
