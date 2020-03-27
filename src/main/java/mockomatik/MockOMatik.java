package mockomatik;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mockomatik.classes.control.InputController;
import mockomatik.classes.service.scan.ObjectTypeManager;

public class MockOMatik {

    final static Logger logger = LogManager.getLogger(MockOMatik.class);
    
    public static void main(String[] args) {
        
        logger.info("Starting Application!");
        
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
            logger.error("Failed to load appliation", e);
        } finally {
            System.out.println("\n > Exiting Application \n");
            System.exit(0);
        }
    }

}
