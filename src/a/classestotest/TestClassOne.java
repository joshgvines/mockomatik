package a.classestotest;

public class TestClassOne {

    private String testClassInfoOne;
    private String testClassInfoTwo;

    public TestClassOne(String testClassInfoOne, String testClassInfoTwo) {
        this.testClassInfoOne = testClassInfoOne;
        this.testClassInfoTwo = testClassInfoTwo;
    }

    public void outputClassInfo() {
        System.out.println(testClassInfoOne);
    }

    public String getTestClassInfo() {
        return testClassInfoOne;
    }

}
