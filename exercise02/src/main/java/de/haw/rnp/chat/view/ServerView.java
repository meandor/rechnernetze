package de.haw.rnp.chat.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * View for starting the server.
 */
public class ServerView {
    private GridPane grid;
    private Text sceneTitle;
    private Label hostNameLabel;
    private TextField hostNameField;
    private Label portLabel;
    private TextField portField;
    private Button startServer;

    private Scene scene;

    public ServerView() {
        scene = initScene();
    }

    private Scene initScene() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        sceneTitle = new Text("Start a Server");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        hostNameLabel = new Label("Host:");
        grid.add(hostNameLabel, 0, 1);

        hostNameField = new TextField();
        grid.add(hostNameField, 1, 1);

        portLabel = new Label("Port:");
        grid.add(portLabel, 0, 2);

        portField = new TextField();
        grid.add(portField, 1, 2);

        startServer = new Button("Start Server");
        grid.add(startServer, 1, 3);

        return new Scene(grid);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Button getStartServer() {
        return startServer;
    }

    public void setStartServer(Button startServer) {
        this.startServer = startServer;
    }

    public TextField getPortField() {
        return portField;
    }

    public void setPortField(TextField portField) {
        this.portField = portField;
    }

    public TextField getHostNameField() {
        return hostNameField;
    }

    public void setHostNameField(TextField hostNameField) {
        this.hostNameField = hostNameField;
    }
}
