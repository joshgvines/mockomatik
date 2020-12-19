package com.mockomatik.model;

import java.util.List;

public class TestClassModel {
    private final String fileName;
    private final List<String> importList;
    private final List<String> constructorList;
    private final List<String> mockedVariableList;
    private final List<String> variableList;
    private final List<String> methodList;


    public TestClassModel(String fileName,
                          List<String> getImportList,
                          List<String> constructorList,
                          List<String> mockedVariableList,
                          List<String> variableList,
                          List<String> methodList) {

        this.fileName = fileName;
        this.importList = getImportList;
        this.constructorList = constructorList;
        this.mockedVariableList = mockedVariableList;
        this.variableList = variableList;
        this.methodList = methodList;
    }

    public String getClassName() {
        return fileName;
    }

    public List<String> getImportList() {
        return importList;
    }

    public List<String> getConstructorList() {
        return constructorList;
    }

    public List<String> getMockedVariableList() {
        return mockedVariableList;
    }

    public List<String> getVariableList() {
        return variableList;
    }

    public List<String> getMethodList() {
        return methodList;
    }


}
