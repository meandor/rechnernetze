package de.haw.rnp.client.view;

import de.haw.rnp.client.model.User;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.concurrent.BlockingQueue;

public class ChatViewController {

    private ChatView chatView;
    private ViewController controller;

    public ChatViewController(ViewController controller){
        this.controller = controller;
    }

    public Scene initChatView() {
        chatView = new ChatView();
        return chatView.getScene();
    }

    private void initOnEvents(){

        chatView.getMessageTextArea().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && chatView.getMessageTextArea().getText().length() > 0) {
                processMessage();
            }
        });

        chatView.getSendButton().setOnAction(action -> {
            String text = chatView.getMessageTextArea().getText();
            if(text != ""){
                processMessage();
            }
        });

        chatView.getLogoutButton().setOnAction(action -> {
            this.controller.logout();
            setUserLoggedOff();
            initLoginView();
        });
    }

    private void processMessage(){
        String text = chatView.getMessageTextArea().getText();
        String recipient = chatView.getReceiver().getText();
        if (controller.sendMessage(recipient, text)) {
            chatView.getDisplayTextArea().appendText(controller.getLoggedInUser().getName() + " send to " + recipient + ":\n" + text + "\n");
            chatView.getMessageTextArea().clear();
        }
    }

}
