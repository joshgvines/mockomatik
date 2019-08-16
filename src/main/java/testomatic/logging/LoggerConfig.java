package testomatic.logging;

import java.util.logging.*;

public class LoggerConfig {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void testLog() {
        try {
            LogManager.getLogManager().reset();
            LOG.setLevel(Level.ALL);

            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            LOG.addHandler(ch);

            FileHandler fh = new FileHandler("MockOMatic.log", true);
            fh.setLevel(Level.FINE);
            LOG.addHandler(fh);
        } catch (Exception e) {
            LOG.info("jul logger has failed");
        }
    }

}
