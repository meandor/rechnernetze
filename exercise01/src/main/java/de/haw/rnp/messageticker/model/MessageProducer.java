package de.haw.rnp.messageticker.model;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Producer in the consumer producer pattern. Produces random messages.
 */
public class MessageProducer extends Thread {

    private RandomGenerator random;
    private LinkedBlockingQueue sharedMemory;

    /**
     * Constructs the producer.
     * @param sharedMemory Shared memory (buffer) for the produced items.
     */
    public MessageProducer(LinkedBlockingQueue sharedMemory) {
        super();
        this.random = RandomGenerator.getInstance();
        this.sharedMemory = sharedMemory;
    }

    /**
     * Uses the RandomGenerator to produce random messages at random intervals (1s-5s).
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(this.random.generateRandomSleepTime(1000, 5000));
                Message n = new Message(this.random.generateRandomMessageType(), this.random.generateRandomMessage());
                this.sharedMemory.put(n);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}