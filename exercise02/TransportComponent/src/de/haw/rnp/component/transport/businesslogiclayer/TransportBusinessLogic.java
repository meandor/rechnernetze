package de.haw.rnp.component.transport.businesslogiclayer;

import de.haw.rnp.adapter.outgoingpeer.accesslayer.IOutgoingPeerAdapterServices;
import de.haw.rnp.component.transport.accesslayer.ITransportServices;
import de.haw.rnp.component.transport.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Field;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.component.transport.dataaccesslayer.enumerations.FieldType;
import de.haw.rnp.component.user.accesslayer.IUserServices;
import de.haw.rnp.util.AddressType;
import de.haw.rnp.util.ChatUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TransportBusinessLogic implements ITransportServices, ITransportServicesForIncomingPeerAdapter {

    private IUserServices userServices;
    private IOutgoingPeerAdapterServices outgoingPeerAdapterServices;

    public TransportBusinessLogic(IUserServices userServices, IOutgoingPeerAdapterServices outgoingPeerAdapterServices){
        this.userServices = userServices;
        this.outgoingPeerAdapterServices = outgoingPeerAdapterServices;
    }

    @Override
    public void sendMessage(Frame frame) {
        outgoingPeerAdapterServices.sendData(frame.getRecipient(), frame.getFrameAsBytes());
    }

    @Override
    public void sendLogin(Frame frame) {
        outgoingPeerAdapterServices.sendData(frame.getRecipient(), frame.getFrameAsBytes());
    }

    @Override
    public void sendLogout(Frame frame) {
        outgoingPeerAdapterServices.sendData(frame.getRecipient(), frame.getFrameAsBytes());
    }

    @Override
    public void SendUsername(Frame frame) {
        outgoingPeerAdapterServices.sendData(frame.getRecipient(), frame.getFrameAsBytes());
    }

    @Override
    public Frame receiveFrameAsBytes(byte[] bytes) {
        Frame frame = new Frame(Arrays.copyOf(bytes, 12));
        frame.setFields(parseFields(Arrays.copyOfRange(bytes, 12, bytes.length)));
        System.out.println(frame.getLength() + " " + frame.getMessageType());
        return frame;
    }

    private Collection<Field> parseFields(byte[] bytes){
        Collection<Field> fields = new ArrayList<>();

        while(bytes.length > 0){
            FieldType fieldType = FieldType.fromBytes(Arrays.copyOf(bytes, 2));
            int length = ChatUtil.byteArrayToInt(Arrays.copyOfRange(bytes, 2, 4));
            bytes = ChatUtil.cut(bytes, 4);
            switch(fieldType){
                case IP: fields.add(parseIP(Arrays.copyOf(bytes, length), fieldType, length)); break;
                case Port: fields.add(parsePort(Arrays.copyOf(bytes, length), fieldType, length)); break;
                case Name: fields.add(parseName(Arrays.copyOf(bytes, length), fieldType, length)); break;
                case Text: fields.add(parseText(Arrays.copyOf(bytes, length), fieldType, length)); break;
                case UserList: fields.add(parseUserlist(Arrays.copyOf(bytes, length), fieldType, length)); break;
            }
            bytes = ChatUtil.cut(bytes, length);
        }

        return fields;
    }

    private Field parseIP(byte[] bytes, FieldType fieldType, int length){
        try {
            InetAddress address = InetAddress.getByAddress(bytes);
            return new Field<>(fieldType, length, address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Field parsePort(byte[] bytes, FieldType fieldType, int length){
        return new Field<>(fieldType, length, ChatUtil.byteArrayToInt(bytes));
    }

    private Field parseName(byte[] bytes, FieldType fieldType, int length){
        return  new Field<>(fieldType, length, new String(bytes, StandardCharsets.US_ASCII));
    }

    private Field parseText(byte[] bytes, FieldType fieldType, int length){
        return new Field<>(fieldType, length, new String(bytes, StandardCharsets.US_ASCII));
    }

    private Field parseUserlist(byte[] bytes, FieldType fieldType, int length){
        Collection<AddressType> addresses = new ArrayList<>();
        for(int x = 0; x < (length / 8); x++){
            try {

                InetAddress ip = InetAddress.getByAddress(Arrays.copyOf(bytes, 4));
                int port = ChatUtil.byteArrayToInt(Arrays.copyOfRange(bytes, 6, 8));
                addresses.add(new AddressType(ip, port));

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            bytes = ChatUtil.cut(bytes, 8);
        }
        return new Field<>(fieldType, length, addresses);
    }

}