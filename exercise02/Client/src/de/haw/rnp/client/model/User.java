package de.haw.rnp.client.model;

import de.haw.rnp.util.AddressType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {

    private StringProperty name;
    private StringProperty hostname;
    private IntegerProperty port;

    private AddressType address;

    public User(String hostname, int port, String name) {
        this.name = new SimpleStringProperty(name);
        this.hostname = new SimpleStringProperty(hostname);
        this.port = new SimpleIntegerProperty(port);

    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getHostName() {
        return hostname.get();
    }

    public StringProperty hostNameProperty() {
        return hostname;
    }

    public void setHostName(String hostName) {
        this.hostname.set(hostName);
    }

    public int getPort() {
        return port.get();
    }

    public IntegerProperty portProperty() {
        return port;
    }

    public void setPort(int port) {
        this.port.set(port);
    }

    public AddressType getAddress() {
        return address;
    }

    public void setAddress(AddressType address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name + " (" + address.getIp().getHostAddress() + ":" + address.getPort() + ")";
    }

    private void updateAddress(){
        try {
            address = new AddressType(InetAddress.getByName(hostname.get()), port.get());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
