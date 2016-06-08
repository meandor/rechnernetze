package de.haw.rnp.adapter.outgoingclient.accesslayer;

import de.haw.rnp.adapter.outgoingclient.businesslogiclayer.OutgoingClientAdapterBusinessLogic;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageDTO;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.util.IObserver;

public class OutClientAdapterFacade implements IOutClientAdapterServices, IOutClientAdapterServicesForInPeer {

    private OutgoingClientAdapterBusinessLogic businessLogic;

    public OutClientAdapterFacade(){
        businessLogic = new OutgoingClientAdapterBusinessLogic();
    }

    @Override
    public void registerObserverToMessages(IObserver observer) {

    }

    @Override
    public void removeObserverFromMessages(IObserver observer) {

    }

    @Override
    public void registerObserverToUsers(IObserver observer) {

    }

    @Override
    public void removeObserverFromUsers(IObserver observer) {

    }

    @Override
    public void addMessage(MessageDTO message) {

    }

    @Override
    public void addUser(UserDTO user) {

    }

    @Override
    public void removeUser(UserDTO user) {

    }

    @Override
    public void updateUsername(UserDTO user) {

    }
}
