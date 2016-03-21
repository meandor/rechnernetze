package de.haw.rnp01.newsticker;

import de.haw.rnp01.newsticker.model.News;
import de.haw.rnp01.newsticker.model.RandomGenerator;

/**
 * Created by on 21.03.2016.
 */
public class MessageProducer extends Thread {

    private News message;
    private RandomGenerator random;

    public MessageProducer() {
        super();
        this.random = RandomGenerator.getInstance();
    }

    public void run() {
        try {
            sleep(this.random.generateRandomSleepTime());
            this.message = new News(this.random.generateRandomMessageType(), this.random.generateRandomMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public News getMessage() {
        return message;
    }
}
