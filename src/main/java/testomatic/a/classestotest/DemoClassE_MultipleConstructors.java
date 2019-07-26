package testomatic.a.classestotest;

// Multi-Constructor Class with Setters and Getters
public class DemoClassE_MultipleConstructors {

    String testString;
    int testInt;

    public DemoClassE_MultipleConstructors() {
        this.testString = "test";
        this.testInt = 1;
    }

    public DemoClassE_MultipleConstructors(String testString) {
        this.testString = testString;
    }

    public DemoClassE_MultipleConstructors(String testString, int testInt) {
        this.testString = testString;
        this.testInt = testInt;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }
}
