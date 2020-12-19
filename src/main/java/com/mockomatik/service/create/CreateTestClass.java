package com.mockomatik.service.create;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.mockomatik.model.TestClassModel;
import com.mockomatik.service.scan.impl.ClassCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mockomatik.enums.ClassComponent;

public class CreateTestClass {

    final static Logger log = LogManager.getLogger(CreateTestClass.class);

    private final CreateTestMethod createTestMethod = new CreateTestMethod();
    private final CreateTestConstructor createTestConstructor = new CreateTestConstructor();

    private TestClassModel testClassModel;
    private String packageForNewTest;
    private String currentFileName;

    /**
     * Put class content together into one file
     *
     * @param packageForNewTest
     * @param classCollection
     */
    public void createClass(String packageForNewTest, ClassCollection classCollection) throws IOException {
        this.packageForNewTest = packageForNewTest;
        try {
            while(!classCollection.isEmpty()) {
                this.testClassModel = classCollection.pop();
                this.currentFileName = testClassModel.getClassName();
                File file = new File(packageForNewTest + currentFileName + "Test.java");
                writeToTestClassFile(file);
            }
        } catch (Exception e) {
            log.error("Test class creation failed, current file name: {}, " +
                    "error: {}", currentFileName, e);
            throw e;
        } finally {
            // TODO: add successes and failures to a collection for output data
            outputDataFromCreationToUser();
        }
    }

    private void writeToTestClassFile(File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(file)) {
            boolean needsMocks = testClassModel.getMockedVariableList().isEmpty();

            outputPackage(writer);
            outputImports(writer);
            outputClassHeader(writer, needsMocks);

            if (!needsMocks) {
                outputMockedMembers(writer);
            }

            createTestConstructor.createAndOutputConstructor(
                    testClassModel.getConstructorList(), currentFileName, writer);

            createTestMethod.createAndOutputMethod(
                    testClassModel.getMethodList(), writer);

            writer.println("}");
            file.createNewFile();
        } catch(Exception e) {
            log.error("Failed to output content to test class: {}", currentFileName);
            throw e;
        }
    }

    private void outputPackage(PrintWriter writer) {
        String destinationPackage = createPackageStatement();
        writer.println("package " + destinationPackage + "; \n");
    }

    // TODO: Research if StringBuilder is more efficient here
    private void outputImports(PrintWriter writer) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CreateUtility.listToString(testClassModel.getImportList()));
        stringBuilder.append(ClassComponent.REQUIRED_IMPORTS.getComponent());
        writer.print(stringBuilder.toString());
    }

    private void outputClassHeader(PrintWriter writer, boolean needsMocks) {
        if (needsMocks) {
            writer.println(ClassComponent.MOCKITO_RUNNER.getComponent());
        }
        writer.println("public class " + currentFileName + "Test {\n");
        outputMembers(writer);
    }

    // TODO: Attempting to optimize
    private void outputMembers(PrintWriter writer) {
        if (!testClassModel.getVariableList().isEmpty()) {

//            final long startTime = System.currentTimeMillis();

//            List<String> membersList = testClassModel.getVariableList();
//            String variables = CreateUtility.listToString(membersList);
//            writer.print(variables);

            for (String nonMock : testClassModel.getVariableList()) {
                writer.print(nonMock);
            }
            writer.println();
//            final long endTime = System.currentTimeMillis();
//            System.out.println("norm Total execution time: " + (endTime - startTime));
        }
    }

    // TODO: Attempting to optimize
    private void outputMockedMembers(PrintWriter writer) {
        if (!testClassModel.getMockedVariableList().isEmpty()) {

//            final long startTime = System.currentTimeMillis();

//            List<String> mockedMembersList = testClassModel.getMockedVariableList();
//            String mockedVariables = CreateUtility.listToString(mockedMembersList);
//            log.info("Mocked Var In create:::: {}", mockedVariables);
//            writer.print(mockedVariables);

            for (String mock : testClassModel.getMockedVariableList()) {
                writer.print(mock);
            }
//            final long endTime = System.currentTimeMillis();
//            System.out.println("mock Total execution time: " + (endTime - startTime));
        }
    }

    private void outputDataFromCreationToUser() {
//        OutputData outputData = new OutputData();
//        outputData.outputFilesFound(testClassModel);
    }

    /**
     * Creates a package statement based on the location of the file.
     * TODO: Needs to adapt to different project environments, or give the option to
     * @return String destinationPackage
     *          Formatted package to match the path given directory for test class output.
     */
    private String createPackageStatement() {
        String destinationPackage = convertPathToPackageString();
        int packageIndexToEnd = destinationPackage.length() - 1;
        if (destinationPackage.contains("java")) {
            destinationPackage = destinationPackage
                    .substring((destinationPackage.indexOf("java") + 5), packageIndexToEnd);
        } else if (destinationPackage.contains("src")) {
            destinationPackage = destinationPackage
                    .substring((destinationPackage.indexOf("src") + 4), packageIndexToEnd);
        } else if (destinationPackage.contains("com")) {
            destinationPackage = destinationPackage
                    .substring((destinationPackage.indexOf("com") + 4), packageIndexToEnd);
        } else if (destinationPackage.contains("org")) {
            destinationPackage = destinationPackage
                    .substring((destinationPackage.indexOf("org") + 4), packageIndexToEnd);
        } else {
            log.error("Irregular package structure: {}", destinationPackage);
            throw new IllegalArgumentException("Irregular Package structure");
        }
        return destinationPackage;
    }

    private String convertPathToPackageString() {
        String destinationPackage = packageForNewTest.replaceAll("\\\\", ".");
        destinationPackage = destinationPackage.toLowerCase();
        return destinationPackage;
    }

}