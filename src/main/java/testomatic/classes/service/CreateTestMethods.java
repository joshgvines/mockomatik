package testomatic.classes.service;

import java.io.PrintWriter;
import java.util.List;

public class CreateTestMethods {

    /**
     *
     * @param testMethodList
     * @param writer
     */
    public void createMethod(List<String> testMethodList, PrintWriter writer) {
        if (testMethodList.toString().contains("(") && testMethodList.toString().contains(")")) {

            String type,
                    method,
                    methodName,
                    testMethodName;

            boolean isMethod;
            for(int index = 0; index < testMethodList.size(); index++) {
                method = testMethodList.get(index);
                methodName = "";
                testMethodName = "";
                isMethod = true;
                try {
                    // check for getters
                    if (method.contains(" get") && !method.contains(" void ")) {
                        methodName = method.substring(method.indexOf("get"), method.indexOf("("));
                        testMethodName = methodName.replaceAll("get", "Get");
                    }
                    // check for setters
                    else if (method.contains(" set") && method.contains(" void ")) {
                        methodName = method.substring(method.indexOf("set"), method.indexOf("("));
                        testMethodName = methodName.replaceAll("set", "Set");
                    }
                    // check for non-setter void methods
                    else if (method.contains(" public ") && method.contains(" void ")) {
                        methodName = method.substring((method.indexOf(" void ") + 6), method.indexOf("("));
                        testMethodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
                    }
                    // check for non-getter return type methods
                    else if (method.contains(" public ") && !method.contains(" void ")) {
                        type = method.substring((method.indexOf("public ") + 7), method.indexOf("("));
                        type = type.substring(0, type.indexOf(" "));
                        methodName = method.substring((method.indexOf(type) + (type.length() + 1)), method.indexOf("("));
                        testMethodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
//                        .substring(0, 1).toUpperCase() + methodName.substring(1);
                    } else {
                        isMethod = false;
                    }
                } catch (Exception e) {
                    System.out.println("ERROR: CreateTestMethods > createMethod() " + e);
                    e.printStackTrace();
                } finally {
                    // TODO: need a better way to check...
                    if (isMethod) {
                        writer.println("\t@Test\n" +
                                "\tpublic void test" + testMethodName + "() {\n" +
                                "\t\t//cut." + methodName + "( ... )\n" +
                                "\t}\n"
                        );
                    }
                    // Index did not contain a valid method to test
                }
            }
        } else {
            // Class did not contain any valid methods to test
            writer.println("\t@Test\n" +
                    "\tpublic void testMethod() {\n" +
                    "\t\t//blah\n" +
                    "\t}"
            );
        }

    }

}
