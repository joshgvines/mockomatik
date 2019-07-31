package testomatic.classes.model;

import java.util.List;

public class TestMethods {

    List<List<String>> primaryTestMethodList;
    List<String> methodVariableList;

    public List<List<String>> getPrimaryTestMethodList() {
        return primaryTestMethodList;
    }

    public void setPrimaryTestMethodList(List<List<String>> primaryTestMethodList) {
        this.primaryTestMethodList = primaryTestMethodList;
    }

    public List<String> getMethodVariableList() {
        return methodVariableList;
    }

    public void setMethodVariableList(List<String> variableList) {
        this.methodVariableList = variableList;
    }
}
