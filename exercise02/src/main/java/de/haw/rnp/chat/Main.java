package de.haw.rnp.chat;

import de.haw.rnp.chat.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller(primaryStage);
        /*MessageHandler messageHandler = controller.getMessageHandler();
        ThreadPoolExecutor executor = ((ThreadPoolExecutor) messageHandler.getExecutor());
        Node server = messageHandler.getFactory().createNode(InetAddress.getByName("0.0.0.0"), 18181);
        ServerStartTask task = new ServerStartTask(server);
        Future<Boolean> serverStarted = executor.submit(task);
        if (serverStarted.get()) {
            ServerAwaitConnectionsTask task2 = new ServerAwaitConnectionsTask(server);
            executor.execute(task2);
        }*/
    }
}
