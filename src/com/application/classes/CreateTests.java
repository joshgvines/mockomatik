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

    public void createTest(List<String> constructorList, List<String> argumentList, String path, List<String> fileNameList) {
        try {

            String testDependentObjects;
            for (int i = 0; i < constructorList.size(); i++) {
                testDependentObjects = constructorList.get(i);
                File file = new File(path + fileNameList.get(i) + "Test.java");

                if (testDependentObjects.contains( fileNameList.get(i) + "(")) {
                    int thisPosition = testDependentObjects.indexOf(fileNameList.get(i) + "(");
                    int endPosition = testDependentObjects.indexOf(")");
                    testDependentObjects = testDependentObjects.substring(thisPosition, endPosition);

                    PrintWriter pw = new PrintWriter(file);

//                    Package thisPackage = new Package();

                    pw.println("package \n");

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

    }

}
