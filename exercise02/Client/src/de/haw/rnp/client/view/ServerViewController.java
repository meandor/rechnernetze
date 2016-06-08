package de.haw.rnp.client.view;

import javafx.scene.Scene;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerViewController {

    private ServerView serverView;

    public Scene initServerView() {
        serverView = new ServerView();
        initOnEvents();
        return serverView.getScene();
    }

    private void initOnEvents(){
        serverView.getStartServer().setOnAction(event -> {
            String hostName = serverView.getHostNameField().getText();
            String port = serverView.getPortField().getText();

            InetAddress serverHostName = null;
            try {
                serverHostName = InetAddress.getByName(hostName);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            int serverPort = Integer.parseInt(port);
            if (controller.startServer(serverHostName, serverPort)) {
                this.serverHostName = serverHostName;
                this.serverPort = serverPort;
                initLoginView();
            }

        });
    }

}
