package de.haw.rnp.client.view;

import javafx.scene.Scene;

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

            if (controller.startServer(hostname, port, serverView.isTCP())) {
                controller.changeViewState(ViewController.ViewState.Login);
            }
        });

        serverView.getComboBox().valueProperty().addListener((ov, t, t1) -> {
            if (t1.equals("TCP")) {
                serverView.setTCP(true);
            } else {
                serverView.setTCP(false);
            }
        });

        serverView.getStartServer().defaultButtonProperty().bind(serverView.getStartServer().focusedProperty());
    }

}
