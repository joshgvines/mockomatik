package testomatic.classes.service;

import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

public class CreateTestMethods {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private String type;
    private String method;
    private String methodName;
    private String testMethodName;

    /**
     * Create @Test methods for test classes
     * @param testMethodList
     * @param writer
     */
    public void createMethod(List<String> testMethodList, PrintWriter writer) {
        if (testMethodList.toString().contains("(")) {
            boolean isMethod;
            for(int index = 0; index < testMethodList.size(); index++) {
                method = testMethodList.get(index);
                // TODO: Strings should never be null or throw a null exception
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
                    } else {
                        isMethod = false;
                    }
                } catch (Exception e) {
                    LOG.severe("ERROR: CreateTestMethods > createMethod() " + e);
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
            // Class did not contain any valid methods to test, use a default test method instead.
            writer.println("\t@Test\n" +
                    "\tpublic void testMethod() {\n" +
                    "\t\t//blah\n" +
                    "\t}"
            );
        }
    }

}
