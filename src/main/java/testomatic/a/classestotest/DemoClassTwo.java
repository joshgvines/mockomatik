package testomatic.a.classestotest;

public class DemoClassTwo {

    private String testClassInfoOne;
    private String testClassInfoTwo;

//    public DemoClassTwo(String testClassInfoOne, String testClassInfoTwo) {
//        this.testClassInfoOne = testClassInfoOne;
//        this.testClassInfoTwo = testClassInfoTwo;
//    }

    public void outputClassInfo() {
        System.out.println(testClassInfoOne);
    }

    public String getTestClassInfo() {
        return testClassInfoOne;
    }
}
