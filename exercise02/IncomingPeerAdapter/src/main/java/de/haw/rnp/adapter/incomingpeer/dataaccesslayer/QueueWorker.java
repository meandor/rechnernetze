package de.haw.rnp.adapter.incomingpeer.dataaccesslayer;

import de.haw.rnp.adapter.outgoingclient.accesslayer.IOutClientAdapterServicesForInPeer;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageDTO;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Field;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.util.enumerations.FieldType;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;

public class QueueWorker implements Runnable {

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
        synchronized (this) {
            runningThread = Thread.currentThread();
            isRunning = true;
        }
        while (isRunning()) {
            try {
                byte[] tmp = queue.take();
                int align = 1;
                for (byte b : tmp) {
                    System.out.format("0x%x ", b);
                    if ((align % 4) == 0 && align > 1) {
                        System.out.print("\n");
                    }
                    align++;
                }
                Frame frame = transportServices.receiveFrameAsBytes(tmp);
                switch (frame.getMessageType()) {
                    case Login: {
                        Collection<UserDTO> users = outClientAdapterServices.getAllUsers();
                        if (!transportServices.checkLocal(frame) && !users.contains(frame.toUserDTO())) {
                            outClientAdapterServices.addUser(frame.toUserDTO());
                            transportServices.propagatePeer(frame, users);
                        } else if (transportServices.checkLocal(frame) && !users.contains(frame.toUserDTO())) {
                            outClientAdapterServices.addUser(frame.senderToUserDTO());
                        }
                        break;
                    }
                    case TextMessage: {
                        MessageDTO msg = frame.toMessageDTO();
                        if (msg == null)
                            break;
                        outClientAdapterServices.addMessage(msg);
                        break;
                    }
                    case Logout: {
                        Collection<UserDTO> users = outClientAdapterServices.getAllUsers();
                        if (users.contains(frame.toUserDTO())) {
                            outClientAdapterServices.removeUser(frame.toUserDTO());
                            transportServices.propagateLogout(frame, outClientAdapterServices.getAllUsers());
                        }
                        break;
                    }

                    case MyName: {
                        UserDTO user = null;

                        for (Field field : frame.getFields()) {
                            if (field.getFieldType() == FieldType.Name) {
                                String name = (String) field.getData();
                                user = new UserDTO(frame.getSender(), name);
                            }
                        }

                        if (user != null)
                            outClientAdapterServices.updateUsername(user);
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("QueueWorker stopped.");
    }

    public synchronized void stop() {
        isRunning = false;
        this.runningThread.interrupt();
    }

    private synchronized boolean isRunning() {
        return isRunning;
    }
}
