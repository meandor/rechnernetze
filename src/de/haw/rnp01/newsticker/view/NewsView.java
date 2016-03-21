package de.haw.rnp01.newsticker.view;

/**
 * Created by daniel on 21.03.2016.
 */

import javax.swing.*;

public class NewsView {

    public void createView() {
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
