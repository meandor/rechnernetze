package de.haw.rnp.chat.server.businesslogiclayer;

import de.haw.rnp.chat.server.accesslayer.IUserServices;
import de.haw.rnp.chat.server.dataaccesslayer.entities.User;
import de.haw.rnp.chat.server.dataaccesslayer.entities.Userlist;

import java.net.InetAddress;

public class UserBusinessLogic implements IUserServices {
    @Override
    public void createLocalUser(User user) {

    }

    @Override
    public User getLocalUser() {
        return null;
    }

    @Override
    public void createUser(User user) {

    }

    @Override
    public Userlist getAllActiveUsers() {
        return null;
    }

    @Override
    public User removeUserFromActiveUsers(User user) {
        return null;
    }

    @Override
    public User findUserByAddress(InetAddress address) {
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void updateLocalUser(User user) {

    }
}
