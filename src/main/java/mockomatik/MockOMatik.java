package mockomatik;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import mockomatik.classes.control.InputController;
import mockomatik.classes.service.scan.ObjectTypeManager;

public class MockOMatik {

    public static void main(String[] args) {
    	
    	ObjectTypeManager.readXml();
        Logo.runLogo();

        try {
            Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
            System.out.println("  > Your Location: " + path);
            System.out.println("  > Type 'EXIT' To Quit Program.");
            System.out.println("  > Type 'KILL' To Cancel/Restart Current Run.");

            final InputController inputController = new InputController();
            inputController.runProgram();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n > Exiting Application \n");
            System.exit(0);
        }
    }

}
