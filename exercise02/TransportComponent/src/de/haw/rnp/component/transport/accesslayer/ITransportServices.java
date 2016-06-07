package de.haw.rnp.component.transport.accesslayer;

import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;

public interface ITransportServices {

    void sendMessage(Frame frame);

    void sendLogin(Frame frame);

    void sendLogout(Frame frame);

    void SendUsername(Frame frame);

}
