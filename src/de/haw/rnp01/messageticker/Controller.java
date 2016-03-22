package de.haw.rnp01.messageticker;

import de.haw.rnp01.messageticker.model.GeneralPurposeListener;
import de.haw.rnp01.messageticker.model.Message;
import de.haw.rnp01.messageticker.model.MessageProducer;
import de.haw.rnp01.messageticker.view.MessagesView;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Controller for the MVC Pattern.
 */
public class Controller extends Thread {

    private MessagesView view;
    private LinkedBlockingQueue queue;
    private ArrayList<Thread> threadPool;
    private boolean interruptThreads;

    /**
     * Constructs the Controller with a thread pool.
     *
     * @param threadCount count of the threads in the pool
     */
    public Controller(int threadCount) {
        super();
        String[] messageTypes = {"INFO", "WARN", "CORR"};
        // Queue can have a size for less delay but too small size can lead to "package" loss
        this.queue = new LinkedBlockingQueue();
        this.threadPool = new ArrayList<Thread>();
        this.view = new MessagesView(messageTypes);
        GeneralPurposeListener l = new GeneralPurposeListener(this);
        this.view.registerSendButtonListener(l);
        this.view.registerThreadButtonListener(l);

        for (int i = 0; i < threadCount; i++) {
            MessageProducer thread = new MessageProducer(this.queue);
            this.threadPool.add(thread);
        }
    }

    public void showView() {
        this.view.setVisible(true);
    }

    public void run() {
        for (Thread t : this.threadPool) {
            t.start();
        }

        while (true) {
            try {
                this.view.addMessage(((Message) this.queue.take()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the actions from the GeneralPurposeListener.
     * @param e ActionEvent
     */
    public void performAction(ActionEvent e) {
        /**
         * Adds the message from the View into the queue
         */
        if (e.getSource() == this.view.getSend()) {
            Message n = new Message(this.view.getMessageTypesSelector(), this.view.getMessageInput());
            try {
                this.queue.put(n);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.view.getPauseThreads()) {
            /**
             * Stops and restarts all threads
             */
            for (Thread t : this.threadPool) {
                if (!this.interruptThreads) {
                    t.interrupt();
                } else {
                    t = new MessageProducer(this.queue);
                    t.start();
                }
            }

            if (!this.interruptThreads) {
                this.view.getPauseThreads().setText("Threads starten");
            } else {
                this.view.getPauseThreads().setText("Threads pausieren");
            }

            this.interruptThreads = !this.interruptThreads;
        }
    }
}