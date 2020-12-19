package com.mockomatik.service.create;

import com.mockomatik.model.TestClassModel;

import java.util.List;

public class TestClassModelBuilder {
    /*
     * DEFAULT CLASS LAYOUT:
     * > IMPORTS
     * > VARIABLES
     * > CONSTRUCTORS
     * > METHODS
     * > EXTRAS
     */
    private String fileName;
    private List<String> importList;
    private List<String> constructorList;
    private List<String> mockedVariableList;
    private List<String> variableList;
    private List<String> methodList;

    public TestClassModelBuilder className(final String fileName) {
        this.fileName = fileName;
        return this;
    }

    public TestClassModelBuilder importList(final List<String> importList) {
        this.importList = importList;
        return this;
    }

    public TestClassModelBuilder constructorList(final List<String> constructorList) {
        this.constructorList = constructorList;
        return this;
    }

    public TestClassModelBuilder variableList(final List<String> variableList) {
        this.variableList = variableList;
        return this;
    }

    public TestClassModelBuilder mockedVariableList(final List<String> mockedVariableList) {
        this.mockedVariableList = mockedVariableList;
        return this;
    }

    public TestClassModelBuilder methodList(final List<String> methodList) {
        this.methodList = methodList;
        return this;
    }

    public TestClassModel build() {
        return new TestClassModel(
                fileName,
                importList,
                constructorList,
                mockedVariableList,
                variableList,
                methodList
        );
    }

}
