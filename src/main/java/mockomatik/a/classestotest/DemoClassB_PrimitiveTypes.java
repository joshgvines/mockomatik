package mockomatik.a.classestotest;

import java.util.Scanner;

public class DemoClassB_PrimitiveTypes {

    private String testClassInfoOne;
    String testClassInfoTwo;

    //Primitive types
    private float testfloat = 1.1F;
    private Float testFloat = 1.2F;

    private double testdouble = 1.1;
    protected double testDouble = 1.01;

    private char testChar = 'a';

    private short testshort = 100;
    private Short testShort = 300;

    public long testlong = 1000000000000L;
    private Long testLong = 50000000000000L;

    protected int testint = 3000000;
    private Integer testInt = 7000000;

    public byte testbyte = 1;
    private Byte testByte = 2;

    Scanner keyboard = new Scanner(System.in);

    // Partially unused constructor;
    public DemoClassB_PrimitiveTypes(float testfloat, double testdouble, short testshort) {
        this.testfloat = testfloat;
        this.testdouble = testdouble;
    }

    // Unused methods
    public String getTestClassInfoOne() {
        return testClassInfoOne;
    }

    public void setTestClassInfoOne(String testClassInfoOne) {
        this.testClassInfoOne = testClassInfoOne;
    }

    public String getTestClassInfoTwo() {
        return testClassInfoTwo;
    }

    public void setTestClassInfoTwo(String testClassInfoTwo) {
        this.testClassInfoTwo = testClassInfoTwo;
    }

    public float getTestfloat() {
        return testfloat;
    }

    public void setTestfloat(float testfloat) {
        this.testfloat = testfloat;
    }

    public Float getTestFloat() {
        return testFloat;
    }

    public void setTestFloat(Float testFloat) {
        this.testFloat = testFloat;
    }

    public double getTestdouble() {
        return testdouble;
    }

    public void setTestdouble(double testdouble) {
        this.testdouble = testdouble;
    }

    public double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(double testDouble) {
        this.testDouble = testDouble;
    }

    public char getTestChar() {
        return testChar;
    }

    public void setTestChar(char testChar) {
        this.testChar = testChar;
    }

    public short getTestshort() {
        return testshort;
    }

    public void setTestshort(short testshort) {
        this.testshort = testshort;
    }

    public Short getTestShort() {
        return testShort;
    }

    public void setTestShort(Short testShort) {
        this.testShort = testShort;
    }

    public String aStringReturnTypeMethod() {
        return "method";
    }

}
