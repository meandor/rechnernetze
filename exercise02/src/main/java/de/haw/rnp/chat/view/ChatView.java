package de.haw.rnp.chat.view;

import de.haw.rnp.chat.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * View responsible for displaying the main chat window.
 */
public class ChatView {
    private GridPane grid;
    private TextArea displayTextArea;
    private TextArea messageTextArea;
    private TextField receiver;
    private Button sendButton;
    private Button logoutButton;
    private ListView<User> userList;
    private Scene scene;

    public ChatView(BlockingQueue<User> users) {
        scene = initScene(users);
    }

    private Scene initScene(BlockingQueue<User> users) {
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

        receiver = new TextField();
        receiver.setPromptText("Recipients");
        grid.add(receiver, 0, 3);

        messageTextArea = new TextArea();
        messageTextArea.setPromptText("Message");
        messageTextArea.setPrefRowCount(2);

        grid.add(messageTextArea, 0, 4, 1, 1);

        userList = new ListView<>();
        ArrayList<User> usersList = new ArrayList<>();
        for (User u : users) {
            usersList.add(u);
        }
        ObservableList<User> myObservableList = FXCollections.observableArrayList(usersList);
        this.userList.setItems(myObservableList);
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

    public Scene getScene() {
        return scene;
    }

    public TextField getReceiver() {
        return receiver;
    }

    public ListView<User> getUserList() {
        return userList;
    }
}
