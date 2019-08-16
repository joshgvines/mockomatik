package mockomatik.a.classestotest;

// import is commented out

public class DemoClassD_IrregularConstructor {

    private String testClassInfoOne = "test";
    private String testClassInfoTwo = "test";

    // Constructor is partially commented out
                                       public DemoClassD_IrregularConstructor(String testClassInfoOne,
                                                                              String testClassInfoTwo) {
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
