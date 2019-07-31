package testomatic.classes.service;

import java.io.PrintWriter;
import java.util.List;

public class CreateTestMethod {

    public void buildMethod(List<String> testMethodList, PrintWriter writer) {

        for(int index = 0; index < testMethodList.size(); index++) {

            String method = testMethodList.get(index);



            if (method.contains("get") && method.contains("(")){
                String methodName = method.substring(method.indexOf("get"), method.indexOf("("));
                methodName = methodName.replaceAll("get", "Get");
                writer.println(
                        "\t@Test\n" +
                        "\tpublic void test" + methodName + "() {\n" +
                        "\t\t//blah\n" +
                        "\t}\n");
            } else {
                // ignored
            }
        }

    }

}
