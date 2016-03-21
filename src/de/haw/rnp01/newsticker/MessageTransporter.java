package de.haw.rnp01.newsticker;

import de.haw.rnp01.newsticker.model.News;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by on 21.03.2016.
 */
public class MessageTransporter extends Thread {

    private LinkedList<News> queue;
    private ArrayList<News> news;

    public MessageTransporter(LinkedList<News> queue, ArrayList<News> news) {
        super();
        this.news = news;
        this.queue = queue;
    }

    public void run() {
        while (true) {
            while (queue.size() > 0) {
                this.news.add(queue.removeFirst());
                System.out.println("queue: "+this.queue.toString());
                System.out.println("news: " + this.news.toString());
            }
        }
    }
}
