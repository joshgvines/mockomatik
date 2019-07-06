package com.application.classes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

public class CreateTests {

    public CreateTests() {

    }

    public void buildTest(List<String> constructorList, List<String> argumentList, String packageForNewTest, List<String> fileNameList) {
        try {
            String testDependentObjects;
            for (int i = 0; i < constructorList.size(); i++) {
                testDependentObjects = constructorList.get(i);
                File file = new File(packageForNewTest + fileNameList.get(i) + "Test.java");

                if (testDependentObjects.contains( fileNameList.get(i) + "(")) {

                    String fileName = fileNameList.get(i);
                    String arguments = argumentList.get(i);
                    String destinationPackage = packageForNewTest;
                    destinationPackage = destinationPackage.replaceAll("\\\\", ".");

                    int startPosition = testDependentObjects.indexOf(fileNameList.get(i) + "(");
                    int endPosition = testDependentObjects.indexOf(")");
                    testDependentObjects = testDependentObjects.substring(startPosition, endPosition);
                    startPosition = packageForNewTest.indexOf("src");
                    endPosition = destinationPackage.length();

                    destinationPackage = destinationPackage.toLowerCase();
                    destinationPackage = destinationPackage.substring(
                            (startPosition + 4), (endPosition - 1));

                    PrintWriter pw = new PrintWriter(file);

                    pw.println("package " + destinationPackage + "; \n");
                    pw.println("public class " + fileName + "Test {\n");
                    pw.println(     arguments + "\n");
                    pw.println("    private " + fileName + " cut;\n");
                    pw.println("    @Mock " + testDependentObjects + "\n");

                    pw.println("    @Before\n" +
                               "    public void setUp() {\n" +
                               "        cut = new " + fileNameList.get(i) + "();\n" +
                               "    }\n");

                    pw.println("    @After\n" +
                               "    public void tearDown() {\n" +
                               "        cut = null;\n" +
                               "    }\n");
                    pw.println("}");
                    pw.close();

                    file.createNewFile();

                } else {
                    System.err.println(" > ERROR: createTest");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            System.err.println(" > ERROR: createTest " + e);
            System.exit(0);
        }
    }
}
