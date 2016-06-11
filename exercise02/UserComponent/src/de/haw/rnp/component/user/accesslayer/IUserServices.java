package de.haw.rnp.component.user.accesslayer;

import de.haw.rnp.component.user.dataaccesslayer.entities.*;

import java.net.InetAddress;

public interface IUserServices {

    void createLocalUser(User user);

    User getLocalUser();

    void createUser(User user);

    Userlist getAllActiveUsers();

    User removeUserFromActiveUsers(User user);

    User findUserByAddress(InetAddress address);

    User findUserByUsername(String username);

    void updateUser(User user);

    void updateLocalUser(User user);

}
