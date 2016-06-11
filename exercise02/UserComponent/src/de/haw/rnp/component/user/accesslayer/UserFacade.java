package de.haw.rnp.component.user.accesslayer;

import de.haw.rnp.component.user.businesslogiclayer.UserBusinessLogic;
import de.haw.rnp.component.user.dataaccesslayer.entities.User;
import de.haw.rnp.component.user.dataaccesslayer.entities.Userlist;

import java.net.InetAddress;

public class UserFacade implements IUserServices{

    private UserBusinessLogic businessLogic;

    public UserFacade(){
        businessLogic = new UserBusinessLogic();
    }

    @Override
    public void createLocalUser(User user) {
        checkUser(user);
        businessLogic.createLocalUser(user);
    }

    @Override
    public User getLocalUser() {
        return businessLogic.getLocalUser();
    }

    @Override
    public void createUser(User user) {
        checkUser(user);
        businessLogic.createUser(user);
    }

    @Override
    public Userlist getAllActiveUsers() {
        return businessLogic.getAllActiveUsers();
    }

    @Override
    public User removeUserFromActiveUsers(User user) {
        checkUser(user);
        return businessLogic.removeUserFromActiveUsers(user);
    }

    @Override
    public User findUserByAddress(InetAddress address) {
        return businessLogic.findUserByAddress(address);
    }

    @Override
    public User findUserByUsername(String username) {
        return businessLogic.findUserByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        checkUser(user);
        businessLogic.updateUser(user);
    }

    @Override
    public void updateLocalUser(User user) {
        checkUser(user);
        businessLogic.updateLocalUser(user);
    }

    private void checkUser(User user){
        if(user.getIp() == null || user.getPort() == 0)
            throw new IllegalArgumentException();
    }
}
