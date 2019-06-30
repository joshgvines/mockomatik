package com.application;

import com.application.classes.InputController;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Application {
    public static void main(String[] args) {
        System.out.println("_________________________________________________________________");
        System.out.println(" _______________________________________________________________ ");
        System.out.println("|_   _| ____/ ___|_   _|  / _ \\( |  \\/  |  / \\|_   _|_ _/ ___|||");
        System.out.println("  | | |  _| \\___ \\ | |   | | | |/| |\\/| | / _ \\ | |  | | |    ");
        System.out.println("  | | | |___ ___) || |   | |_| | | |  | |/ ___ \\| |  | | |______ ");
        System.out.println("  |_| |_____|____/ |_|    \\___/  |_|  |_/_/   \\_|_| |___\\____|||");
        System.out.println("_________________________________________________________________");

        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
        System.out.println("Your Location: " + path);
        if (!path.equals("C:\\") && !path.equals("root")) {
            InputController inputController = new InputController();
            inputController.runProgram();
        } else {
            System.out.println("Do not run in root drive!");
        }
    }
}
