package de.haw.rnp.chat.view;

import de.haw.rnp.chat.controller.IControllerService;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class ViewController implements IView {
    private Stage stage;
    private LoginView loginView;
    private ChatView chatView;
    private IControllerService controllerService;
    private boolean isLogged;
    private String userName;

    public ViewController(Stage stage, IControllerService controllerService) {
        this.stage = stage;
        this.controllerService = controllerService;
        this.isLogged = false;
        initLoginView();
    }

    private boolean validateFields(String user, String host, String port, String localHost, String localport) {
        if (user.length() <= 0 && host.length() <= 0 && port.length() <= 0 & localHost.length() <= 0 & localport.length() <= 0)
            return false;
        return true;
    }

    private void initLoginView() {
        loginView = new LoginView();

        loginView.getSignin().setOnAction(action -> {
            String user = loginView.getUserTextField().getText();
            String host = loginView.getHostTextField().getText();
            String port = loginView.getPortTextField().getText();
            String localHost = loginView.getLocalHostTextField().getText();
            String localport = loginView.getLocalPortTextField().getText();
            if (validateFields(user, host, port, localHost, localport)) {
                try {
                    userName = controllerService.login(
                            user,
                            InetAddress.getByName(host),
                            InetAddress.getByName(localHost),
                            Integer.parseInt(port),
                            Integer.parseInt(localport));
                    isLogged = true;
                    initChatView();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        stage.setScene(loginView.getScene());
        stage.show();
    }

    private void initChatView() {
        chatView = new ChatView();

        chatView.getMessageTextArea().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && chatView.getMessageTextArea().getText().length() > 0) {
                String text = chatView.getMessageTextArea().getText();
                String recipient = chatView.getUserlistBox().getValue().toString();
                controllerService.sendMessage(recipient, text);
                chatView.getDisplayTextArea().appendText(userName + ":\n" + text + "\n");
                chatView.getMessageTextArea().clear();
            }
        });

        chatView.getLogoutButton().setOnAction(action -> {
            this.controllerService.logout();
            setUserLoggedOff();
            initLoginView();
        });
        stage.setScene(chatView.getScene());
        stage.show();
    }

    private void setUserLoggedOff() {
        this.chatView = null;
        this.userName = "";
        this.isLogged = false;
        //delete user credentials
    }

    @Override
    public void setUserLoggedIn(String userName) {
        this.isLogged = true;
        this.userName = userName;
        this.loginView = null;
        initChatView();
    }

    @Override
    public void updateUserlist(List<String> usernames) {
        if (isLogged && !usernames.isEmpty()) {
            chatView.getUserlistBox().setItems(FXCollections.observableArrayList(usernames));
            chatView.getUserlistBox().setValue(usernames.get(0));
        } else if (isLogged && usernames.isEmpty()) {
            chatView.getUserlistBox().setItems(FXCollections.observableArrayList("empty"));
            chatView.getUserlistBox().setValue("empty");
        }
    }

    @Override
    public void appendMessage(String from, String message) {
        if (isLogged) {
            chatView.getDisplayTextArea().appendText("Message from " + from + ":\n" + message);
        }
    }
}
