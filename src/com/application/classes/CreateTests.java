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

    public void buildTest(List<String> constructorList,
                          List<String> argumentList,
                          String packageForNewTest,
                          List<String> fileNameList) {
        String fileName;
        String arguments;
        String destinationPackage;
        String testObjects;
        try {
            for (int index = 0; index < constructorList.size(); index++) {
                testObjects = constructorList.get(index);
                File file = new File(packageForNewTest + fileNameList.get(index) + "Test.java");

                if (testObjects.contains(fileNameList.get(index) + "(")) {
                    fileName = fileNameList.get(index);
                    arguments = argumentList.get(index);

                    System.out.println(arguments);

                    destinationPackage = packageForNewTest;
                    destinationPackage = destinationPackage.replaceAll("\\\\", ".");

                    testObjects = testObjects.substring(
                            testObjects.indexOf(fileName + "("), testObjects.indexOf(")"));

                    destinationPackage = createPackageStatement(destinationPackage);

                    PrintWriter writer = new PrintWriter(file);

                    writer.println("package " + destinationPackage + "; \n");

                    writer.println("public class " + fileName + "Test {\n");
                    writer.println(     arguments + "\n");
                    writer.println("    private " + fileName + " cut;\n");
                    writer.println("    @Mock " + testObjects + "\n");

                    writer.println("    @Before\n" +
                                   "    public void setUp() {\n" +
                                   "        cut = new " + fileName + "();\n" +
                                   "    }\n");

                    writer.println("    @After\n" +
                                   "    public void tearDown() {\n" +
                                   "        cut = null;\n" +
                                   "    }\n");
                    writer.println("}");

                    writer.close();

                    file.createNewFile();

                } else {
                    System.err.println(" > ERROR: createTest");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            System.err.println(" > ERROR: createTest " + e);
            System.exit(0);
        } catch (Exception e){
            System.err.println(" > ERROR: createTest " + e);
            System.exit(0);
        }
    }
    /**
     * Create the package statement for test class under construction
     * @param destinationPackage
     * @return
     */
    private String createPackageStatement(String destinationPackage) {
        destinationPackage = destinationPackage.toLowerCase();
        if (destinationPackage.contains("src")) {
            destinationPackage = destinationPackage.substring(
                    (destinationPackage.indexOf("src") + 4), (destinationPackage.length() - 1));
        } else if (destinationPackage.contains("com")) {
            destinationPackage = destinationPackage.toLowerCase();
            destinationPackage = destinationPackage.substring(
                    (destinationPackage.indexOf("com") + 4), (destinationPackage.length() - 1));
        } else {
            // TODO: throw correct error here
            System.err.println("Invalid path");
            System.exit(0);
        }
        return destinationPackage;
    }
}
