package de.haw.rnp.adapter.outgoingclient.dataaccesslayer;

import de.haw.rnp.util.AddressType;
import de.haw.rnp.util.IObserver;
import de.haw.rnp.util.ISubject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;

public class UserSubject implements ISubject{

    private ArrayList<IObserver> observers = new ArrayList<>();
    private ArrayList<UserDTO> users = new ArrayList<>();

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(IObserver<ArrayList<UserDTO>> observer : observers){
            observer.update(users);
        }
    }

    public ArrayList<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserDTO> users) {
        this.users = users;
        notifyObservers();
    }

    public void addUser(UserDTO user){
        users.add(user);
        notifyObservers();
    }

    public void removeUserByAddressType(AddressType address){
        users.remove(findUserByAddressType(address));
        notifyObservers();
    }

    public void updateUsername(UserDTO user){
        users.remove(findUserByAddressType(user.getAddress()));
        addUser(user);
    }

    private UserDTO findUserByAddressType(AddressType address){
        for(UserDTO user : users){
            if(user.getAddress().getIp().equals(address.getIp()) &&
               user.getAddress().getPort() == address.getPort())
                return user;
        }
        return null;
    }
}
