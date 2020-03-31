package mockomatik.classes.service.create;

import java.io.PrintWriter;
import java.util.List;

import mockomatik.classes.enums.ClassComponent;
import mockomatik.classes.service.scan.ObjectTypeManager;

public class CreateTestConstructor {

    protected CreateTestConstructor() {}

    /**
     * Create constructors for test classes
     * 
     * @param constructorList
     * @param fileName
     * @param writer
     */
    public void createConstructor(List<String> constructorList, String fileName, PrintWriter writer) {
        String constructorArguments;
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
                // @Before
                writer.print(ClassComponent.JUNIT_BEFORE_HEADER.getComponent());
                for (int setUpIndex = 0; setUpIndex < constructorList.size(); setUpIndex++) {
                    constructorArguments = createConstructorArguments(constructorList.get(setUpIndex), fileName);
                    writer.println("\t\tcut" + (setUpIndex + 1) + " = new " + fileName + "(" + constructorArguments
                            + "\n\t\t);");
                }
                writer.println("\t}\n");
                // @After
                writer.println(ClassComponent.JUNIT_MULTI_AFTER_HEADER.getComponent());
                for (int tearDownIndex = 0; tearDownIndex < constructorList.size(); tearDownIndex++) {
                    if (tearDownIndex == (constructorList.size() - 1)) {
                        writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;\n\t}\n");
                    } else {
                        writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;");
                    }
                }
            }
            // Single Constructor
            else if (constructorList.size() == 1) {
                writer.println("\tprivate " + fileName + " cut;\n");
                // @Before
                writer.print(ClassComponent.JUNIT_BEFORE_HEADER.getComponent());
                constructorArguments = listToString(constructorList);
                constructorArguments = createConstructorArguments(constructorArguments, fileName);
                writer.println("\t\tcut = new " + fileName + "(" + constructorArguments + ");\n\t}\n");
                // @After
                writer.println(ClassComponent.JUNIT_SINGLE_AFTER_HEADER.getComponent());
            } else {
                // Default Constructor
                createDefaultConstructor(writer, fileName);
            }
        } catch (Exception e) {
            System.out.println("CreateTestClasses > createConstructor() " + e);
        }
    }

    /**
     * Formats arguments to be used in constructor tests
     * 
     * @param testObject
     * @param fileName
     * @return String testObjects
     */
    private String createConstructorArguments(String testObject, String fileName) {
        try {

            System.out.println(fileName);

            // Ensure string is not a default constructor first
            if (testObject.contains(")") && !testObject.contains("()")) {
                /*
                 * Get first index of fileName which should represent the beginning of the
                 * constructor (using parentheses is not as accurate)
                 */
                int startOfArgumentsIndex = testObject.indexOf(fileName.trim());
                int endOfArgumentsIndex = testObject.indexOf("{");
                /*
                 * TODO: Simplify this process: 
                 * Removing all characters here is not as accurate
                 */
                testObject = testObject.substring(startOfArgumentsIndex, endOfArgumentsIndex);
                testObject = testObject.replaceAll("(" + fileName + "|\\(|\n|\t)", "");
                testObject = testObject.replaceAll("\\)", "");
                testObject = testObject.replaceAll("\\s", " ");

                if (testObject.contains(" ")) {
                    testObject = testObject.trim().replaceAll(" +", " ");
                    System.out.println(testObject);
                    testObject = " " + testObject;
                    testObject = testObject.replaceAll("#", "");
                    testObject = testObject.replaceAll(" ", "#");
                    testObject = testObject.replaceAll("#/?[A-Za-z]+#", "");
                }
            } else {
                testObject = "/*NoArgs*/";
            }
            // Final format and new lines
            testObject = testObject.replaceAll("\\s", "");
            if (testObject.contains(",")) {
                testObject = testObject.replaceAll(",", ",\n\t\t\t\t");
                return testObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CreateTestConstructor > createConstructorArguments()" + e);
        }
        return testObject;
    }

    /**
     * Converts a list of Strings to a usable string.
     * 
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
        } catch (Exception e) {
            System.out.println("CreateTestConstructors > listToString()" + e);
        }
        return "";
    }

    /**
     * Writes basic default constructor cut object creation inside @Before method to
     * avoid empty test errors.
     * 
     * @param writer
     * @param fileName
     */
    private void createDefaultConstructor(PrintWriter writer, String fileName) {
        writer.println("\tprivate " + fileName + " cut;\n");
        // @Before
        writer.print(ClassComponent.JUNIT_SINGLE_AFTER_HEADER.getComponent());
        writer.println("\t\tcut = new " + fileName + "();\n" + "\t}\n");
        // @After
        writer.println(ClassComponent.JUNIT_SINGLE_AFTER_HEADER.getComponent());
    }
}
