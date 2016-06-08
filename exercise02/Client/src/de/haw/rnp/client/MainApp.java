package de.haw.rnp.client;

import de.haw.rnp.client.model.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    ObservableList<User> users;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        users = FXCollections.observableArrayList();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public ObservableList<User> getList(){return users;}

    public static void main(String[] args) {
        launch(args);
    }
}