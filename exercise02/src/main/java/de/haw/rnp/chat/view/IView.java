package de.haw.rnp.chat.view;

import de.haw.rnp.chat.model.Message;

import java.util.ArrayList;
import java.util.List;

public interface IView {
    public void setUserLoggedIn(String userName);
    public void updateUserlist(List<String> usernames);
    public void appendMessage(String from, String message);
}
