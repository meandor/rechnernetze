package de.haw.rnp.client.view;

import de.haw.rnp.client.model.User;
import de.haw.rnp.util.AddressType;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class ChatViewController {

    private ChatView chatView;
    private ViewController controller;
    private ObservableList<User> users;

    public ChatViewController(ViewController controller, ObservableList<User> users){
        this.controller = controller;
        this.users = users;
    }

    public Scene initChatView() {
        chatView = new ChatView(users);
        initOnEvents();
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
            if(!text.isEmpty()){
                processMessage();
            }
        });

        chatView.getLogoutButton().setOnAction(action -> {
            controller.sendLogout();
            controller.changeViewState(ViewController.ViewState.Login);
        });
    }

    private void processMessage(){
        String text = chatView.getMessageTextArea().getText();
        ObservableList<User> recipients = chatView.getUserList().getSelectionModel().getSelectedItems();
        if (controller.sendMessage(text, recipients)) {
            String recipient = "";
            for(User rec : recipients){
                if(recipient.isEmpty()){
                    recipient += rec.getName();
                }
                else{
                    recipient += "," + rec.getName();
                }
            }
            chatView.getDisplayTextArea().appendText(controller.getLocal().getName() + " send to " + recipient + ":\n" + text + "\n");
            chatView.getMessageTextArea().clear();
        }
    }

    public void appendMessage(User sender, String message){
        if (chatView != null) {
            chatView.getDisplayTextArea().appendText("Message from " + sender.getName() + ":\n" + message + "\n");
        }
    }
}
