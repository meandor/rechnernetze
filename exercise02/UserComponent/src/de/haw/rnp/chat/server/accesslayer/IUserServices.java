package de.haw.rnp.chat.server.accesslayer;

import de.haw.rnp.chat.server.dataaccesslayer.entities.*;

import java.net.InetAddress;

public interface IUserServices {

    public void createLocalUser(User user);

    public User getLocalUser();

    public void createUser(User user);

    public Userlist getAllActiveUsers();

    public User removeUserFromActiveUsers(User user);

    public User findUserByAddress(InetAddress address);

    public User findUserByUsername(String username);

    public void updateUser(User user);

    public void updateLocalUser(User user);

}
