package de.haw.rnp01.newsticker;

import de.haw.rnp01.newsticker.model.News;
import de.haw.rnp01.newsticker.view.NewsView;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class Controller extends Thread {

    private NewsView view;
    private LinkedBlockingQueue queue;
    private MessageProducer t1;
    private MessageProducer t2;

    public Controller() {
        super();
        String[] messageTypes = {"INFO", "WARN", "CORR"};
        this.queue = new LinkedBlockingQueue();
        this.view = new NewsView(messageTypes);
        GeneralPurposeListener l = new GeneralPurposeListener(this);
        this.view.registerSendButtonListener(l);
        t1 = new MessageProducer(queue);
        t2 = new MessageProducer(queue);
    }

    public void showView() {
        this.view.setVisible(true);
    }

    public void run() {
        t1.start();
        t2.start();
        while (true) {
            try {
                this.view.addNews(this.queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void performAction(ActionEvent e) {
        if (e.getSource() == this.view.getSend()) {
            News n = new News(this.view.getNewsTypesSelector(), this.view.getNewsInput());
            try {
                this.queue.put(n);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}