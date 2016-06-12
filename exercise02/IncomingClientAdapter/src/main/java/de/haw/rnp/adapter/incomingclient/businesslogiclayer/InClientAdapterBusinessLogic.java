package de.haw.rnp.adapter.incomingclient.businesslogiclayer;

import de.haw.rnp.adapter.incomingclient.accesslayer.IInClientAdapterServices;
import de.haw.rnp.adapter.incomingclient.dataaccesslayer.FieldDTO;
import de.haw.rnp.adapter.incomingclient.dataaccesslayer.FrameDTO;
import de.haw.rnp.adapter.incomingpeer.accesslayer.IIncomingPeerAdapterServices;
import de.haw.rnp.adapter.outgoingpeer.accesslayer.IOutgoingPeerAdapterServices;
import de.haw.rnp.component.transport.accesslayer.ITransportServices;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Field;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.util.AddressType;
import de.haw.rnp.util.ChatUtil;
import de.haw.rnp.util.enumerations.FieldType;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class InClientAdapterBusinessLogic implements IInClientAdapterServices {

    IIncomingPeerAdapterServices incomingPeerAdapterServices;
    ITransportServices transportServices;

    public InClientAdapterBusinessLogic(IIncomingPeerAdapterServices incomingPeerAdapterServices, ITransportServices transportServices) {
        this.transportServices = transportServices;
        this.incomingPeerAdapterServices = incomingPeerAdapterServices;
    }

    @Override
    public boolean startServer(AddressType address) {
        incomingPeerAdapterServices.startServer(address);
        incomingPeerAdapterServices.startQueueWorker();
        transportServices.setLocal(address, "");
        return true;
    }

    @Override
    public void stopServer() {
        incomingPeerAdapterServices.stopServer();
        incomingPeerAdapterServices.stopQueueWorker();
    }

    @Override
    public boolean sendMessage(FrameDTO frame) {
        transportServices.sendMessage(toFrame(frame));
        return true;
    }

    @Override
    public boolean sendLogin(FrameDTO frame, String name) {
        transportServices.setLocal(frame.getSender(), name);
        transportServices.sendLogin(toFrame(frame));
        return true;
    }

    @Override
    public boolean sendLogout(FrameDTO frame) {
        transportServices.sendLogout(toFrame(frame));
        return true;
    }

    @Override
    public boolean sendUsername(FrameDTO frame) {
        transportServices.sendUsername(toFrame(frame));
        return true;
    }

    private Frame toFrame(FrameDTO framedto){
        Frame frame = new Frame(framedto.getSender(),
                                framedto.getRecipient(),
                                framedto.getVersion(),
                                framedto.getMessageType(),
                                framedto.getLength());

        for(FieldDTO fielddto : framedto.getFields()){
            frame.addField(toField(fielddto));
        }
        return frame;
    }

    @SuppressWarnings("unchecked")
    private Field toField(FieldDTO fielddto){
        FieldType fieldType = fielddto.getFieldType();

        if(fieldType == FieldType.IP){
            InetAddress tmp = (InetAddress) fielddto.getData();
            return new Field<>(fieldType, fielddto.getLength(), tmp);
        }else if(fieldType == FieldType.Port){
            int tmp = (Integer) fielddto.getData();
            return new Field<>(fieldType, fielddto.getLength(), tmp);
        }else if(fieldType == FieldType.Name){
            String tmp = (String) fielddto.getData();
            return new Field<>(fieldType, fielddto.getLength(), tmp);
        }else if(fieldType == FieldType.Text){
            String tmp = (String) fielddto.getData();
            return new Field<>(fieldType, fielddto.getLength(), tmp);
        }else{
            Collection<AddressType> tmp = (Collection) fielddto.getData();
            return new Field<>(fieldType, fielddto.getLength(), tmp);
        }
    }
}
