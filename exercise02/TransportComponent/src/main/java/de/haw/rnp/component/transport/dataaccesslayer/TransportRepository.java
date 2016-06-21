package de.haw.rnp.component.transport.dataaccesslayer;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.util.AddressType;

/**
 * Repository for transport related Objects.
 */
public class TransportRepository {

    private UserDTO local;

    public TransportRepository(AddressType address, String name) {
        local = new UserDTO(address, name);
    }

    public TransportRepository(AddressType address) {
        local = new UserDTO(address, "");
    }

    public UserDTO getLocal() {
        return local;
    }
}
