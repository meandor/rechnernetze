package de.haw.rnp.chat;

import de.haw.rnp.chat.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = Controller.getInstance();
        controller.startView(primaryStage);
    }
}
