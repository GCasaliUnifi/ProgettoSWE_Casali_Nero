package main;

import controller.Controller;
import view.ViewInterface;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        ViewInterface viewInterface = new ViewInterface(new Controller(), "", "");
        viewInterface.init();
    }
}
