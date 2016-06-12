package de.haw.rnp.adapter.outgoingclient.dataaccesslayer;

import de.haw.rnp.util.AddressType;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        return Objects.equals(this.address, other.address);
    }

    @Override
    public int hashCode() {
        int result = getAddress().hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
