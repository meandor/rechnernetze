package de.haw.rnp.messageticker.model;

import java.util.Random;

/**
 * Generator for random messages and other stuff with the singleton pattern.
 */
class RandomGenerator {

    private String[] messageTypes = {"INFO", "WARN", "CORR"};
    private Random generator = new Random();
    private static RandomGenerator instance;
    private static final String ALLOWED_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * Overwrites and cancels normal constructor.
     */
    public RandomGenerator() {
    }

    /**
     * Returns the singleton
     *
     * @return RandomGenerator singleton
     */
    public static synchronized RandomGenerator getInstance() {
        if (RandomGenerator.instance == null) {
            RandomGenerator.instance = new RandomGenerator();
        }
        return RandomGenerator.instance;
    }

    /**
     * Random message type
     *
     * @return {"INFO", "WARN", "CORR"} any of those
     */
    public String generateRandomMessageType() {
        int messageTypeSize = this.messageTypes.length;
        return this.messageTypes[this.generator.nextInt(messageTypeSize)];
    }

    /**
     * Random sleep time for the thread in milliseconds
     *
     * @return sleep time from 1000 - 5000 ms
     */
    public long generateRandomSleepTime(int minSleepTime, int maxSleepTime) {
        int normalizedInterval = (maxSleepTime - minSleepTime);
        return ((long) (this.generator.nextInt(normalizedInterval) + minSleepTime));
    }//

    /**
     * Random message
     *
     * @return message
     */
    public String generateRandomMessage() {
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; i++)
            sb.append(ALLOWED_CHARS.charAt(this.generator.nextInt(ALLOWED_CHARS.length())));
        return sb.toString();
    }
}
