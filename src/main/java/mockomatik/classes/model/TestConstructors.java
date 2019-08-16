package mockomatik.classes.model;

import java.util.List;

public class TestConstructors {

    List<List<String>> primaryConstructorList;
    List<String> constructorArguments;

    public List<List<String>> getPrimaryConstructorList() {
        return primaryConstructorList;
    }

    public void setPrimaryConstructorList(List<List<String>> primaryConstructorList) {
        this.primaryConstructorList = primaryConstructorList;
    }

    public List<String> getConstructorArguments() {
        return constructorArguments;
    }

    public void setConstructorArguments(List<String> constructorArguments) {
        this.constructorArguments = constructorArguments;
    }
}
