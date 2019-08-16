package mockomatik.classes.service;

import java.util.logging.Logger;

public class ValidateClass {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private String results;

    public void runTests(String packageWithNewTest) {
        if (packageWithNewTest != null) {
            System.out.println("add testing method here");
        }
    }

    public String getResults() {
        return results;
    }

}
