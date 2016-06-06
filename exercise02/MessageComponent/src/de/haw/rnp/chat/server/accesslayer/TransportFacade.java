package de.haw.rnp.chat.server.accesslayer;

import de.haw.rnp.chat.server.businesslogiclayer.TransportBusinessLogic;
import de.haw.rnp.chat.server.dataaccesslayer.entities.Frame;

public class TransportFacade implements ITransportServices, ITransportServicesForIncomingPeerAdapter{

    private TransportBusinessLogic transportBusinessLogic;

    public TransportFacade(IUserServices userServices){
        transportBusinessLogic = new TransportBusinessLogic(userServices);
    }

    @Override
    public void sendMessage(Frame frame) {
        transportBusinessLogic.sendMessage(frame);
    }

    @Override
    public void sendLogin(Frame frame) {
        transportBusinessLogic.sendLogin(frame);
    }

    @Override
    public void sendLogout(Frame frame) {
        transportBusinessLogic.sendLogout(frame);
    }

    @Override
    public void SendUsername(Frame frame) {
        transportBusinessLogic.SendUsername(frame);
    }

    @Override
    public Frame recieveFrameAsBytes(byte[] bytes) {
        return transportBusinessLogic.recieveFrameAsBytes(bytes);
    }
}
