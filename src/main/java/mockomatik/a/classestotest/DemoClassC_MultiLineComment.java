package mockomatik.a.classestotest;

// A Basic Class
public class DemoClassC_MultiLineComment {

    private String testClassInfoOne = "test value One";
    private String testClassInfoTwo = "test value Two";

    public DemoClassC_MultiLineComment(String testClassInfoOne, String testClassInfoTwo) {
        this.testClassInfoOne = testClassInfoOne;
        this.testClassInfoTwo = testClassInfoTwo;
    }

    /*
    blah
    blah
    String
    double
    Double
    public
    String these lines should be ignored
     */

    public void printClassInfo() {
        System.out.println(testClassInfoOne);
    }

    public String getTestClassInfoOne() {
        return testClassInfoOne;
    }

    // A method with extra space and unused variables
    public String getTestClassInfoTwo() {

        int unused = 100;

        return testClassInfoTwo;


    }
}
