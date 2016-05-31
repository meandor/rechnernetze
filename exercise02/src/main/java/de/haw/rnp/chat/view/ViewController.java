package de.haw.rnp.chat.view;

import de.haw.rnp.chat.controller.IControllerService;
import de.haw.rnp.chat.model.User;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ViewController implements IView {
    private Stage stage;
    private LoginView loginView;
    private ChatView chatView;
    private ServerView serverView;
    private IControllerService controller;
    private String userName;
    private InetAddress serverHostName;
    private int serverPort;

    public ViewController(Stage stage, IControllerService controller) {
        this.stage = stage;
        this.controller = controller;
        initServerView();
        //initChatView(controller.getUserList());
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
                if (controller.startServer(serverHostName, serverPort)) {
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
                    if (controller.login(user, InetAddress.getByName(host), serverHostName, Integer.parseInt(port), serverPort)) {
                        initChatView(controller.getUserList());
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        stage.setScene(loginView.getScene());
        stage.show();
    }

    private void initChatView(BlockingQueue<User> userList) {
        chatView = new ChatView(userList);

        chatView.getMessageTextArea().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && chatView.getMessageTextArea().getText().length() > 0) {
                String text = chatView.getMessageTextArea().getText();
                String recipient = chatView.getUserlistBox().getValue().toString();
                controller.sendMessage(recipient, text);
                chatView.getDisplayTextArea().appendText(userName + "send to" + recipient + ":\n" + text + "\n");
                chatView.getMessageTextArea().clear();
            }
        });

        chatView.getLogoutButton().setOnAction(action -> {
            this.controller.logout();
            setUserLoggedOff();
            initLoginView();
        });
        stage.setScene(chatView.getScene());
        stage.show();
    }

    private void setUserLoggedOff() {
        this.chatView = null;
        this.userName = "";
        controller.setLoggedInUser(null);
    }

    @Override
    public void setUserLoggedIn(String userName) {
        this.userName = userName;
        this.loginView = null;
        initChatView(controller.getUserList());
    }

    @Override
    public void updateUserlist(List<String> usernames) {
        if (controller.getLoggedInUser() != null && !usernames.isEmpty()) {
            chatView.getUserlistBox().setItems(FXCollections.observableArrayList(usernames));
            chatView.getUserlistBox().setValue(usernames.get(0));
        } else if (controller.getLoggedInUser() != null && usernames.isEmpty()) {
            chatView.getUserlistBox().setItems(FXCollections.observableArrayList("empty"));
            chatView.getUserlistBox().setValue("empty");
        }
    }

    @Override
    public void appendMessage(String from, String message) {
        if (controller.getLoggedInUser() != null) {
            chatView.getDisplayTextArea().appendText("Message from " + from + ":\n" + message);
        }
    }
}
