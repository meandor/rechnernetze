package de.haw.rnp.chat.view;

import de.haw.rnp.chat.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class ChatView {
    private GridPane grid;
    private TextArea displayTextArea;
    private TextArea messageTextArea;
    private Button logoutButton;
    private ChoiceBox userlistBox;
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

        messageTextArea = new TextArea();
        messageTextArea.setPrefRowCount(2);

        grid.add(messageTextArea, 0, 3, 1, 1);

        userlistBox = new ChoiceBox(FXCollections.observableArrayList("empty"));
        userlistBox.setValue("empty");
        grid.add(userlistBox, 1, 3);

        userList = new ListView<>();
        ArrayList<User> usersList = new ArrayList<>();
        for (User u : users) {
            usersList.add(u);
        }
        ObservableList<User> myObservableList = FXCollections.observableArrayList(usersList);
        this.userList.setItems(myObservableList);
        grid.add(userList, 3, 0);

        logoutButton = new Button("Logout");
        grid.add(logoutButton, 2, 3);

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

    public ChoiceBox getUserlistBox() {
        return userlistBox;
    }
}
