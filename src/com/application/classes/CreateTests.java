package com.application.classes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

public class CreateTests {

    public CreateTests() {

    }

    public void createTest(List<String> returnedConstructorList, String path, List<String> fileNameList) {
        try {

            String test;
            for (int i = 0; i < returnedConstructorList.size(); i++) {
                test = returnedConstructorList.get(i);
                File file = new File(path + fileNameList.get(i) + "Test.java");
                if (test.contains("this.")) {
                    int thisPosition = test.indexOf("this");
                    int endPosition = test.indexOf("}");
                    test = test.substring(thisPosition, endPosition);

                    PrintWriter pw = new PrintWriter(file);

//                    Package thisPackage = new Package();

                    pw.println("package \n");

                    pw.println("public class " + fileNameList.get(i) + "Test {");
                    pw.println("    " + test);
                    pw.println("}");
                    pw.close();

                    file.createNewFile();
                } else {
                    System.err.println(" > ERROR: createTest");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            System.err.println(" > ERROR: createTest " + e);
            System.exit(0);
        }

    }

}
