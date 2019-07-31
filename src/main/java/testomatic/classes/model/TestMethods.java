package testomatic.classes.model;

import java.util.List;

public class TestMethods {

    List<List<String>> primaryTestMethodList;
    List<String> testMethodArguments;

    public List<List<String>> getPrimaryTestMethodList() {
        return primaryTestMethodList;
    }

    public void setPrimaryTestMethodList(List<List<String>> primaryTestMethodList) {
        this.primaryTestMethodList = primaryTestMethodList;
    }

    public List<String> getTestMethodArguments() {
        return testMethodArguments;
    }

    public void setTestMethodArguments(List<String> testMethodArguments) {
        this.testMethodArguments = testMethodArguments;
    }
}
