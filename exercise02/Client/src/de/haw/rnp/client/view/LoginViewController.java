package de.haw.rnp.client.view;

import javafx.scene.Scene;

public class LoginViewController {

    private LoginView loginView;
    private ViewController controller;

    public LoginViewController(ViewController controller){
        this.controller = controller;
    }

    public Scene initLoginView() {
        loginView = new LoginView();
        initOnEvents();
        return loginView.getScene();
    }

    private void initOnEvents(){
        loginView.getSignin().setOnAction(action -> {
            String user = loginView.getUserTextField().getText();
            String host = loginView.getHostTextField().getText();
            int port = Integer.parseInt(loginView.getPortTextField().getText());

            if(controller.sendLogin(user, host, port)){
                controller.changeViewState(ViewController.ViewState.Chat);
            }
        });

        loginView.getSignin().defaultButtonProperty().bind(loginView.getSignin().focusedProperty());
    }

}
