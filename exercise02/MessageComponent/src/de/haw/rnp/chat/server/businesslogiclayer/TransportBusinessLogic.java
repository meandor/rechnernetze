package de.haw.rnp.chat.server.businesslogiclayer;

import de.haw.rnp.chat.server.accesslayer.ITransportServices;
import de.haw.rnp.chat.server.accesslayer.IUserServices;
import de.haw.rnp.chat.server.dataaccesslayer.entities.Frame;

public class TransportBusinessLogic implements ITransportServices {

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
}
