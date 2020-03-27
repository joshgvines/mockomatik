package mockomatik.classes.service;

public class ValidateClass {

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
