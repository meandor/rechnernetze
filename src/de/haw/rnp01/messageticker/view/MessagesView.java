package de.haw.rnp01.messageticker.view;

/**
 * Created by daniel on 21.03.2016.
 */

import de.haw.rnp01.messageticker.model.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MessagesView extends JFrame {

    private String[] messageTypes;
    private JLabel messageLabel;
    private JTextField messageInput;
    private JComboBox messageTypesSelector;
    private JButton send;
    private JButton pauseThreads;
    private JTextArea messageOutput;
    private JScrollPane scrollPane;

    public MessagesView(String[] messageTypes) {
        super("Nachrichten-Ticker");
        this.messageTypes = messageTypes;
        this.messageLabel = new JLabel("Nachricht:");
        this.messageInput = new JTextField(20);
        this.messageTypesSelector = new JComboBox(this.messageTypes);
        this.send = new JButton("absenden");
        this.pauseThreads = new JButton("Threads pausieren");
        this.messageOutput = new JTextArea(40, 100);
        this.scrollPane = new JScrollPane(this.messageOutput);
        this.messageOutput.setEditable(false);

        initForm();
    }

    private void initForm() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setBounds(0, 0, 1200, 800);

        this.add(messageLabel);
        this.add(messageInput);
        this.add(messageTypesSelector);
        this.add(send);
        this.add(pauseThreads);
        this.add(scrollPane);
    }

    public void addMessage(Message message) {
        this.messageOutput.append(message.toString() + "\n");
    }

    public String getMessageInput() {
        return messageInput.getText();
    }

    public JButton getSend() {
        return this.send;
    }

    public JButton getPauseThreads() {
        return this.pauseThreads;
    }

    public String getMessageTypesSelector() {
        return messageTypesSelector.getSelectedItem().toString();
    }

    public void registerSendButtonListener(ActionListener l) {
        this.send.addActionListener(l);
    }

    public void registerThreadButtonListener(ActionListener l) {
        this.pauseThreads.addActionListener(l);
    }
}