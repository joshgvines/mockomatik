package com.mockomatik.service.create;

import java.io.PrintWriter;
import java.util.List;

import com.mockomatik.enums.ClassComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateTestMethod {
    final static Logger log = LogManager.getLogger(CreateTestMethod.class);

    private String type;
    private String method;
    private String methodName;
    private String testMethodName;

    protected CreateTestMethod() {
    }

    /**
     * Create @Test methods for test classes
     * 
     * @param methodList
     * @param writer
     */
    public boolean createAndOutputMethod(List<String> methodList, PrintWriter writer) {
        if (!methodList.toString().contains("(") || methodList.isEmpty()) {
            createDefaultMethod(writer);
            return false;
        }
        for (int index = 0; index < methodList.size(); index++) {
            try {
                method = methodList.get(index);
                if (method != null) {
                    if (filterMethod()) {
                        outputTestMethod(writer);
                    }
                }
            } catch (Exception e) {
                log.error("Failed to create test method for method(s) named: {}", methodName);
                throw e;
            }
        }
        return true;
    }

    /**
     * Filter method components and info
     * 
     * @return boolean
     */
    private boolean filterMethod() {
        if ( getterFilter() ) {
        } else if ( setterFilter() ) {
        } else if ( voidFilter()   ) {
        } else if ( returnFilter() ) {
        } else {
            log.warn("Method filter did not recognize method type.");
            return false;
        }
        return true;
    }

    private boolean getterFilter() {
        if (method.contains(" get") && !method.contains(" void ")) {
            methodName = method.substring(method.indexOf("get"), method.indexOf("("));
            testMethodName = methodName.replaceAll("get", "Get");
            return true;
        }
        return false;
    }

    private boolean setterFilter() {
        if (method.contains(" set") && method.contains(" void ")) {
            methodName = method.substring(method.indexOf("set"), method.indexOf("("));
            testMethodName = methodName.replaceAll("set", "Set");
            return true;
        }
        return false;
    }

    private boolean voidFilter() {
        if (method.contains(" public ") && method.contains(" void ")) {
            methodName = method.substring((method.indexOf(" void ") + 6), method.indexOf("("));
            testMethodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
            return true;
        }
        return false;
    }

    private boolean returnFilter() {
        if (method.contains(" public ") && !method.contains(" void ")) {
            type = method.substring((method.indexOf("public ") + 7), method.indexOf("("));
            type = type.substring(0, type.indexOf(" "));
            methodName = method.substring((method.indexOf(type) + (type.length() + 1)), method.indexOf("("));
            testMethodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
            return true;
        }
        return false;
    }

    private void outputTestMethod(PrintWriter writer) {
        // Junit 4 Test method template:
        writer.println(ClassComponent.JUNIT_TEST_HEADER.getComponent()
                + "\tpublic void test" + testMethodName + "() {\n"
                + "\t\t//cut." + methodName + "( ... )\n"
                + "\t}\n");
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
