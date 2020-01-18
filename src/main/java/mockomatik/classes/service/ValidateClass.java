package mockomatik.classes.service;

import jgv.java.SHJLogger.Priority;
import jgv.java.SHJLogger.SHJLogger;

public class ValidateClass {

	private SHJLogger logger = new SHJLogger(this.getClass().toString());

    private String results;

    public void runTests(String packageWithNewTest) {
        if (packageWithNewTest != null) {
            System.out.println("add testing method here");
            logger.log(Priority.OKAY, "Run output here!");
        }
    }

    public String getResults() {
        return results;
    }

}
