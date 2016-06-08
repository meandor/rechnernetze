package de.haw.rnp.adapter.outgoingclient.accesslayer;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.MessageDTO;
import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;

public interface IOutClientAdapterServicesForInPeer {

    public void addMessage(MessageDTO message);

    public void addUser(UserDTO user);

    public void removeUser(UserDTO user);

    public void updateUsername(UserDTO user);

}
