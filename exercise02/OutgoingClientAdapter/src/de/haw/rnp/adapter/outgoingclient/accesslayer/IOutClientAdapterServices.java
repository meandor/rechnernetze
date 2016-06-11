package de.haw.rnp.adapter.outgoingclient.accesslayer;

import de.haw.rnp.util.IObserver;

public interface IOutClientAdapterServices {

    public void registerObserverToMessages(IObserver observer);

    public void removeObserverFromMessages(IObserver observer);

    public void registerObserverToUsers(IObserver observer);

    public void removeObserverFromUsers(IObserver observer);

}
