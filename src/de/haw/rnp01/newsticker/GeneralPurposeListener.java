package de.haw.rnp01.newsticker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by on 21.03.2016.
 */
public class GeneralPurposeListener implements ActionListener {

    private Controller controller;

    public GeneralPurposeListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.controller.performAction(e);
    }
}
