package mockomatik.classes.model;

import java.util.List;

public class TestMethods {

    private List<List<String>> primaryTestMethodList;
    private List<String> methodVariableList;

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
