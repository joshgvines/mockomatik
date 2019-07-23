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
                if (!testObjects.isEmpty()) {
                    fileName = fileNameList.get(index);

                    destinationPackage = createPackageStatement(packageForNewTest);

                    // Possible Imports
                    imports = createImportStatements(primaryImportList.get(index));
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
                        variables = createVariableStatements(primaryVariableList.get(index));
                        if (!variables.contains("//")) {
                            writer.println(variables);
                        }
                    }

                    writer.println("\tprivate " + fileName + " cut;\n");

                    // Create Class Under Test Objects
                    if (!testObjects.isEmpty() && !testObjects.contains("//") && testObjects.contains("(")) {

                        System.out.println("\n" + fileName + " " + testObjects + "\n");

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
        if (testObjects.contains(fileName)) {
            testObjects = testObjects.replaceAll(fileName, "");
        }
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
     * Modify import statements into correct format and location
     * @param importList
     * @return String imports
     */
    private String createImportStatements(List<String> importList) {
        if (!importList.isEmpty()) {
            String imports = importList.toString();
            imports = imports.replaceAll("\\[", "");
            imports = imports.replaceAll("]", "");
            imports = imports.replaceAll(", ", "");
            imports.trim();
            return imports + "\n";
        }
        return "";
    }

    /**
     * Modify variable statements into correct format and location
     * @param variableList
     * @return String variables
     */
    private String createVariableStatements(List<String> variableList) {
        if (!variableList.isEmpty()) {
            String variables = variableList.toString();
            variables = variables.replaceAll("\\[", "");
            variables = variables.replaceAll("]", "");
            variables = variables.replaceAll(", ", "");
            variables.trim();
            return variables + "\n";
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
