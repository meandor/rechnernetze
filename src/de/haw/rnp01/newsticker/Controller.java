package de.haw.rnp01.newsticker;

import de.haw.rnp01.newsticker.model.News;
import de.haw.rnp01.newsticker.model.RandomGenerator;
import de.haw.rnp01.newsticker.view.NewsView;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created by daniel on 21.03.2016.
 */
public class Controller {

    private ArrayList<News> news;
    private RandomGenerator randomGenerator;
    private NewsView view;

    public Controller() {
        String[] messageTypes = {"INFO", "WARN", "CORR"};
        this.news = new ArrayList<News>();
        this.randomGenerator = new RandomGenerator();
        this.view = new NewsView(messageTypes);
        GeneralPurposeListener l = new GeneralPurposeListener(this);
        this.view.registerSendButtonListener(l);
    }

    public void showView() {
        this.view.setVisible(true);
    }

    public void performAction(ActionEvent e) {
        if (e.getSource() == this.view.getSend()) {
            News n = new News(this.view.getNewsTypesSelector(), this.view.getNewsInput());
            this.news.add(n);
            this.view.addNews(this.news.get(this.news.size() - 1));
        }
    }

    private void addTestInput() {
        News n1 = new News("INFO", "Test 1");
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
        this.view.addNews(n1);
    }
}