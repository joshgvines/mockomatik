package testomatic.classes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CreateClass {

    public CreateClass() {

    }

    public void buildTest(String packageForNewTest,
                          List<String> fileNameList,
                          List<String> constructorList,
                          List<List<String>> primaryVariableList,
                          List<List<String>> primaryImportList) {
        String fileName;
        String variables;
        String destinationPackage;
        String testObjects;
        String imports;

        try {
            for (int index = 0; index < constructorList.size(); index++) {
                testObjects = constructorList.get(index);
                File file = new File(packageForNewTest + fileNameList.get(index) + "Test.java");

//                if (testObjects.contains(fileNameList.get(index) + "(")) {
                if (constructorList.size() > 0) {
                    fileName = fileNameList.get(index);

                    destinationPackage = createPackageStatement(packageForNewTest);

                    // Possible Imports
                    imports = listToString(primaryImportList.get(index));
                    // Required Imports
                    imports = imports + "import org.junit.After;\n";
                    imports = imports + "import org.junit.Before;\n";
                    imports = imports + "import org.junit.Test;\n";
                    imports = imports + "import org.junit.runner.RunWith;\n";
                    imports = imports + "import org.mockito.Mock;\n";
                    imports = imports + "import org.mockito.junit.MockitoJUnitRunner;\n\n";

                    PrintWriter writer = new PrintWriter(file);

                    writer.println("package " + destinationPackage + "; \n");

                    writer.print(imports);

                    writer.println("@RunWith(MockitoJUnitRunner.class)");
                    writer.println("public class " + fileName + "Test {\n");

                    if(!primaryVariableList.isEmpty()){
                        variables = listToString(primaryVariableList.get(index));
                        if (!variables.contains("//")) {
                            writer.print(variables);
                        }
                    }

                    writer.println("\tprivate " + fileName + " cut;\n");

                    // Create Class Under Test Objects
                    if (!testObjects.isEmpty() && !testObjects.contains("//") && testObjects.contains("(")) {

                        System.out.println("\n 1) " + fileName + "\n" + testObjects + "\n");

                        testObjects = createConstructorArguments(testObjects, fileName);
                        // TODO: To be, or not to be, mocked?
//                        writer.println("\t@Mock " + testObjects + "\n");
                        writer.println(
                                "\t@Before\n" +
                                "\tpublic void setUp() {\n" +
                                "\t\tcut = new " + fileName + "(" + testObjects + "\n" +
                                "\t\t);\n" +
                                "\t}\n");
                    } else {
                        writer.println(
                                "\t@Before\n" +
                                "\tpublic void setUp() {\n" +
                                "\t\tcut = new " + fileName + "();\n" +
                                "\t}\n");
                    }
                    writer.println(
                            "\t@After\n" +
                            "\tpublic void tearDown() {\n" +
                            "\t\tcut = null;\n" +
                            "\t}\n");
                    writer.println(
                            "\t@Test\n" +
                            "\tpublic void testMethod() {\n" +
                            "\t\t//blah\n" +
                            "\t}");
                    writer.println("}");

                    writer.close();
                    file.createNewFile();
                } else {
                    System.err.println(" > ERROR: CreateClass > buildTest() > !testObjects.isEmpty() ");
                    System.exit(0);
                }
            }
            OutputData outputData = new testomatic.classes.OutputData();
            outputData.outputTextFile(fileNameList);

        } catch (IOException e) {
            System.err.println(" > ERROR: buildTest() " + e);
            e.printStackTrace();
        } catch (Exception e){
            System.err.println(" > ERROR: buildTest() " + e);
            e.printStackTrace();
        }
    }

    /**
     * Modifies variables and objects needed for class under test constructor(s).
     * @param testObjects
     * @param fileName
     * @return String testObjects
     */
    private String createConstructorArguments(String testObjects, String fileName) {
        int startOfObjects = testObjects.indexOf(fileName + "(");
        testObjects = testObjects.substring(startOfObjects, testObjects.indexOf(")"));

        if (testObjects.contains("String")) {
            testObjects = testObjects.replaceAll("String ", "");
        }
        if (testObjects.contains("int")) {
            testObjects = testObjects.replaceAll("int ", "");
        }
        if (testObjects.contains("Integer")) {
            testObjects = testObjects.replaceAll("Integer ", "");
        }
        if (testObjects.contains("boolean")) {
            testObjects = testObjects.replaceAll("boolean ", "");
        }
        if (testObjects.contains("Boolean")) {
            testObjects = testObjects.replaceAll("Boolean ", "");
        }
        if (testObjects.contains("double")) {
            testObjects = testObjects.replaceAll("double ", "");
        }
        if (testObjects.contains("Double")) {
            testObjects = testObjects.replaceAll("Double ", "");
        }
        if (testObjects.contains("float")) {
            testObjects = testObjects.replaceAll("float ", "");
        }
        if (testObjects.contains("Float")) {
            testObjects = testObjects.replaceAll("Float ", "");
        }
        if (testObjects.contains("short")) {
            testObjects = testObjects.replaceAll("short ", "");
        }
        if (testObjects.contains("Short")) {
            testObjects = testObjects.replaceAll("Short ", "");
        }
        if (testObjects.contains("char")) {
            testObjects = testObjects.replaceAll("char ", "");
        }
        if (testObjects.contains("Char")) {
            testObjects = testObjects.replaceAll("Char ", "");
        }
        if (testObjects.contains(fileName)) {
            testObjects = testObjects.replaceAll(fileName, "");
        }
        if (testObjects.contains("(")) {
            testObjects = testObjects.replaceAll("\\(", "");
            testObjects = testObjects.replaceAll("\\s", "");
        }
        if(testObjects.contains(",")) {
            testObjects = testObjects.replaceAll(",", ",\r\t\t\t");
        }
        return testObjects;
    }

    /**
     * Modify import statements into correct format and location
     * @param list
     * @return String listToString
     */
    private String listToString(List<String> list) {
        if (!list.isEmpty()) {
            String listToString = list.toString();
            listToString = listToString.replaceAll("\\[", "");
            listToString = listToString.replaceAll("]", "");
            listToString = listToString.replaceAll(", ", "");
            listToString.trim();
            return listToString + "\n";
        }
        return "";
    }
    /**
     * Create the package statement for test class under construction from path
     * @param packageForNewTest
     * @return String destinationPackage
     */
    private String createPackageStatement(String packageForNewTest) {
        String destinationPackage = packageForNewTest;
        destinationPackage = destinationPackage.replaceAll("\\\\", ".");

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
            // TODO: throw correct error here
            System.err.println("createPackageStatement: Invalid path");
            System.exit(0);
        }
        return destinationPackage;
    }

}
