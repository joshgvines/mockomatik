package testomatic.classes.service;

import java.io.PrintWriter;
import java.util.List;

public class CreateTestConstructors {

    /**
     *
     * @param constructorList
     * @param fileName
     * @param writer
     */
    public void createConstructors(List<String> constructorList, String fileName, PrintWriter writer) {
        String testObjects;
        if (!constructorList.isEmpty() && constructorList != null) {
            // Multiple constructors
            if (constructorList.size() > 1) {
                writer.print("\tprivate " + fileName);
                for (int cutIndex = 0; cutIndex < constructorList.size(); cutIndex++) {
                    if (cutIndex == (constructorList.size() - 1)) {
                        writer.println(" cut" + (cutIndex + 1) + ";\n");
                    } else {
                        writer.print(" cut" + (cutIndex + 1) + ", ");
                    }
                }
                writer.print("\t@Before\n" +
                        "\tpublic void setUp() {\n"
                );
                for (int constructorIndex = 0; constructorIndex < constructorList.size(); constructorIndex++) {
                    testObjects = createConstructorArgs(constructorList.get(constructorIndex), fileName);
                    writer.println(
                            "\t\tcut" + (constructorIndex + 1) + " = new " + fileName + "(" + testObjects + "\n" +
                                    "\t\t);" +
                                    "\t}\n\n");
                }
                writer.println("\t@After\n" +
                        "\tpublic void tearDown() {"
                );
                for (int tearDownIndex = 0; tearDownIndex < constructorList.size(); tearDownIndex++) {
                    if (tearDownIndex == (constructorList.size() - 1)) {
                        writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;\n\t}\n");
                    } else {
                        writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;");
                    }
                }
            } else {
                writer.println("\tprivate " + fileName + " cut;\n");
                writer.print("\t@Before\n" +
                        "\tpublic void setUp() {\n"
                );
                // Single Constructor
                testObjects = listToString(constructorList);
                if (!testObjects.contains("//") && testObjects.contains("(")) {
                    testObjects = createConstructorArgs(testObjects, fileName);
                    writer.println(
                            "\t\tcut = new " + fileName + "(" + testObjects + "\n" +
                                    "\t\t);\n" +
                                    "\t}\n");
                } else {
                    // Default Constructor
                    writer.println("\t\tcut = new " + fileName + "();\n" +
                            "\t}\n"
                    );
                }
                writer.println("\t@After\n" +
                        "\tpublic void tearDown() {\n" +
                        "\t\tcut = null;\n" +
                        "\t}\n"
                );
            }
        } else {
            System.out.println(" > ERROR: CreateTestClasses > createTest() > !testObjects.isEmpty() ");
            System.exit(0);
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
