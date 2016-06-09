package de.haw.rnp.adapter.outgoingclient.accesslayer;

import de.haw.rnp.adapter.outgoingclient.businesslogiclayer.OutgoingClientAdapterBusinessLogic;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageDTO;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.util.IObserver;

import java.util.Collection;

public class OutClientAdapterFacade implements IOutClientAdapterServices, IOutClientAdapterServicesForInPeer {

    private OutgoingClientAdapterBusinessLogic businessLogic;

    public OutClientAdapterFacade(){
        businessLogic = new OutgoingClientAdapterBusinessLogic();
    }

    @Override
    public void registerObserverToMessages(IObserver observer) {
        businessLogic.registerObserverToMessages(observer);
    }

    @Override
    public void removeObserverFromMessages(IObserver observer) {
        businessLogic.removeObserverFromMessages(observer);
    }

    @Override
    public void registerObserverToUsers(IObserver observer) {
        businessLogic.registerObserverToUsers(observer);
    }

    @Override
    public void removeObserverFromUsers(IObserver observer) {
        businessLogic.removeObserverFromUsers(observer);
    }

    @Override
    public void addMessage(MessageDTO message) {
        businessLogic.addMessage(message);
    }

    @Override
    public void addUser(UserDTO user) {
        businessLogic.addUser(user);
    }

    @Override
    public void removeUser(UserDTO user) {
        businessLogic.removeUser(user);
    }

    @Override
    public void updateUsername(UserDTO user) {
        businessLogic.updateUsername(user);
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return businessLogic.getAllUsers();
    }
}
