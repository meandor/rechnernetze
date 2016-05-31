package de.haw.rnp.chat.view;

import de.haw.rnp.chat.model.User;

import java.util.concurrent.BlockingQueue;

/**
 * Interface defining the possible actions with the view.
 * <p>
 * This interface is implemented by the ViewController.
 */
public interface IView {

    /**
     * Sets the logged in User
     */
    void setUserLoggedIn();

    /**
     * Updates the user list in the view.
     *
     * @param users BlockingQueue with the users
     */
    void updateUserlist(BlockingQueue<User> users);

    /**
     * Appends a message to the view.
     *
     * @param from    sender String
     * @param message String of the message
     */
    void appendMessage(String from, String message);
}
