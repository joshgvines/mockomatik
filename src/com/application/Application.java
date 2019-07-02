package com.application;

import com.application.classes.InputController;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Application {
    public static void main(String[] args) {

        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
        System.out.println("Your Location: " + path);
        if (!path.equals("C:\\") && !path.equals("root")) {
            InputController inputController = new InputController();
            inputController.runProgram();
        } else {
            System.out.println("Do not run in root drive");
        }
    }
}
