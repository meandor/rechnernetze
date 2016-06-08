package de.haw.rnp.adapter.outgoingclient.accesslayer;

import de.haw.rnp.adapter.outgoingclient.businesslogiclayer.OutgoingClientAdapterBusinessLogic;
import de.haw.rnp.util.IObserver;

public class OutClientAdapterFacade implements IOutClientAdapterServices, IOutClientAdapterServicesForInPeer {

    private OutgoingClientAdapterBusinessLogic businessLogic;

    @Override
    public void addMessage() {

    }

    @Override
    public void addUser() {

    }

    @Override
    public void removeUser() {

    }

    @Override
    public void updateUser() {

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
}
