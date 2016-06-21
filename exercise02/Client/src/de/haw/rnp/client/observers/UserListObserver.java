package de.haw.rnp.client.observers;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.client.model.User;
import de.haw.rnp.util.IObserver;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Observer used to observe the User list.
 */
public class UserListObserver implements IObserver<ArrayList<UserDTO>> {

    private ObservableList<User> users;

    public UserListObserver(ObservableList<User> users) {
        this.users = users;
    }

    @Override
    public void update(ArrayList<UserDTO> elem) {
        Platform.runLater(() -> users.clear());

        for (UserDTO e : elem) {
            Platform.runLater(() -> users.add(new User(e.getName(), e.getAddress())));
        }
    }
}
