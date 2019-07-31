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
            String method;
            String methodName;
            for(int index = 0; index < testMethodList.size(); index++) {
                method = testMethodList.get(index);
                if (method.contains("get") && !method.contains(" void ")) {
                    methodName = method.substring(method.indexOf("get"), method.indexOf("("));
                    String testMethodName = methodName.replaceAll("get", "Get");
                    writer.println("\t@Test\n" +
                            "\tpublic void test" + testMethodName + "() {\n" +
                            "\t\t//Type testResp = cut." + methodName + "()\n" + "\t}\n"
                    );
                } else if (method.contains("set") && method.contains(" void ")) {
                    methodName = method.substring(method.indexOf("set"), method.indexOf("("));
                    String testMethodName = methodName.replaceAll("set", "Set");
                    writer.println("\t@Test\n" +
                            "\tpublic void test" + testMethodName + "() {\n" +
                            "\t\t//cut." + methodName + "( variableToSet... )\n" +
                            "\t}\n"
                    );
                }
            }
        } else {
            writer.println("\t@Test\n" +
                    "\tpublic void testMethod() {\n" +
                    "\t\t//blah\n" +
                    "\t}"
            );
        }

    }

}
