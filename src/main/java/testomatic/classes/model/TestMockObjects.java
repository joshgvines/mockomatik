package testomatic.classes.model;

import java.util.ArrayList;
import java.util.List;

public class TestMockObjects {

    private List<List<String>> primaryTestMockList = new ArrayList<>();

    public List<List<String>> getPrimaryTestMockList() {
        return primaryTestMockList;
    }

    public void setPrimaryTestMockList(List<List<String>> primaryTestMockList) {
        this.primaryTestMockList = primaryTestMockList;
    }
}
