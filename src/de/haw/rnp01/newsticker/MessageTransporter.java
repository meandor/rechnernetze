package de.haw.rnp01.newsticker;

import de.haw.rnp01.newsticker.model.News;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by on 21.03.2016.
 */
public class MessageTransporter extends Thread {

    private LinkedBlockingQueue queue;
    private ArrayList<News> news;

    public MessageTransporter(LinkedBlockingQueue queue, ArrayList<News> news) {
        super();
        this.news = news;
        this.queue = queue;
    }

    public void run() {
        while (true) {
            if (queue.size() > 0) {
                System.out.println("queue: "+this.queue.toString());
            }
            /*while (queue.size() > 0) {
                this.news.add(queue.removeFirst());
                System.out.println("queue: "+this.queue.toString());
                System.out.println("news: " + this.news.toString());
            }*/
        }
    }
}
