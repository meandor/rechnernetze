package de.haw.rnp.adapter.outgoingclient.dataaccesslayer;

import de.haw.rnp.util.IObserver;
import de.haw.rnp.util.ISubject;

import java.util.ArrayList;

public class MessageSubject implements ISubject {

    private ArrayList<IObserver> observers = new ArrayList<IObserver>();

    @Override
    public void registerObserver(IObserver observer) {

    }

    @Override
    public void removeObserver(IObserver observer) {

    }

    @Override
    public void notifyObservers() {

    }
}
