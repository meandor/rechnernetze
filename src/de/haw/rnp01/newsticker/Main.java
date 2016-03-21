package de.haw.rnp01.newsticker;

public class Main {

    private static Controller controller;

    public static void main(String[] args) {
        controller = new Controller();
        controller.showView();
    }
}
