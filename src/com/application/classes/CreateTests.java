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

    public void createTest(List<String> constructorList, List<String> argumentList, String packageForNewTest, List<String> fileNameList) {
        try {
            int startPosition;
            int endPosition;

            String testDependentObjects;
            for (int i = 0; i < constructorList.size(); i++) {
                testDependentObjects = constructorList.get(i);
                File file = new File(packageForNewTest + fileNameList.get(i) + "Test.java");

                if (testDependentObjects.contains( fileNameList.get(i) + "(")) {

                    startPosition = testDependentObjects.indexOf(fileNameList.get(i) + "(");
                    endPosition = testDependentObjects.indexOf(")");
                    testDependentObjects = testDependentObjects.substring(startPosition, endPosition);

                    PrintWriter pw = new PrintWriter(file);

                    String destinationPackage = packageForNewTest;
                    destinationPackage = destinationPackage.replaceAll("\\\\", ".");

                    startPosition = packageForNewTest.indexOf("src");
                    endPosition = destinationPackage.length();

                    destinationPackage = destinationPackage.toLowerCase();
                    destinationPackage = destinationPackage.substring(
                            (startPosition + 4), (endPosition - 1));


                    pw.println("package " + destinationPackage + "; \n");

                    pw.println("public class " + fileNameList.get(i) + "Test {\n");

                    pw.println(     argumentList.get(i) + "\n");

                    pw.println("    private " + fileNameList.get(i) + " cut;\n");

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

    public void classContent() {
        //TODO:
    }

}
