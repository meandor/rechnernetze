package de.haw.rnp.chat.view;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginView {
    private GridPane grid;
    private Text sceneTitle;
    private Label userName;
    private Label hostName;
    private Label portName;
    private Label localHostName;
    private Label localPortName;
    private TextField userTextField;
    private TextField hostTextField;
    private TextField portTextField;
    private TextField localHostTextField;
    private TextField localPortTextField;
    private Button signin;

    private Scene scene;

    public LoginView(){
        scene = initScene();
    }

    private Scene initScene(){
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        hostName = new Label("Host:");
        grid.add(hostName, 0,2);

        hostTextField = new TextField();
        grid.add(hostTextField, 1, 2);

        portName = new Label("Port:");
        grid.add(portName, 0, 3);

        portTextField = new TextField();
        grid.add(portTextField, 1, 3);

        localHostName = new Label("Local Host:");
        grid.add(localHostName, 0, 4);

        localHostTextField = new TextField();
        grid.add(localHostTextField, 1, 4);

        localPortName = new Label("Local Port:");
        grid.add(localPortName, 0, 5);

        localPortTextField = new TextField();
        grid.add(localPortTextField, 1, 5);

        signin = new Button("Sign in");
        grid.add(signin, 1, 6);

        return new Scene(grid);
    }

    public Scene getScene() {
        return scene;
    }

    public TextField getUserTextField() {
        return userTextField;
    }

    public TextField getPortTextField() {
        return portTextField;
    }

    public TextField getHostTextField() {
        return hostTextField;
    }

    public TextField getLocalHostTextField() {
        return localHostTextField;
    }

    public TextField getLocalPortTextField() {
        return localPortTextField;
    }

    public Button getSignin() {
        return signin;
    }
}
