package de.haw.rnp01.newsticker.view;

/**
 * Created by daniel on 21.03.2016.
 */

import javax.swing.*;
import java.awt.*;

public class NewsView extends JFrame {

    private JLabel messageLabel = new JLabel("Nachricht");
    private JTextField messageInput = new JTextField(255);
    private JButton send = new JButton("absenden");

    public NewsView() {
        super("Nachrichten-Ticker");
        initForm();
    }

    private void initForm() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setBounds(200, 200, 500, 100);

        this.add(messageLabel);
        this.add(messageInput);
        this.add(send);
    }

    public String getMessage() {
        return this.messageInput.getText();
    }
}
