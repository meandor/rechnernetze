package de.haw.rnp.component.transport.accesslayer;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.adapter.outgoingpeer.accesslayer.IOutgoingPeerAdapterServices;
import de.haw.rnp.component.transport.businesslogiclayer.TransportBusinessLogic;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.util.AddressType;

import java.util.Collection;

public class TransportFacade implements ITransportServices, ITransportServicesForIncomingPeerAdapter {

    private TransportBusinessLogic transportBusinessLogic;

    public TransportFacade(IOutgoingPeerAdapterServices outgoingPeerAdapterServices) {
        transportBusinessLogic = new TransportBusinessLogic(outgoingPeerAdapterServices);
    }

    @Override
    public void sendMessage(Frame frame, boolean isTCP) {
        transportBusinessLogic.sendMessage(frame, isTCP);
    }

    @Override
    public void sendLogin(Frame frame, boolean isTCP) {
        transportBusinessLogic.sendLogin(frame, isTCP);
    }

    @Override
    public void sendLogout(Frame frame, boolean isTCP) {
        transportBusinessLogic.sendLogout(frame, isTCP);
    }

    @Override
    public void sendUsername(Frame frame, boolean isTCP) {
        transportBusinessLogic.sendUsername(frame, isTCP);
    }

    @Override
    public void setLocal(AddressType address, String name) {
        transportBusinessLogic.setLocal(address, name);
    }

    @Override
    public Frame receiveFrameAsBytes(byte[] bytes) {
        return transportBusinessLogic.receiveFrameAsBytes(bytes);
    }

    @Override
    public void propagatePeer(Frame frame, Collection<UserDTO> recipients, boolean isTCP) {
        transportBusinessLogic.propagatePeer(frame, recipients, isTCP);
    }

    @Override
    public void propagateLogout(Frame frame, Collection<UserDTO> recipients, boolean isTCP) {
        transportBusinessLogic.propagateLogout(frame, recipients, isTCP);
    }

    @Override
    public boolean checkLocal(Frame frame) {
        return transportBusinessLogic.checkLocal(frame);
    }
}
