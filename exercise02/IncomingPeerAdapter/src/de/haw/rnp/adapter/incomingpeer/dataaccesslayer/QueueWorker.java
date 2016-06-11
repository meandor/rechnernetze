package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServicesForInPeer;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageDTO;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Field;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.util.enumerations.FieldType;
import de.haw.rnp.util.enumerations.MessageType;

import java.io.IOException;
import java.util.Collection;
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
                        if(!transportServices.checkLocal(frame)) {
                            outClientAdapterServices.addUser(frame.toUserDTO());
                            transportServices.propagatePeer(frame, outClientAdapterServices.getAllUsers());
                        }
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
                        outClientAdapterServices.removeUser(user);
                        transportServices.propagateLogout(frame, outClientAdapterServices.getAllUsers());
                    }

                    case MyName:{
                        UserDTO user = null;

                        for(Field field : frame.getFields()){
                            if(field.getFieldType() == FieldType.Name){
                                String name = (String) field.getData();
                                user = new UserDTO(frame.getSender(), name);
                            }
                        }

                        if(user != null)
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
