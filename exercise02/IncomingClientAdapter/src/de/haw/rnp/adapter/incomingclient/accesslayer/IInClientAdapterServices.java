package de.haw.rnp.adapter.incomingclient.accesslayer;

import de.haw.rnp.adapter.incomingclient.dataaccesslayer.FrameDTO;
import de.haw.rnp.util.AddressType;

public interface IInClientAdapterServices {

    boolean startServer(AddressType address);

    boolean sendMessage(FrameDTO frame);

    boolean sendLogin(FrameDTO frame);

    boolean sendLogout(FrameDTO frame);

    boolean sendUsername(FrameDTO frame);

}
