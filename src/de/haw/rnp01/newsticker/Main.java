package de.haw.rnp01.newsticker;

public class Main {

    private static Controller controller;

    public static void main(String[] args) {
        int threadCount = 1;
        if (args.length > 0) {
            threadCount = Integer.parseInt(args[0]);
        }
        controller = new Controller(threadCount);
        controller.showView();
        controller.start();
    }
}
