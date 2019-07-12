package testomatic.a.classestotest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestClassOne {

    private String testClassInfoOne;
    private String testClassInfoTwo;

    Scanner keyboard = new Scanner(System.in);



//    public TestClassOne(String testClassInfoOne, String testClassInfoTwo) {
//        this.testClassInfoOne = testClassInfoOne;
//        this.testClassInfoTwo = testClassInfoTwo;
//    }

    public void testAnObject() throws IOException {

        File file = new File("AutoTest\\src\\a\\classestotest\\TestClassOne.java");

        FileReader fileReader = new FileReader(file);

        BufferedReader buffFeader = new BufferedReader(fileReader);

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
