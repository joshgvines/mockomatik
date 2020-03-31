package mockomatik.classes.service.create;

import java.io.PrintWriter;
import java.util.List;

import mockomatik.classes.enums.ClassComponent;

public class CreateTestMethod {

    private String type;
    private String method;
    private String methodName;
    private String testMethodName;

    protected CreateTestMethod() {
    }

    /**
     * Create @Test methods for test classes
     * 
     * @param testMethodList
     * @param writer
     */
    public void createMethod(List<String> testMethodList, PrintWriter writer) {
        if (testMethodList.toString().contains("(")) {
            boolean isMethod = true;
            for (int index = 0; index < testMethodList.size(); index++) {
                method = testMethodList.get(index);
                try {
                    isMethod = filterMethod();
                } catch (Exception e) {
                    System.out.println("CreateTestMethods > createMethod() " + e);
                } finally {
                    if (isMethod) {
                        writer.println(ClassComponent.JUNIT_TEST_HEADER.getComponent() 
                                + "\tpublic void test" + testMethodName + "() {\n"
                                + "\t\t//cut." + methodName + "( ... )\n"
                                + "\t}\n");
                    }
                }
            }
        } else {
            /* 
             * Class did not contain any valid methods to test, use a default test 
             * method instead. 
             */
            createDefaultMethod(writer);
        }
    }

    /**
     * Gather method components and info
     * 
     * @return boolean
     */
    private boolean filterMethod() {
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
            return false;
        }
        return true;
    }

    /**
     * Writes basic default test method to avoid empty test errors.
     * 
     * @param writer
     */
    private void createDefaultMethod(PrintWriter writer) {
        writer.println(ClassComponent.JUNIT_TEST_HEADER.getComponent() 
                + "\tpublic void testMethod() {\n"
                + "\t\t//blah\n" 
                + "\t}");
    }

}
