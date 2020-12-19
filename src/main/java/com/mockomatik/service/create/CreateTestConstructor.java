package com.mockomatik.service.create;

import java.io.PrintWriter;
import java.util.List;

import com.mockomatik.enums.ClassComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateTestConstructor {

    private static final Logger log = LogManager.getLogger(CreateTestConstructor.class);

    private List<String> constructorList;
    private String fileName;

    public CreateTestConstructor() {}

    /**
     * Create constructors for test classes
     * 
     * @param constructorList
     * @param fileName
     * @param writer
     */
    public boolean createAndOutputConstructor(List<String> constructorList, String fileName, PrintWriter writer) {
        if (!constructorList.toString().contains("(") || constructorList.isEmpty()) {
            createDefaultConstructor(writer, fileName);
            return false;
        }
        try {
            this.constructorList = constructorList;
            this.fileName = fileName;
            if (constructorList.size() > 1) {
                outputMultiConstructor(writer);
            } else  {
                outputSingleConstructor(writer);
            }
        } catch (Exception e) {
            log.error("Failed to create test constructors for file: {}", fileName);
            throw e;
        }
        return true;
    }

    private void outputMultiConstructor(PrintWriter writer) {
        writer.print("\tprivate " + fileName);
        for (int cutIndex = 0; cutIndex < constructorList.size(); cutIndex++) {
            if (cutIndex == (constructorList.size() - 1)) {
                writer.println(" cut" + (cutIndex + 1) + ";\n");
            } else {
                writer.print(" cut" + (cutIndex + 1) + ", ");
            }
        }
        outputMultiConstructorJunitBeforeMethod(writer);
        outputMultiConstructorJunitAfterMethod(writer);
    }

    private void outputMultiConstructorJunitBeforeMethod(PrintWriter writer) {
        writer.print(ClassComponent.JUNIT_BEFORE_HEADER.getComponent());
        for (int setUpIndex = 0; setUpIndex < constructorList.size(); setUpIndex++) {
            String constructorArguments = createConstructorArguments(constructorList.get(setUpIndex), fileName);
            writer.println("\t\tcut" + (setUpIndex + 1) + " = new " + fileName + "(" + constructorArguments
                    + "\n\t\t);");
        }
        writer.println("\t}\n");
    }

    private void outputMultiConstructorJunitAfterMethod(PrintWriter writer) {
        writer.println(ClassComponent.JUNIT_MULTI_AFTER_HEADER.getComponent());
        for (int tearDownIndex = 0; tearDownIndex < constructorList.size(); tearDownIndex++) {
            if (tearDownIndex == (constructorList.size() - 1)) {
                writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;\n\t}\n");
            } else {
                writer.println("\t\tcut" + (tearDownIndex + 1) + " = null;");
            }
        }
    }

    private void outputSingleConstructor(PrintWriter writer) {
        writer.println("\tprivate " + fileName + " cut;\n");
        writer.print(ClassComponent.JUNIT_BEFORE_HEADER.getComponent());

        String constructorArguments = CreateUtility.listToString(constructorList);
        constructorArguments = createConstructorArguments(constructorArguments, fileName);
        writer.println("\t\tcut = new " + fileName + "(" + constructorArguments + ");\n\t}\n");
        writer.println(ClassComponent.JUNIT_SINGLE_AFTER_HEADER.getComponent());
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
            log.error("Failed to create constructor arguments for file: {}", fileName);
            throw e;
        }
        return testObject;
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
        writer.print(ClassComponent.JUNIT_BEFORE_HEADER.getComponent());
        writer.println("\t\tcut = new " + fileName + "();\n" + "\t}\n");
        writer.println(ClassComponent.JUNIT_SINGLE_AFTER_HEADER.getComponent());
    }
}
