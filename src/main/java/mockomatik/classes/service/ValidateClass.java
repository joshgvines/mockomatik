package mockomatik.classes.service;

import io.jgv.logger.impl.SHJLoggerImpl;

public class ValidateClass {

	private SHJLoggerImpl logger = SHJLoggerImpl.getLogger();

    private String results;

    public void runTests(String packageWithNewTest) {
        if (packageWithNewTest != null) {
            System.out.println("add testing method here");
            logger.okay("Run output here!");
        }
    }

    public String getResults() {
        return results;
    }

}
