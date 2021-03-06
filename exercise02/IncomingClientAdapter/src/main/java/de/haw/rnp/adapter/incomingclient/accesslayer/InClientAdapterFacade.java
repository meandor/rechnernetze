package de.haw.rnp.adapter.incomingclient.accesslayer;

import de.haw.rnp.adapter.incomingclient.businesslogiclayer.InClientAdapterBusinessLogic;
import de.haw.rnp.adapter.incomingclient.dataaccesslayer.FrameDTO;
import de.haw.rnp.adapter.incomingpeer.accesslayer.IIncomingPeerAdapterServices;
import de.haw.rnp.component.transport.accesslayer.ITransportServices;
import de.haw.rnp.util.AddressType;
import de.haw.rnp.util.enumerations.MessageType;

public class InClientAdapterFacade implements IInClientAdapterServices {

    private InClientAdapterBusinessLogic businessLogic;

    public InClientAdapterFacade(IIncomingPeerAdapterServices incomingPeerAdapterServices,
                                 ITransportServices transportServices){
        businessLogic = new InClientAdapterBusinessLogic(incomingPeerAdapterServices, transportServices);
    }

    @Override
    public boolean startServer(AddressType address, boolean TCP) {
        if(address.getIp() == null)
            return false;
        return businessLogic.startServer(address, TCP);
    }

    @Override
    public void stopServer() {
        businessLogic.stopServer();
    }

    @Override
    public boolean sendMessage(FrameDTO frame, boolean isTCP) {
        if(!checkHeader(frame, MessageType.TextMessage))
            return false;
        return businessLogic.sendMessage(frame, isTCP);
    }

    @Override
    public boolean sendLogin(FrameDTO frame, String name, boolean isTCP) {
        if(!checkHeader(frame, MessageType.Login))
            return false;
        return businessLogic.sendLogin(frame, name, isTCP);
    }

    @Override
    public boolean sendLogout(FrameDTO frame, boolean isTCP) {
        if(!checkHeader(frame, MessageType.Logout))
            return false;
        return businessLogic.sendLogout(frame, isTCP);
    }

    @Override
    public boolean sendUsername(FrameDTO frame, boolean isTCP) {
        if(!checkHeader(frame, MessageType.MyName))
            return false;
        return businessLogic.sendUsername(frame, isTCP);
    }

    private boolean checkHeader(FrameDTO framedto, MessageType expected){
        if(framedto.getRecipient() == null)
            return false;
        if(framedto.getSender() == null)
            return false;
        if(framedto.getFields().size() != framedto.getLength())
            return false;
        if(framedto.getMessageType() != expected)
            return false;
        return true;
    }
}
