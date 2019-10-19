package mockomatik;

import mockomatik.classes.control.InputController;
import mockomatik.logging.LoggerConfig;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.*;

public class MockOMatik {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {

        LoggerConfig.testLog();
        LOG.fine(" >> LOG TEST << ");

        try {
            final Logo logo = new Logo();
            logo.runLogo();
        } catch (Exception e) {
            LOG.info("Logo did not load properly" + e);
        }
        try {
            Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
            System.out.println("  > Your Location: " + path);
            System.out.println("  > Type 'EXIT' To Quit Program.");
            System.out.println("  > Type 'KILL' To Cancel/Restart Current Run.");

            // TODO: better way to confirm location
            if (!path.equals("C:\\") && !path.equals("root") && !(path.toString().length() < 4)) {
                final InputController inputController = new InputController();
                inputController.runProgram();
            } else {
                System.out.println(" > Do not run in root drive, folder, or location!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.severe("Could not find your location!" + e);
        } finally {
            System.exit(0);
        }
    }

}