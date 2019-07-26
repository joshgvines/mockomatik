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
                    variables = listToString(primaryVariableList.get(primaryIndex));
                    writer.print(variables);
                }

                if (!primaryConstructorList.isEmpty() && primaryConstructorList.get(primaryIndex) != null) {

                    System.out.println(primaryIndex + ") " + fileName + "\n" +
                            primaryConstructorList.get(primaryIndex));

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
                            testObjects = makeConstructorArgs(constructorList.get(constructorIndex), fileName);
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
                            testObjects = makeConstructorArgs(testObjects, fileName);
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
                    writer.println(
                            "\t@Test\n" +
                            "\tpublic void testMethod() {\n" +
                            "\t\t//blah\n" +
                            "\t}");
                    writer.println("}");

                    writer.close();
                    file.createNewFile();
                } else {
                    System.out.println(" > ERROR: CreateClass > buildTest() > !testObjects.isEmpty() ");
                    System.exit(0);
                }
            }
            OutputData outputData = new OutputData();
            outputData.outputTextFile(fileNameList);
        } catch (IOException e) {
            System.out.println(" > ERROR: CreateClass > buildTest()\n" +  e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(" > ERROR: CreateClass > buildTest()\n" + e);
            e.printStackTrace();
        }
    }

    private String makeConstructorArgs(String testObjects, String fileName) {
        int startOfObjects = testObjects.indexOf(fileName + "(");
        testObjects = testObjects.substring(startOfObjects, testObjects.indexOf(")"));

        if (testObjects.contains("String ")) {
            testObjects = testObjects.replaceAll("String ", "");
        }
        if (testObjects.contains("int ")) {
            testObjects = testObjects.replaceAll("int ", "");
        }
        if (testObjects.contains("Integer ")) {
            testObjects = testObjects.replaceAll("Integer ", "");
        }
        if (testObjects.contains("boolean ")) {
            testObjects = testObjects.replaceAll("boolean ", "");
        }
        if (testObjects.contains("Boolean ")) {
            testObjects = testObjects.replaceAll("Boolean ", "");
        }
        if (testObjects.contains("double ")) {
            testObjects = testObjects.replaceAll("double ", "");
        }
        if (testObjects.contains("Double ")) {
            testObjects = testObjects.replaceAll("Double ", "");
        }
        if (testObjects.contains("float ")) {
            testObjects = testObjects.replaceAll("float ", "");
        }
        if (testObjects.contains("Float ")) {
            testObjects = testObjects.replaceAll("Float ", "");
        }
        if (testObjects.contains("short ")) {
            testObjects = testObjects.replaceAll("short ", "");
        }
        if (testObjects.contains("Short ")) {
            testObjects = testObjects.replaceAll("Short ", "");
        }
        if (testObjects.contains("char ")) {
            testObjects = testObjects.replaceAll("char ", "");
        }
        if (testObjects.contains("Char ")) {
            testObjects = testObjects.replaceAll("Char ", "");
        }
        if (testObjects.contains("byte ")) {
            testObjects = testObjects.replaceAll("byte ", "");
        }
        if (testObjects.contains("Byte ")) {
            testObjects = testObjects.replaceAll("Byte ", "");
        }
        if (testObjects.contains(fileName)) {
            testObjects = testObjects.replaceAll(fileName, "");
        }
        if (testObjects.contains("(")) {
            testObjects = testObjects.replaceAll("\\(", "");
            testObjects = testObjects.replaceAll("\\s", "");
        }
        if(testObjects.contains(",")) {
            testObjects = testObjects.replaceAll(",", ",\n\t\t\t");
        }
        return testObjects;
    }

    private String listToString(List<String> list) {
        if (!list.isEmpty()) {
            String listToString = list.toString();

            listToString = listToString.replaceAll("\\[", "");
            listToString = listToString.replaceAll("]", "");

            // Differentiate between variables and constructor arguments
            if (listToString.contains("private ")) {
                listToString = listToString.replaceAll(", ", "");
            }
            listToString.trim();

            return listToString + "\n";
        }
        return "";
    }

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
            System.out.println(" > ERROR: CreateClass > createPackageStatement()\n" +
                    " > An invalid or incompatible path was entered as a destination package!");
            System.exit(0);
        }
        return destinationPackage;
    }

}
