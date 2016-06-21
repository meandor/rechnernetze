package de.haw.rnp.client.view;

import de.haw.rnp.client.IControllerService;
import de.haw.rnp.client.model.User;
import de.haw.rnp.util.AddressType;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Controller responsible only for the views and all data send between them.
 */
public class ViewController {

    public enum ViewState {
        Start,
        Login,
        Chat
    }

    private Stage stage;
    private LoginViewController loginView;
    private ChatViewController chatView;
    private ServerViewController serverView;
    private IControllerService controller;
    private ViewState state;
    private ObservableList<User> users;
    private User local;
    private boolean isTCP;

    public ViewController(Stage stage, IControllerService controller, ObservableList<User> users) {
        this.stage = stage;
        this.controller = controller;
        this.users = users;
        state = ViewState.Start;
        serverView = new ServerViewController(this);
        loginView = new LoginViewController(this);
        chatView = new ChatViewController(this, this.users);
        changeView();
        this.stage.show();
        this.isTCP = true;
    }

    public User getLocal() {
        return local;
    }

    public void changeViewState(ViewState state) {
        this.state = state;
        changeView();
    }

    private void changeView() {
        switch (state) {
            case Start:
                stage.setScene(serverView.initServerView());
                break;
            case Login:
                stage.setScene(loginView.initLoginView());
                break;
            case Chat:
                stage.setScene(chatView.initChatView());
                break;
        }
    }

    public boolean startServer(String hostname, int port) {
        local = new User(hostname, port);
        stage.setTitle(hostname + " : " + port);
        return controller.startServer(new AddressType(hostname, port), isTCP);
    }

    public boolean sendLogin(String username, String hostname, int port) {
        local.setName(username);
        stage.setTitle(username + " - " + stage.getTitle());
        return controller.sendLogin(new AddressType(hostname, port), isTCP);
    }

    public boolean sendMessage(String message, ObservableList<User> recipients) {
        ArrayList<AddressType> addresses = new ArrayList<>();
        for (User user : recipients) {
            addresses.add(user.getAddress());
        }
        return controller.sendMessage(message, addresses, isTCP);
    }

    public void sendLogout() {
        local.setName("");
        controller.sendLogout(isTCP);
    }

    public void appendMessage(AddressType sender, String message) {
        chatView.appendMessage(findUserByAddress(sender), message);
    }

    public boolean isTCP() {
        return isTCP;
    }

    public void setTCP(boolean TCP) {
        isTCP = TCP;
    }

    private User findUserByAddress(AddressType address) {
        for (User user : users) {
            if (user.getAddress().equals(address))
                return user;
        }
        return null;
    }
}
