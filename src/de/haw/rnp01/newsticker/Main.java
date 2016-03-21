package de.haw.rnp01.newsticker;

import de.haw.rnp01.newsticker.view.NewsView;

public class Main {

    public static void main(String[] args) {
        NewsView view = new NewsView();
        view.createView();
        System.out.println("Hello World!");
    }
}
