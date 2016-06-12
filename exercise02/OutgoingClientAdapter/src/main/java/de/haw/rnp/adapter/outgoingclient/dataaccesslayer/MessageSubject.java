package de.haw.rnp.adapter.outgoingclient.dataaccesslayer;

import de.haw.rnp.util.IObserver;
import de.haw.rnp.util.ISubject;

import java.util.ArrayList;

public class MessageSubject implements ISubject {

    private ArrayList<IObserver> observers = new ArrayList<>();
    private MessageDTO lastMessage;

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
        for(IObserver<MessageDTO> observer : observers){
            observer.update(lastMessage);
        }
    }

    public void addMessage(MessageDTO message){
        lastMessage = message;
        notifyObservers();
    }
}
