package de.haw.rnp.chat.view;

import java.util.List;

public interface IView {
    void setUserLoggedIn(String userName);

    void updateUserlist(List<String> usernames);

    void appendMessage(String from, String message);
}
