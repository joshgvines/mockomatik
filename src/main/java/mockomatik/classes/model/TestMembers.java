package mockomatik.classes.model;

import java.util.ArrayList;
import java.util.List;

public class TestMembers {

    private List<List<String>> primaryTestMockList = new ArrayList<>();
    private List<List<String>> primaryTestVariableList = new ArrayList<>();

    public List<List<String>> getPrimaryTestMockList() {
        return primaryTestMockList;
    }

    public void setPrimaryTestMockList(List<List<String>> primaryTestMockList) {
        this.primaryTestMockList = primaryTestMockList;
    }

    public List<List<String>> getPrimaryTestVariableList() {
        return primaryTestVariableList;
    }

    public void setPrimaryVariablesList(List<List<String>> primaryTestVariableList) {
        this.primaryTestVariableList = primaryTestVariableList;
    }
}
