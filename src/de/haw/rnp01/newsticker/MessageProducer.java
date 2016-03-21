package de.haw.rnp01.newsticker;

import de.haw.rnp01.newsticker.model.News;
import de.haw.rnp01.newsticker.model.RandomGenerator;

import java.util.LinkedList;

/**
 * Created by on 21.03.2016.
 */
public class MessageProducer extends Thread {

    private RandomGenerator random;
    private LinkedList<News> sharedMemory;

    public MessageProducer(LinkedList<News> sharedMemory) {
        super();
        this.random = RandomGenerator.getInstance();
        this.sharedMemory = sharedMemory;
    }

    public void run() {
        try {
            while (true) {
                sleep(this.random.generateRandomSleepTime());
                News n = new News(this.random.generateRandomMessageType(), this.random.generateRandomMessage());
                this.sharedMemory.addLast(n);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
