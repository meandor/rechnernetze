package de.haw.rnp.client.observers;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.client.model.User;
import de.haw.rnp.util.AddressType;
import de.haw.rnp.util.IObserver;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class UserlistObserver implements IObserver<ArrayList<UserDTO>> {

    private ObservableList<User> users;

    public UserlistObserver(ObservableList<User> users){
        this.users = users;
    }

    @Override
    public void update(ArrayList<UserDTO> elem) {
        for(UserDTO e : elem){
            User tmp = findUserByAddress(e.getAddress());
            if(tmp != null){
                int i = users.indexOf(tmp);
                tmp.setName(e.getName());
                users.set(i, tmp);
            }else{
                users.add(new User(e.getName(), e.getAddress()));
            }
        }
    }

    private User findUserByAddress(AddressType address){
        for(User user : users){
            if(user.getAddress().equals(address))
                return user;
        }
        return null;
    }
}
