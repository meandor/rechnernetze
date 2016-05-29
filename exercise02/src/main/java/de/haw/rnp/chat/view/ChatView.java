package de.haw.rnp.chat.view;

import de.haw.rnp.chat.controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatView {
    private GridPane grid;
    private TextArea displayTextArea;
    private TextArea messageTextArea;
    private Button logoutButton;
    private ChoiceBox userlistBox;
    private Scene scene;


    public ChatView(){ scene = initScene();}

    private Scene initScene(){
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        displayTextArea = new TextArea();
        displayTextArea.setEditable(false);
        //always scroll to the latest entry
        displayTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            displayTextArea.setScrollTop(Double.MAX_VALUE);
        });
        grid.add(displayTextArea, 0, 0, 3, 2);

        messageTextArea = new TextArea();
        messageTextArea.setPrefRowCount(2);

        grid.add(messageTextArea, 0,3,1,1);

        userlistBox = new ChoiceBox(FXCollections.observableArrayList("empty"));
        userlistBox.setValue("empty");
        grid.add(userlistBox, 1,3);

        logoutButton = new Button("Logout");
        grid.add(logoutButton, 2,3);

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

    public ChoiceBox getUserlistBox(){
        return userlistBox;
    }
}
