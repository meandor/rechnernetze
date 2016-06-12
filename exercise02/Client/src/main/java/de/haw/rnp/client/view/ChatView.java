package de.haw.rnp.client.view;

import de.haw.rnp.client.model.User;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ChatView {
    private GridPane grid;
    private TextArea displayTextArea;
    private TextArea messageTextArea;

    private Button sendButton;
    private Button logoutButton;
    private ListView<User> userList;
    private Scene scene;
    private ObservableList<User> users;

    public ChatView(ObservableList<User> users) {
        this.users = users;
        scene = initScene();
    }

    private Scene initScene() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        displayTextArea = new TextArea();
        displayTextArea.setEditable(false);
        //always scroll to the latest entry
        displayTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            displayTextArea.setScrollTop(Double.MAX_VALUE);
        });
        grid.add(displayTextArea, 0, 0, 3, 2);

        messageTextArea = new TextArea();
        messageTextArea.setPromptText("Message");
        messageTextArea.setPrefRowCount(2);
        grid.add(messageTextArea, 0, 4, 1, 1);

        userList = new ListView<>();
        userList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userList.setItems(users);
        grid.add(userList, 3, 0);

        sendButton = new Button("send");
        grid.add(sendButton, 1, 4);

        logoutButton = new Button("Logout");
        grid.add(logoutButton, 2, 4);

        return new Scene(grid);
    }

    public TextArea getDisplayTextArea() {
        return displayTextArea;
    }

    public TextArea getMessageTextArea() {
        return messageTextArea;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public Scene getScene() {
        return scene;
    }

    public void setUserList(ObservableList<User> users) {
        userList = new ListView<>(users);
    }

    public ListView<User> getUserList(){
        return userList;
    }
}
