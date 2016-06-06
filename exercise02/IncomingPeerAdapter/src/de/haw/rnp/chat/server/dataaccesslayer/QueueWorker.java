package de.haw.rnp.chat.server.dataaccesslayer;

import de.haw.rnp.chat.server.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.chat.server.dataaccesslayer.entities.Frame;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class QueueWorker implements Runnable{

    private BlockingQueue<byte[]> queue;
    private ITransportServicesForIncomingPeerAdapter transportServices;
    private boolean isRunning;
    private Thread runningThread;

    public QueueWorker(ITransportServicesForIncomingPeerAdapter transportServices, BlockingQueue<byte[]> queue) {
        this.transportServices = transportServices;
        this.queue = queue;
    }


    @Override
    public void run() {
        synchronized(this){
            runningThread = Thread.currentThread();
            isRunning = true;
        }
        while(isRunning()){
            try {
                byte[] tmp = queue.take();
                Frame frame = transportServices.receiveFrameAsBytes(tmp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("QueueWorker stopped.");
    }

    public synchronized void stop(){
        isRunning = false;
    }

    private synchronized boolean isRunning() {
        return isRunning;
    }
}
