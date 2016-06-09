package de.haw.rnp.adapter.incomingclient.accesslayer;

import de.haw.rnp.adapter.incomingpeer.accesslayer.IIncomingPeerAdapterServices;
import de.haw.rnp.adapter.incomingpeer.accesslayer.IncomingPeerAdapterFacade;
import de.haw.rnp.adapter.outgoingpeer.accesslayer.IOutgoingPeerAdapterServices;
import de.haw.rnp.adapter.outgoingpeer.accesslayer.OutgoingPeerAdapterFacade;
import de.haw.rnp.component.transport.accesslayer.ITransportServices;
import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.component.transport.accesslayer.TransportFacade;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Field;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.util.enumerations.FieldType;
import de.haw.rnp.util.enumerations.MessageType;
import de.haw.rnp.component.user.accesslayer.IUserServices;
import de.haw.rnp.component.user.accesslayer.UserFacade;
import de.haw.rnp.util.AddressType;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    static IIncomingPeerAdapterServices incomingPeerAdapterServices;
    static ITransportServices transportServices;
    static IUserServices userServices;
    static ITransportServicesForIncomingPeerAdapter transportServicesForIncomingPeerAdapter;
    static IOutgoingPeerAdapterServices outgoingPeerAdapterServices;

    public static void main(String[] args) {
        userServices = new UserFacade();
        outgoingPeerAdapterServices = new OutgoingPeerAdapterFacade();
        transportServices = new TransportFacade(userServices, outgoingPeerAdapterServices);
        transportServicesForIncomingPeerAdapter = new TransportFacade(userServices, outgoingPeerAdapterServices);
        incomingPeerAdapterServices = new IncomingPeerAdapterFacade(transportServicesForIncomingPeerAdapter);

        InetAddress net = null;

        try {
            net = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        AddressType address = new AddressType(net, 10000);
        incomingPeerAdapterServices.startServer(address);
        incomingPeerAdapterServices.startQueueWorker();

        Frame frame = new Frame(address, address, 1, MessageType.Login, 3);
        frame.addField(new Field<>(FieldType.IP, 4, address.getIp()));
        frame.addField(new Field<>(FieldType.Port, 2, address.getPort()));
        frame.addField(new Field<>(FieldType.Name, 3, "FOO"));

        System.out.println("fuck off");
        transportServices.sendLogin(frame);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.setMessageType(MessageType.MyName);
        transportServices.sendLogin(frame);
    }
}
