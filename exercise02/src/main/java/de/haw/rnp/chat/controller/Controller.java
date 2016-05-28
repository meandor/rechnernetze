package de.haw.rnp.chat.controller;

import de.haw.rnp.chat.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by daniel on 28.05.16.
 */
public class Controller {
    private BlockingQueue messageQueue;
    private User loggedInUser;
    private static Controller controller = new Controller();
    private List<User> userList;

    private Controller() {
        this.messageQueue = new LinkedBlockingQueue();
        this.userList = new ArrayList<>();
    }

    public static Controller getInstance() {
        return controller;
    }
}
