package de.haw.rnp.messageticker.view;

import de.haw.rnp.messageticker.model.Message;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The view for displaying all messages and the basic chat functionality.
 */
public class MessagesView extends JFrame {

    private String[] messageTypes;
    private JLabel messageLabel;
    private JTextField messageInput;
    private JComboBox messageTypesSelector;
    private JButton send;
    private JButton pauseThreads;
    private JTextArea messageOutput;
    private JScrollPane scrollPane;
    private JPanel statusBar;
    private JLabel statusLabel;
    private JPanel inputContainer;
    private DefaultCaret messageOutputCaret;

    /**
     * Constructs the view.
     * @param messageTypes establishes the messageTypes
     */
    public MessagesView(String[] messageTypes) {
        super("Nachrichten-Ticker");
        this.messageTypes = messageTypes;
        this.messageLabel = new JLabel("Nachricht:");
        this.messageInput = new JTextField(20);
        this.messageTypesSelector = new JComboBox(this.messageTypes);
        this.send = new JButton("absenden");
        this.pauseThreads = new JButton("Threads pausieren");
        this.messageOutput = new JTextArea(40, 100);
        this.messageOutputCaret = (DefaultCaret) this.messageOutput.getCaret();
        this.scrollPane = new JScrollPane(this.messageOutput);
        this.messageOutput.setEditable(false);
        this.statusBar = new JPanel();
        this.statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.statusLabel = new JLabel("STATUS HAR HAR");
        this.statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.statusBar.setPreferredSize(new Dimension(this.getWidth(),35));
        this.inputContainer = new JPanel();
        initForm();
    }

    /**
     * Initializes the view and its components.
     */
    private void initForm() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 1200, 800);

        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        this.messageOutputCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 0;
        container.add(inputContainer, gc);

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 1;
        container.add(scrollPane, gc);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.PAGE_END;
        container.add(statusBar, gc);
        this.statusBar.add(statusLabel);

        this.inputContainer.setLayout(new FlowLayout());
        this.inputContainer.add(messageLabel);
        this.inputContainer.add(messageInput);
        this.inputContainer.add(messageTypesSelector);
        this.inputContainer.add(send);
        this.inputContainer.add(pauseThreads);


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

    public JLabel getStatusLabel(){return this.statusLabel;}

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