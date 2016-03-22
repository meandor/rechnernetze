package de.haw.rnp01.newsticker;

import de.haw.rnp01.newsticker.model.Message;
import de.haw.rnp01.newsticker.model.RandomGenerator;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by on 21.03.2016.
 */
public class MessageProducer extends Thread {

    private RandomGenerator random;
    private LinkedBlockingQueue sharedMemory;

    public MessageProducer(LinkedBlockingQueue sharedMemory) {
        super();
        this.random = RandomGenerator.getInstance();
        this.sharedMemory = sharedMemory;
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(this.random.generateRandomSleepTime());
                Message n = new Message(this.random.generateRandomMessageType(), this.random.generateRandomMessage());
                this.sharedMemory.put(n);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}