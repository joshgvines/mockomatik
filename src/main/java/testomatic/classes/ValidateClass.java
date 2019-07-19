package testomatic.classes;

public class ValidateClass {

    private String results;

    public ValidateClass() {

    }

    public void runTests(String packageWithNewTest) {
        if (packageWithNewTest != null) {
            System.out.println("add testing method here");
        }
    }

    public String getResults() {
        return results;
    }

}
