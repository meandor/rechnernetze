package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServicesForInPeer;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageDTO;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.util.enumerations.MessageType;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class QueueWorker implements Runnable{

    private BlockingQueue<byte[]> queue;
    private ITransportServicesForIncomingPeerAdapter transportServices;
    private IOutClientAdapterServicesForInPeer outClientAdapterServices;
    private boolean isRunning;
    private Thread runningThread;

    public QueueWorker(ITransportServicesForIncomingPeerAdapter transportServices,
                       IOutClientAdapterServicesForInPeer outClientAdapterServices,
                       BlockingQueue<byte[]> queue) {
        this.outClientAdapterServices = outClientAdapterServices;
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
                switch(frame.getMessageType()){
                    case Login:{
                        UserDTO user = frame.toUserDTO();
                        if(user == null)
                            break;
                        outClientAdapterServices.addUser(user);
                        transportServices.propagatePeer(frame, outClientAdapterServices.getAllUsers());
                        break;
                    }
                    case TextMessage:{
                        MessageDTO msg = frame.toMessageDTO();
                        if(msg == null)
                            break;
                        outClientAdapterServices.addMessage(msg);
                        break;
                    }
                    case Logout:{
                        UserDTO user = frame.toUserDTO();
                        if(user == null)
                            break;
                        outClientAdapterServices.removeUser(user);
                    }

                    case MyName:{
                        UserDTO user = frame.toUserDTO();
                        if(user == null)
                            break;
                        outClientAdapterServices.updateUsername(user);
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("QueueWorker stopped.");
    }

    public synchronized void stop(){
        isRunning = false;
        this.runningThread.interrupt();
    }

    private synchronized boolean isRunning() {
        return isRunning;
    }
}
