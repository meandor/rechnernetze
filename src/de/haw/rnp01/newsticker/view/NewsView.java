package de.haw.rnp01.newsticker.view;

/**
 * Created by daniel on 21.03.2016.
 */

import de.haw.rnp01.newsticker.model.News;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewsView extends JFrame {

    private String[] newsTypes;
    private JLabel newsLabel;
    private JTextField newsInput;
    private JComboBox newsTypesSelector;
    private JButton send;
    private JButton pauseThreads;
    private JTextArea newsOutput;
    private JScrollPane scrollPane;

    public NewsView(String[] newsTypes) {
        super("Nachrichten-Ticker");
        this.newsTypes = newsTypes;
        this.newsLabel = new JLabel("Nachricht:");
        this.newsInput = new JTextField(20);
        this.newsTypesSelector = new JComboBox(this.newsTypes);
        this.send = new JButton("absenden");
        this.pauseThreads = new JButton("Threads pausieren");
        this.newsOutput = new JTextArea(40, 100);
        this.scrollPane = new JScrollPane(this.newsOutput);
        this.newsOutput.setEditable(false);

        initForm();
    }

    private void initForm() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setBounds(0, 0, 1200, 800);

        this.add(newsLabel);
        this.add(newsInput);
        this.add(newsTypesSelector);
        this.add(send);
        this.add(pauseThreads);
        this.add(scrollPane);
    }

    public void addNews(ArrayList<News> news) {
        this.newsOutput.append(news.toString() + "\n");
    }

    public String getNewsInput() {
        return newsInput.getText();
    }

    public JButton getSend() {
        return this.send;
    }

    public JButton getPauseThreads() {
        return this.pauseThreads;
    }

    public String getNewsTypesSelector() {
        return newsTypesSelector.getSelectedItem().toString();
    }

    public void registerSendButtonListener(ActionListener l) {
        this.send.addActionListener(l);
    }

    public void registerThreadButtonListener(ActionListener l) {
        this.pauseThreads.addActionListener(l);
    }
}