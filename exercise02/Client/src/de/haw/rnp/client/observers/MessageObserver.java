package de.haw.rnp.client.observers;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageDTO;
import de.haw.rnp.client.view.ViewController;
import de.haw.rnp.util.IObserver;

public class MessageObserver implements IObserver<MessageDTO>{

    private ViewController controller;

    public MessageObserver(ViewController controller){
        this.controller = controller;
    }

    public void setController(ViewController controller){
        this.controller = controller;
    }

    @Override
    public void update(MessageDTO elem) {
        controller.appendMessage(elem.getSender(), elem.getMessage());
    }
}
