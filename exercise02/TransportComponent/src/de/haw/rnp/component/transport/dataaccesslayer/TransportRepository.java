package de.haw.rnp.component.transport.dataaccesslayer;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.component.transport.dataaccesslayer.entities.Frame;
import de.haw.rnp.util.AddressType;

public class TransportRepository {

    private UserDTO local;

    public TransportRepository(Frame frame){
        local = frame.toUserDTO();
    }

    public TransportRepository(AddressType address){
        local = new UserDTO(address, "");
    }

    public UserDTO getLocal(){
        return local;
    }
}
