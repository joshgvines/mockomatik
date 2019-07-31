package testomatic.classes.service;

import testomatic.classes.model.TestMethods;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CreateTestClass {
    CreateTestMethod createTestMethod = new CreateTestMethod();
//    TestMethods testMethods = new TestMethods();
//    OutputData outputData = new OutputData();

    /**
     * Outputs content for tests with PrintWriter
     * @param packageForNewTest
     * @param fileNameList
     * @param primaryConstructorList
     * @param primaryVariableList
     * @param primaryImportList
     */
    public void buildTest(TestMethods testMethods,
                          String packageForNewTest,
                          List<String> fileNameList,
                          List<List<String>> primaryConstructorList,
                          List<List<String>> primaryVariableList,
                          List<List<String>> primaryImportList) {
        String fileName;
        String variables;
        String destinationPackage;
        String testObjects;
        String imports;

        try {
            for (int primaryIndex = 0; primaryIndex < fileNameList.size(); primaryIndex++) {
                File file = new File(packageForNewTest + fileNameList.get(primaryIndex) + "Test.java");

                destinationPackage = createPackageStatement(packageForNewTest);
                fileName = fileNameList.get(primaryIndex);

                // Possible Imports
                imports = listToString(primaryImportList.get(primaryIndex));
                // Required Imports
                imports += "import org.junit.After;\n";
                imports += "import org.junit.Before;\n";
                imports += "import org.junit.Test;\n";
                imports += "import org.junit.runner.RunWith;\n";
                imports += "import org.mockito.Mock;\n";
                imports += "import org.mockito.junit.MockitoJUnitRunner;\n\n";

                PrintWriter writer = new PrintWriter(file);

                writer.println("package " + destinationPackage + "; \n");
                writer.print(imports);

                writer.println("@RunWith(MockitoJUnitRunner.class)");
                writer.println("public class " + fileName + "Test {\n");

                if(!primaryVariableList.isEmpty()){
                    variables = listToString(primaryVariableList.get(primaryIndex));
                    writer.print(variables);
                }

                if (!primaryConstructorList.isEmpty() && primaryConstructorList.get(primaryIndex) != null) {

//                    outputData.outputConstructorInfo(primaryIndex, fileName, primaryConstructorList.get(primaryIndex));

                    // Multiple constructors
                    if (primaryConstructorList.get(primaryIndex).size() > 1) {
                        List<String> constructorList = primaryConstructorList.get(primaryIndex);

                        writer.print("\tprivate " + fileName);
                        for (int cutIndex = 0; cutIndex < constructorList.size(); cutIndex++) {
                            if (cutIndex == (constructorList.size() - 1)) {
                                writer.println(" cut" + (cutIndex + 1) + ";\n");
                            } else {
                                writer.print(" cut" + (cutIndex + 1) + ", ");
                            }
                        }
                        writer.print(
                                "\t@Before\n" +
                                "\tpublic void setUp() {\n" );

                        for (int constructorIndex = 0; constructorIndex < constructorList.size(); constructorIndex++) {
                            testObjects = createConstructorArgs(constructorList.get(constructorIndex), fileName);
                            writer.println(
                                    "\t\tcut" + (constructorIndex + 1) + " = new " + fileName + "(" + testObjects + "\n" +
                                            "\t\t);");
                        }
                        writer.println(
                                "\t}\n\n" +
                                "\t@After\n" +
                                "\tpublic void tearDown() {");

                        for (int tearDownIndex = 0; tearDownIndex < constructorList.size(); tearDownIndex++) {
                            if (tearDownIndex == (constructorList.size() - 1)) {
                                writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;\n\t}\n");
                            } else {
                                writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;");
                            }
                        }
                    } else {
                        writer.println("\tprivate " + fileName + " cut;\n");
                        writer.print(
                                "\t@Before\n" +
                                "\tpublic void setUp() {\n" );

                        // Single Constructor
                        testObjects = listToString(primaryConstructorList.get(primaryIndex));
                        if (!testObjects.contains("//") && testObjects.contains("(")) {
                            testObjects = createConstructorArgs(testObjects, fileName);
                            writer.println(
                                    "\t\tcut = new " + fileName + "(" + testObjects + "\n" +
                                            "\t\t);\n" +
                                            "\t}\n");
                        } else {
                            // Default Constructor
                            writer.println(
                                    "\t\tcut = new " + fileName + "();\n" +
                                            "\t}\n");
                        }
                        writer.println(
                                "\t@After\n" +
                                "\tpublic void tearDown() {\n" +
                                "\t\tcut = null;\n" +
                                "\t}\n");
                    }

                    createTestMethod.buildMethod(testMethods.getPrimaryTestMethodList().get(primaryIndex), writer);

                    writer.println(
                            "\t@Test\n" +
                            "\tpublic void testMethod() {\n" +
                            "\t\t//blah\n" +
                            "\t}");
                    writer.println("}");

                    writer.close();
                    file.createNewFile();
                } else {
                    System.out.println(" > ERROR: CreateTestClass > buildTest() > !testObjects.isEmpty() ");
                    System.exit(0);
                }
            }
            OutputData outputData = new OutputData();
            outputData.outputFilesFound(fileNameList);
        } catch (IOException e) {
            System.out.println(" > ERROR: CreateTestClass > buildTest()\n" +  e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(" > ERROR: CreateTestClass > buildTest()\n" + e);
            e.printStackTrace();
        }
    }

    /**
     * Formats arguments to be used in constructor tests
     * @param testObjects
     * @param fileName
     * @return
     */
    private String createConstructorArgs(String testObjects, String fileName) {

        if (testObjects.contains(")")) {
            int startOfObjects = testObjects.indexOf(fileName + "(");
            testObjects = testObjects.substring(startOfObjects, testObjects.indexOf(")"));
        }

        testObjects = testObjects.replaceAll("String ", "");
        testObjects = testObjects.replaceAll("int ", "");
        testObjects = testObjects.replaceAll("Integer ", "");
        testObjects = testObjects.replaceAll("boolean ", "");
        testObjects = testObjects.replaceAll("Boolean ", "");
        testObjects = testObjects.replaceAll("double ", "");
        testObjects = testObjects.replaceAll("Double ", "");
        testObjects = testObjects.replaceAll("float ", "");
        testObjects = testObjects.replaceAll("Float ", "");
        testObjects = testObjects.replaceAll("short ", "");
        testObjects = testObjects.replaceAll("Short ", "");
        testObjects = testObjects.replaceAll("char ", "");
        testObjects = testObjects.replaceAll("Char ", "");
        testObjects = testObjects.replaceAll("byte ", "");
        testObjects = testObjects.replaceAll("Byte ", "");
        testObjects = testObjects.replaceAll(fileName, "");

        if (testObjects.contains("(")) {
            testObjects = testObjects.replaceAll("\\(", "");
            testObjects = testObjects.replaceAll("\\s", "");
        }
        if(testObjects.contains(",")) {
            testObjects = testObjects.replaceAll(",", ",\n\t\t\t\t\t");
        }
        return testObjects;
    }

    /**
     * Creates a package statement based on the location of the file.
     * @param packageForNewTest
     * @return
     */
    private String createPackageStatement(String packageForNewTest) {
        String destinationPackage = packageForNewTest;
        destinationPackage = destinationPackage.replaceAll("\\\\", ".");

        // TODO: Needs to adapt to different project environments, or give the option to add a package type
        destinationPackage = destinationPackage.toLowerCase();
        if (destinationPackage.contains("java")) {
            destinationPackage = destinationPackage.substring(
                    (destinationPackage.indexOf("java") + 5), (destinationPackage.length() - 1));
        } else if (destinationPackage.contains("src")) {
            destinationPackage = destinationPackage.substring(
                    (destinationPackage.indexOf("src") + 4), (destinationPackage.length() - 1));
        } else if (destinationPackage.contains("com")) {
            destinationPackage = destinationPackage.toLowerCase();
            destinationPackage = destinationPackage.substring(
                    (destinationPackage.indexOf("com") + 4), (destinationPackage.length() - 1));
        } else {
            // TODO: Throw correct error here
            System.out.println(" > ERROR: CreateTestClass > createPackageStatement()\n" +
                    " > An invalid or incompatible path was entered as a destination package!");
            System.exit(0);
        }
        return destinationPackage;
    }

    /**
     * Converts a list of Strings to a usable string.
     * @param list
     * @return
     */
    private String listToString(List<String> list) {
        if (!list.isEmpty()) {
            String listToString = list.toString();
            listToString = listToString.replaceAll("\\[", "");
            listToString = listToString.replaceAll("]", "");

            // Differentiate between variables and constructor arguments
            if (listToString.contains("private ")) {
                listToString = listToString.replaceAll(", ", "");
            }
            return listToString + "\n";
        }
        return "";
    }

}
