package de.haw.rnp01.newsticker;

import de.haw.rnp01.newsticker.model.News;
import de.haw.rnp01.newsticker.model.RandomGenerator;
import de.haw.rnp01.newsticker.view.NewsView;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by daniel on 21.03.2016.
 */
public class Controller {

    private ArrayList<News> news;
    private NewsView view;
    private LinkedList<News> queue;
    private MessageTransporter mt;
    private MessageProducer t1;
    private MessageProducer t2;

    public Controller() {
        String[] messageTypes = {"INFO", "WARN", "CORR"};
        this.news = new ArrayList<News>();
        this.queue = new LinkedList<News>();
        this.view = new NewsView(messageTypes);
        GeneralPurposeListener l = new GeneralPurposeListener(this);
        this.view.registerSendButtonListener(l);
        mt = new MessageTransporter(queue, news);
        t1 = new MessageProducer(queue);
        t2 = new MessageProducer(queue);
        this.view.addNews(this.news);
    }

    public void showView() {
        this.view.setVisible(true);
        mt.start();
        t1.start();
        t2.start();
    }

    public void performAction(ActionEvent e) {
        if (e.getSource() == this.view.getSend()) {
            News n = new News(this.view.getNewsTypesSelector(), this.view.getNewsInput());
            this.queue.addLast(n);
        }
    }
}