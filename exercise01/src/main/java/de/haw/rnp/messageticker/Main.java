package de.haw.rnp.messageticker;

public class Main {
    public static void main(String[] args) {
        int threadCount = 10;
        if (args.length > 0) {
            threadCount = Integer.parseInt(args[0]);
        }
        Controller controller = new Controller(threadCount);
        controller.showView();
        Thread controllerThread = new Thread(controller);
        controllerThread.start();
    }
}