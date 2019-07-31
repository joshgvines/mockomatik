package testomatic;

import testomatic.classes.control.InputController;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class TestOMatic {
    public static void main(String[] args) {
        Logo logo = new Logo();
        logo.runLogo();

        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
        System.out.println("  >>> Your Location: " + path);
        System.out.println("  >>> Type 'EXIT' Or Use [ ctrl + c ] To Quit Program.");
        System.out.println("  >>> Type 'KILL' To Cancel/Restart Current Run.");

        // TODO: better way to confirm location
        if (!path.equals("C:\\") && !path.equals("root") && !(path.toString().length() < 4)) {
            InputController inputController = new InputController();
            inputController.runProgram();
        } else {
            System.out.println("Do not run in root drive, folder, or location!");
        }
    }
}
