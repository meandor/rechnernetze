package de.haw.rnp.client.view;

import de.haw.rnp.client.IControllerService;
import de.haw.rnp.client.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class ViewController implements IView {
    private Stage stage;
    private LoginViewController loginView;
    private ChatViewController chatView;
    private ServerViewController serverView;
    private IControllerService controller;

    public ViewController(Stage stage, IControllerService controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void appendMessage(String from, String message) {
        if (controller.getLoggedInUser() != null) {
            chatView.getDisplayTextArea().appendText("Message from " + from + ":\n" + message);
        }
    }
}
