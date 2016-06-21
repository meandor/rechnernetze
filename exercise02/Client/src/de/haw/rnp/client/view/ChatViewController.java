package de.haw.rnp.client.view;

import de.haw.rnp.client.model.User;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * Controller responsible for managing the ChatView.
 */
public class ChatViewController {

    private ChatView chatView;
    private ViewController controller;
    private ObservableList<User> users;

    /**
     * Constructs the controller.
     *
     * @param controller Controller
     * @param users      List of all Users
     */
    public ChatViewController(ViewController controller, ObservableList<User> users) {
        this.controller = controller;
        this.users = users;
    }

    /**
     * Initializes the Scene.
     *
     * @return the Scene
     */
    public Scene initChatView() {
        chatView = new ChatView(users);
        initOnEvents();
        return chatView.getScene();
    }

    /**
     * Initializes the Events for button clicks etc.
     */
    private void initOnEvents() {
        chatView.getMessageTextArea().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && chatView.getMessageTextArea().getText().length() > 0) {
                processMessage();
            }
        });

        chatView.getSendButton().setOnAction(action -> {
            String text = chatView.getMessageTextArea().getText();
            if (!text.isEmpty()) {
                processMessage();
            }
        });

        chatView.getLogoutButton().setOnAction(action -> {
            controller.sendLogout();
            controller.changeViewState(ViewController.ViewState.Login);
        });
    }

    /**
     * Process a message send by the view.
     */
    private void processMessage() {
        String text = chatView.getMessageTextArea().getText();
        ObservableList<User> recipients = chatView.getUserList().getSelectionModel().getSelectedItems();
        if (controller.sendMessage(text, recipients)) {
            String recipient = "";
            for (User rec : recipients) {
                if (recipient.isEmpty()) {
                    recipient += rec.getName();
                } else {
                    recipient += "," + rec.getName();
                }
            }
            chatView.getDisplayTextArea().appendText(controller.getLocal().getName() + " send to " + recipient + ":\n" + text + "\n");
            chatView.getMessageTextArea().clear();
        }
    }

    /**
     * Adds a message tot he ChatView.
     *
     * @param sender  User sender of the Message
     * @param message String actual Message
     */
    public void appendMessage(User sender, String message) {
        if (chatView != null) {
            chatView.getDisplayTextArea().appendText("Message from " + sender.getName() + ":\n" + message + "\n");
        }
    }
}
