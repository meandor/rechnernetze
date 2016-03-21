package de.haw.rnp01.newsticker.view;

/**
 * Created by daniel on 21.03.2016.
 */

import de.haw.rnp01.newsticker.model.News;
import javax.swing.*;
import java.awt.*;

public class NewsView extends JFrame {

    private String[] newsTypes;
    private JLabel newsLabel;
    private JTextField newsInput;
    private JComboBox newsTypesSelector;
    private JButton send;
    private JButton pauseThreads;
    private JTextArea newsOutput;

    public NewsView(String[] newsTypes) {
        super("Nachrichten-Ticker");
        this.newsTypes = newsTypes;
        this.newsLabel = new JLabel("Nachricht:");
        this.newsInput = new JTextField(20);
        this.newsTypesSelector = new JComboBox(this.newsTypes);
        this.send = new JButton("absenden");
        this.pauseThreads = new JButton("Threads pausieren");
        this.newsOutput = new JTextArea(100, 500);
        JScrollPane scrollPane = new JScrollPane(this.newsOutput);
        this.newsOutput.setEditable(false);

        initForm();
    }

    private void initForm() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setBounds(200, 200, 1200, 600);

        this.add(newsLabel);
        this.add(newsInput);
        this.add(newsTypesSelector);
        this.add(send);
        this.add(pauseThreads);
        this.add(newsOutput);
    }

    public String getNews() {
        return this.newsInput.getText();
    }

    public void addNews(News news) {
        this.newsOutput.append(news.toString() + "\n");
        System.out.println("shit hinzugefügt");
    }
}