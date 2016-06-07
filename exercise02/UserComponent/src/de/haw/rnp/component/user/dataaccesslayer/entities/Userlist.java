package de.haw.rnp.component.user.dataaccesslayer.entities;

import de.haw.rnp.util.ChatUtil;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;

public class Userlist {
    String groupname;
    Collection<User> users;

    public Userlist(String groupname){
        this.groupname = groupname;
        users = new ArrayList<>();
    }

    public void addUSer(User user){
        users.add(user);
    }

    public User removeUserByIp(InetAddress ip){
        User user = findUserByIp(ip);
        users.remove(user);
        return user;
    }

    public User removeUserByUsername(String username){
        User user = findUserByUsername(username);
        users.remove(user);
        return user;
    }

    public User findUserByIp(InetAddress ip){
        return users.stream().filter(user -> user.getIp().equals(ip)).findFirst().orElse(null);
    }

    public User findUserByUsername(String username){
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public byte[] getUserlistAsBytes(){
        byte[] result = new byte[0];
        byte[] reserved = new byte[]{0x0, 0x0};
        for(User user : users){
            ChatUtil.concat(result, user.getAddressAsBytes(), reserved, user.getPortAsBytes());
        }
        return result;
    }
}
