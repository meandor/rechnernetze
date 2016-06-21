package de.haw.rnp.adapter.incomingclient.accesslayer;

import de.haw.rnp.adapter.incomingclient.dataaccesslayer.FrameDTO;
import de.haw.rnp.util.AddressType;

public interface IInClientAdapterServices {

    boolean startServer(AddressType address, boolean TCP);

    void stopServer();

    boolean sendMessage(FrameDTO frame, boolean isTCP);

    boolean sendLogin(FrameDTO frame, String name, boolean isTCP);

    boolean sendLogout(FrameDTO frame, boolean isTCP);

    boolean sendUsername(FrameDTO frame, boolean isTCP);

}
