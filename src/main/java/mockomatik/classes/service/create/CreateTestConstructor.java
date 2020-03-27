package mockomatik.classes.service.create;

import java.io.PrintWriter;
import java.util.List;

import mockomatik.classes.service.scan.ObjectTypeManager;

public class CreateTestConstructor {

    protected CreateTestConstructor() {
    }

    /**
     * Create constructors for test classes
     * @param constructorList
     * @param fileName
     * @param writer
     */
    protected void createConstructor(List<String> constructorList, String fileName, PrintWriter writer) {
        String testObjects;
        try {
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
                for (int setUpIndex = 0; setUpIndex < constructorList.size(); setUpIndex++) {
                    testObjects = createConstructorArguments(constructorList.get(setUpIndex), fileName);
                    writer.println(
                        "\t\tcut" + (setUpIndex + 1) + " = new " + fileName + "(" + testObjects + "\n" + "\t\t);"
                    );
                }
                writer.println("\t}\n" + "\t@After\n" +
                        "\tpublic void tearDown() {"
                );
                for (int tearDownIndex = 0; tearDownIndex < constructorList.size(); tearDownIndex++) {
                    if (tearDownIndex == (constructorList.size() - 1)) {
                        writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;\n\t}\n");
                    } else {
                        writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;");
                    }
                }
            }
            // Single Constructor
            // TODO: change redundant check
            else if (constructorList.size() == 1) {
                writer.println("\tprivate " + fileName + " cut;\n");
                writer.print("\t@Before\n" +
                        "\tpublic void setUp() {\n"
                );
                testObjects = listToString(constructorList);
                testObjects = createConstructorArguments(testObjects, fileName);
                writer.println("\t\tcut = new " + fileName + "(" + testObjects + "\n" +
                        "\t\t);\n" +
                        "\t}\n"
                );
                writer.println("\t@After\n" +
                        "\tpublic void tearDown() {\n" +
                        "\t\tcut = null;\n" +
                        "\t}\n"
                );
            } else {
                // Default Constructor
                createDefaultConstructor(writer, fileName);
            }
        } catch (Exception e) {
            System.out.println("CreateTestClasses > createConstructor() " + e );
        }
    }

    /**
     * Formats arguments to be used in constructor tests
     * @param testObjects
     * @param fileName
     * @return String testObjects
     */
    private String createConstructorArguments(String testObjects, String fileName) {
        try {
            if (testObjects.contains(")")) {
                int startOfObjects = testObjects.indexOf(fileName + "(");
                testObjects = testObjects.substring(startOfObjects, testObjects.indexOf(")"));
            }
            for (String type : ObjectTypeManager.commonTypes) {
                type = type.trim() + " ";
                testObjects = testObjects.replaceAll(type, "");
            }
            for (String type : ObjectTypeManager.otherTypes) {
                type = type.trim() + " ";
                testObjects = testObjects.replaceAll(type, "");
            }
            testObjects = testObjects.replaceAll(fileName, "");

            if (testObjects.contains("(")) {
                testObjects = testObjects.replaceAll("\\(", "");
                testObjects = testObjects.replaceAll("\\s", "");
            } if (testObjects.contains(",")) {
                testObjects = testObjects.replaceAll(",", ",\n\t\t\t\t\t");
            }
            return testObjects;
        } catch (Exception e) {
            System.out.println("CreateTestConstructor > createConstructorArguments()" + e);
        }
        return "";
    }

    /**
     * Converts a list of Strings to a usable string.
     * @param list
     * @return
     */
    private String listToString(List<String> list) {
        try {
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
        } catch(Exception e) {
            System.out.println("CreateTestConstructors > listToString()" + e);
        }
        return "";
    }

    /**
     * Writes basic default constructor cut object creation inside @Before method to avoid empty test errors.
     * @param writer
     * @param fileName
     */
    private void createDefaultConstructor(PrintWriter writer, String fileName) {
        writer.println("\tprivate " + fileName + " cut;\n");
        writer.print("\t@Before\n" +
                "\tpublic void setUp() {\n"
        );
        writer.println("\t\tcut = new " + fileName + "();\n" +
                "\t}\n"
        );
        writer.println("\t@After\n" +
                "\tpublic void tearDown() {\n" +
                "\t\tcut = null;\n" +
                "\t}\n"
        );
    }
}
