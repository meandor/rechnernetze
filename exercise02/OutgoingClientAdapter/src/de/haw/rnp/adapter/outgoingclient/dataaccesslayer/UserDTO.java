package de.haw.rnp.adapter.outgoingclient.dataaccesslayer;

import de.haw.rnp.util.AddressType;

public class UserDTO {

    private AddressType address;
    private String name;

    public UserDTO(AddressType address, String name){
        this.address = address;
        this.name = name;
    }

    public AddressType getAddress() {
        return address;
    }

    public void setAddress(AddressType address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
