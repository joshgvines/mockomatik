package com.application;

import com.application.classes.InputController;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Application {
    public static void main(String[] args) {
        Logo logo = new Logo();
        logo.runLogo();

        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
        System.out.println("  >>> Your Location: " + path);
        System.out.println("  >>> Type 'EXIT' or use [ ctrl + c ] to quit program.");
        if (!(path.equals("C:\\")) && !(path.equals("root"))) {
            InputController inputController = new InputController();
            inputController.runProgram();
        } else {
            System.out.println("Do not run in root drive");
        }
    }
}
