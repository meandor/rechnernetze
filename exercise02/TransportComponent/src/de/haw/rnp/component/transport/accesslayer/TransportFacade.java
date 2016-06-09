package de.haw.rnp.component.transport.accesslayer;

import de.haw.rnp.adapter.outgoingpeer.accesslayer.IOutgoingPeerAdapterServices;
import de.haw.rnp.component.transport.businesslogiclayer.TransportBusinessLogic;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.component.user.accesslayer.IUserServices;

public class TransportFacade implements ITransportServices, ITransportServicesForIncomingPeerAdapter{

    private TransportBusinessLogic transportBusinessLogic;

    public TransportFacade(IUserServices userServices, IOutgoingPeerAdapterServices outgoingPeerAdapterServices){
        transportBusinessLogic = new TransportBusinessLogic(userServices, outgoingPeerAdapterServices);
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
    public void sendUsername(Frame frame) {
        transportBusinessLogic.sendUsername(frame);
    }

    @Override
    public Frame receiveFrameAsBytes(byte[] bytes) {
        return transportBusinessLogic.receiveFrameAsBytes(bytes);
    }
}
