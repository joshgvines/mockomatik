package testomatic.a.classestotest;

// import is commented out

public class DemoClassD {

    private String testClassInfoOne = "test";
    private String testClassInfoTwo = "test";

    // Constructor is partially commented out
    public DemoClassD(String testClassInfoOne, String testClassInfoTwo) {
        this.testClassInfoOne = testClassInfoOne;
//        this.testClassInfoTwo = testClassInfoTwo;


        /*
        unused characters in constructor
         */

    }

    public void outputClassInfo() {
        System.out.println(testClassInfoOne);
    }

    public String getTestClassInfo() {
        return testClassInfoOne;
    }
}
