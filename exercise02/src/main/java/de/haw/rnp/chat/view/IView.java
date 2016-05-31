package de.haw.rnp.chat.view;

import de.haw.rnp.chat.model.User;

import java.util.concurrent.BlockingQueue;

public interface IView {
    void setUserLoggedIn();

    void updateUserlist(BlockingQueue<User> users);

    void appendMessage(String from, String message);
}
