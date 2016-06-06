package de.haw.rnp.chat.server.businesslogiclayer;

import de.haw.rnp.chat.server.accesslayer.ITransportServices;
import de.haw.rnp.chat.server.accesslayer.ITransportServicesForIncomingPeerAdapter;
import de.haw.rnp.chat.server.accesslayer.IUserServices;
import de.haw.rnp.chat.server.dataaccesslayer.entities.Frame;

import java.util.Arrays;

public class TransportBusinessLogic implements ITransportServices, ITransportServicesForIncomingPeerAdapter {

    private IUserServices userServices;

    public TransportBusinessLogic(IUserServices userServices){
        this.userServices = userServices;
    }

    @Override
    public void sendMessage(Frame frame) {

    }

    @Override
    public void sendLogin(Frame frame) {

    }

    @Override
    public void sendLogout(Frame frame) {

    }

    @Override
    public void SendUsername(Frame frame) {

    }

    @Override
    public void recieveFrameAsBytes(byte[] bytes) {
        Frame frame = new Frame(Arrays.copyOf(bytes, 12));

    }

}