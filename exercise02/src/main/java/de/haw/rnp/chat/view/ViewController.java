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
    private ServerView serverView;
    private IControllerService controllerService;
    private boolean loggedIn;
    private String userName;
    private InetAddress serverHostName;
    private int serverPort;

    public ViewController(Stage stage, IControllerService controllerService) {
        this.stage = stage;
        this.controllerService = controllerService;
        this.loggedIn = false;
        initServerView();
    }

    private boolean validateFields(String user, String host, String port) {
        return (user.length() > 0 && host.length() > 0 && port.length() > 0);
    }

    private void initServerView() {
        this.serverView = new ServerView();

        this.serverView.getStartServer().setOnAction(event -> {
            String hostName = serverView.getHostNameField().getText();
            String port = serverView.getPortField().getText();

            if (validateFields("1", hostName, port)) {
                InetAddress serverHostName = null;
                try {
                    serverHostName = InetAddress.getByName(hostName);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                int serverPort = Integer.parseInt(port);
                if (controllerService.startServer(serverHostName, serverPort)) {
                    this.serverHostName = serverHostName;
                    this.serverPort = serverPort;
                    initLoginView();
                }
            }
        });

        stage.setScene(serverView.getScene());
        stage.show();
    }

    private void initLoginView() {
        loginView = new LoginView();

        loginView.getSignin().setOnAction(action -> {
            String user = loginView.getUserTextField().getText();
            String host = loginView.getHostTextField().getText();
            String port = loginView.getPortTextField().getText();
            if (validateFields(user, host, port)) {
                try {
                    userName = controllerService.login(user, InetAddress.getByName(host), serverHostName, Integer.parseInt(port), serverPort);
                    loggedIn = true;
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
                chatView.getDisplayTextArea().appendText(userName + "send to" + recipient + ":\n" + text + "\n");
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
        this.loggedIn = false;
        //delete user credentials
    }

    @Override
    public void setUserLoggedIn(String userName) {
        this.loggedIn = true;
        this.userName = userName;
        this.loginView = null;
        initChatView();
    }

    @Override
    public void updateUserlist(List<String> usernames) {
        if (loggedIn && !usernames.isEmpty()) {
            chatView.getUserlistBox().setItems(FXCollections.observableArrayList(usernames));
            chatView.getUserlistBox().setValue(usernames.get(0));
        } else if (loggedIn && usernames.isEmpty()) {
            chatView.getUserlistBox().setItems(FXCollections.observableArrayList("empty"));
            chatView.getUserlistBox().setValue("empty");
        }
    }

    @Override
    public void appendMessage(String from, String message) {
        if (loggedIn) {
            chatView.getDisplayTextArea().appendText("Message from " + from + ":\n" + message);
        }
    }
}
