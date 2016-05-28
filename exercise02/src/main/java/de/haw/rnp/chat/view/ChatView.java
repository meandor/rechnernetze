package de.haw.rnp.chat.view;

import de.haw.rnp.chat.controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatView {
    private Controller controller;
    private Stage primaryStage;
    private boolean loggedIn;

    public ChatView(Stage primaryStage, Controller controller){
        this.primaryStage = primaryStage;
        this.controller = controller;
        this.loggedIn = false;
        initComponents();
    }

    private void initComponents(){
        primaryStage.setTitle("RNP ChatApp");
        primaryStage.setScene(login());
        primaryStage.show();
    }

    private Scene login(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label hostName = new Label("Host:");
        grid.add(hostName, 0,2);

        TextField hostTextField = new TextField();
        grid.add(hostTextField, 1, 2);

        Label portName = new Label("Port:");
        grid.add(portName, 0, 3);

        TextField portTextField = new TextField();
        grid.add(portTextField, 1, 3);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(action -> {
            String user = userTextField.getText();
            String host = hostTextField.getText();
            String port = portTextField.getText();
            if(validateFields(user, host, port)){
                //this.controller.login(user, host, port);
                this.primaryStage.setScene(chat());
            }
        });

        return new Scene(grid);
    }

    private boolean validateFields(String user, String host, String port){
        if(user.length() <= 0)
            return false;
        return true;
    }

    private Scene chat(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        TextArea displayTextArea = new TextArea();
        displayTextArea.setEditable(false);
        displayTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            displayTextArea.setScrollTop(Double.MAX_VALUE);
        });
        grid.add(displayTextArea, 0, 0, 2, 2);

        TextArea messageTextArea = new TextArea();
        messageTextArea.setPrefRowCount(1);
        displayTextArea.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER && displayTextArea.getText().length() > 0){
                String text = displayTextArea.getText();
                //send message to users...
                displayTextArea.appendText("Me:\n" + text + "\n");
            }
        });
        grid.add(messageTextArea, 0,3,1,1);

        Button btn = new Button("Logout");
        grid.add(btn, 1,3);

        return new Scene(grid);
    }
}
