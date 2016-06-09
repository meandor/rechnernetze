package de.haw.rnp.client;

import de.haw.rnp.client.model.User;
import de.haw.rnp.client.observers.MessageObserver;
import de.haw.rnp.client.observers.UserlistObserver;
import de.haw.rnp.client.view.ViewController;
import de.haw.rnp.util.AddressType;
import javafx.collections.ObservableList;

import javax.swing.text.View;
import java.util.ArrayList;

public class Controller implements IControllerService {

    private ObservableList<User> users;
    private ViewController viewController;
    private MessageObserver messageObserver;
    private UserlistObserver userlistObserver;
    private IIncomingClientAdapterServices adapterServices;

    public Controller(ObservableList<User> users){
        this.users = users;
    }

    @Override
    public boolean startServer(AddressType localAddress) {
        return false;
    }

    @Override
    public boolean sendLogin(String username, AddressType recipient) {
        return false;
    }

    @Override
    public boolean sendMessage(String message, ArrayList<AddressType> recipients) {
        return false;
    }

    @Override
    public void sendLogout() {

    }
}
