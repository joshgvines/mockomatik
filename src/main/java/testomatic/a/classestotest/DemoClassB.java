package testomatic.a.classestotest;

import java.util.Scanner;

public class DemoClassB {

    private String testClassInfoOne;
    private String testClassInfoTwo;

    Scanner keyboard = new Scanner(System.in);

    public DemoClassB(String testClassInfoOne, String testClassInfoTwo) {
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
