package de.haw.rnp.adapter.outgoingclient.businesslogiclayer;

import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServices;
import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServicesForInPeer;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageDTO;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageSubject;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserSubject;
import de.haw.rnp.util.IObserver;

import java.util.Collection;

public class OutgoingClientAdapterBusinessLogic implements IOutClientAdapterServices, IOutClientAdapterServicesForInPeer {

    private MessageSubject messageSubject;
    private UserSubject userSubject;

    public OutgoingClientAdapterBusinessLogic(){
        messageSubject = new MessageSubject();
        userSubject = new UserSubject();
    }

    @Override
    public void registerObserverToMessages(IObserver observer) {
        messageSubject.registerObserver(observer);
    }

    @Override
    public void removeObserverFromMessages(IObserver observer) {
        messageSubject.removeObserver(observer);
    }

    @Override
    public void registerObserverToUsers(IObserver observer) {
        userSubject.registerObserver(observer);
    }

    @Override
    public void removeObserverFromUsers(IObserver observer) {
        userSubject.removeObserver(observer);
    }

    @Override
    public void addMessage(MessageDTO message) {
        messageSubject.addMessage(message);
    }

    @Override
    public void addUser(UserDTO user) {
        if(!getAllUsers().contains(user))
            userSubject.addUser(user);
    }

    @Override
    public void removeUser(UserDTO user) {
        userSubject.removeUserByAddressType(user.getAddress());
    }

    @Override
    public void updateUsername(UserDTO user) {
        userSubject.updateUsername(user);
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return userSubject.getUsers();
    }
}
