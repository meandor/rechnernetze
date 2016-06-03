package de.haw.rnp.chat.server.accesslayer;

import de.haw.rnp.chat.server.dataaccesslayer.entities.Frame;

public interface ITransportServices {

    public void sendMessage(Frame frame);

    public void sendLogin(Frame frame);

    public void sendLogout(Frame frame);

    public void SendUsername(Frame frame);

}
