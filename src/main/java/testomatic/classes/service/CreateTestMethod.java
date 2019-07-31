package testomatic.classes.service;

import java.io.PrintWriter;
import java.util.List;

public class CreateTestMethod {

    public void buildMethod(List<String> testMethodList, PrintWriter writer) {

        for(int index = 0; index < testMethodList.size(); index++) {

            String method = testMethodList.get(index);
            String methodName = "";
            if (method.contains("(") && method.contains(")")) {
                if (method.contains("get") && !method.contains(" void ")) {
                    methodName = method.substring(method.indexOf("get"), method.indexOf("("));
                    String testMethodName = methodName.replaceAll("get", "Get");

                    writer.println(
                            "\t@Test\n" +
                            "\tpublic void test" + testMethodName + "() {\n" +
                            "\t\t//Type testResp = cut." + methodName + "()\n" +
                            "\t}\n");
                }
                if (method.contains("set") && method.contains(" void ")) {
                    methodName = method.substring(method.indexOf("set"), method.indexOf("("));
                    String testMethodName = methodName.replaceAll("set", "Set");

                    writer.println(
                            "\t@Test\n" +
                            "\tpublic void test" + testMethodName + "() {\n" +
                            "\t\t//cut." + methodName + "( variableToSet... )\n" +
                            "\t}\n");
                }


            } else {
                // ignored
            }
        }

    }

}
