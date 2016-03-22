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
    private ArrayList<Thread> threadPool;

    public Controller(int threadCount) {
        super();
        String[] messageTypes = {"INFO", "WARN", "CORR"};
        this.queue = new LinkedBlockingQueue();
        this.threadPool = new ArrayList<Thread>();
        this.view = new NewsView(messageTypes);
        GeneralPurposeListener l = new GeneralPurposeListener(this);
        this.view.registerSendButtonListener(l);
        for (int i = 0; i < threadCount; i++) {
            MessageProducer thread = new MessageProducer(this.queue);
            this.threadPool.add(thread);
        }
    }

    public void showView() {
        this.view.setVisible(true);
    }

    public void run() {
        for (Thread t: this.threadPool) {
            t.start();
        }
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
        } else if (e.getSource() == this.view.getPauseThreads()) {

        }
    }
}