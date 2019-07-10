package a.classestotest;

import java.util.*;

public class TestClassOne {

    private String testClassInfoOne;
    private String testClassInfoTwo;

    Scanner keyboard = new Scanner(System.in);

    public TestClassOne(String testClassInfoOne, String testClassInfoTwo) {
        this.testClassInfoOne = testClassInfoOne;
        this.testClassInfoTwo = testClassInfoTwo;
    }

    public void testAnObject() {
        String testIn = keyboard.nextLine();
        System.out.println(testIn);
    }

    public void outputClassInfo() {
        System.out.println(testClassInfoOne);
    }

    public String getTestClassInfo() {
        return testClassInfoOne;
    }

}
