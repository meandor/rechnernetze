package de.haw.rnp.client.view;

import javafx.scene.Scene;

/**
 * Controller for the ServerView.
 */
public class ServerViewController {

    private ServerView serverView;
    private ViewController controller;

    public ServerViewController(ViewController controller) {
        this.controller = controller;
    }

    public Scene initServerView() {
        serverView = new ServerView();
        initOnEvents();
        return serverView.getScene();
    }

    private void initOnEvents() {
        serverView.getStartServer().setOnAction(event -> {
            String hostname = serverView.getHostNameField().getText();
            int port = Integer.parseInt(serverView.getPortField().getText());

            if (controller.startServer(hostname, port)) {
                controller.changeViewState(ViewController.ViewState.Login);
            }
        });

        serverView.getComboBox().valueProperty().addListener((ov, t, t1) -> {
            if (t1.equals("TCP")) {
                controller.setTCP(true);
            } else {
                controller.setTCP(false);
            }
        });

        serverView.getStartServer().defaultButtonProperty().bind(serverView.getStartServer().focusedProperty());
    }

}
