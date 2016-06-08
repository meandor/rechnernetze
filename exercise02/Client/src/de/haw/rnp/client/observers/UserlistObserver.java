package de.haw.rnp.client.observers;

import de.haw.rnp.adapter.outgoingclient.dataaccesslayer.UserDTO;
import de.haw.rnp.util.IObserver;

import java.util.ArrayList;

public class UserlistObserver implements IObserver<ArrayList<UserDTO>> {
    @Override
    public void update(ArrayList<UserDTO> elem) {

    }
}
