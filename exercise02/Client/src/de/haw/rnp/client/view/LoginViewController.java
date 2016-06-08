package de.haw.rnp.client.view;

import javafx.scene.Scene;

public class LoginViewController {

    private LoginView loginView;

    public Scene initLoginView() {
        loginView = new LoginView();
        initOnEvents();
        return loginView.getScene();
    }

    private void initOnEvents(){
        loginView.getSignin().setOnAction(action -> {
            String user = loginView.getUserTextField().getText() + " - me";
            String host = loginView.getHostTextField().getText();
            String port = loginView.getPortTextField().getText();
            try {
                if (controller.login(user, InetAddress.getByName(host), serverHostName, Integer.parseInt(port), serverPort)) {
                    initChatView(controller.getUserList());
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });
    }

}
