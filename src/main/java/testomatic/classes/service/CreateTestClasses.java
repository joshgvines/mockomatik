package testomatic.classes.service;

import testomatic.classes.model.TestConstructors;
import testomatic.classes.model.TestMethods;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

public class CreateTestClasses {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    CreateTestMethods createTestMethods = new CreateTestMethods();
    CreateTestConstructors createTestConstructors = new CreateTestConstructors();

    /**
     * Put class content together into one file
     * @param testMethods
     * @param testConstructors
     * @param packageForNewTest
     * @param fileNameList
     * @param primaryVariableList
     * @param primaryImportList
     */
    public void createTest(TestMethods testMethods,
                           TestConstructors testConstructors,
                           String packageForNewTest,
                           List<String> fileNameList,
                           List<List<String>> primaryVariableList,
                           List<List<String>> primaryImportList) {
        String fileName;
        String variables;
        String destinationPackage;
        String imports;
        try {
            for (int primaryIndex = 0; primaryIndex < fileNameList.size(); primaryIndex++) {
                File file = new File(packageForNewTest + fileNameList.get(primaryIndex) + "Test.java");

                destinationPackage = createPackageStatement(packageForNewTest);
                fileName = fileNameList.get(primaryIndex);

                // Possible Imports
                imports = listToString(primaryImportList.get(primaryIndex));
                // Required Imports
                imports += "import org.junit.After;\n";
                imports += "import org.junit.Before;\n";
                imports += "import org.junit.Test;\n";
                imports += "import org.junit.runner.RunWith;\n";
                imports += "import org.mockito.Mock;\n";
                imports += "import org.mockito.junit.MockitoJUnitRunner;\n\n";

                try (PrintWriter writer = new PrintWriter(file)) {
                    writer.println("package " + destinationPackage + "; \n");
                    writer.print(imports);

                    writer.println("@RunWith(MockitoJUnitRunner.class)");
                    writer.println("public class " + fileName + "Test {\n");

                    if(!primaryVariableList.isEmpty()){
                        variables = listToString(primaryVariableList.get(primaryIndex));
                        writer.print(variables);
                    }
                    // Write test constructor(s) to file
                    createTestConstructors.createConstructor(
                            testConstructors.getPrimaryConstructorList().get(primaryIndex), fileName, writer
                    );
                    // write test method(s) to file
                    createTestMethods.createMethod(
                            testMethods.getPrimaryTestMethodList().get(primaryIndex), writer
                    );
                    writer.println("}");
                } finally {
                    file.createNewFile();
                }
            }
            OutputData outputData = new OutputData();
            outputData.outputFilesFound(fileNameList);
        } catch (IOException e) {
            LOG.severe(" > ERROR: CreateTestClasses > createTest()\n" +  e);
        } catch (Exception e) {
            LOG.severe(" > ERROR: CreateTestClasses > createTest()\n" + e);
        }
    }

    /**
     * Creates a package statement based on the location of the file.
     * @param packageForNewTest
     * @return
     */
    private String createPackageStatement(String packageForNewTest) {
        String destinationPackage = packageForNewTest;
        destinationPackage = destinationPackage.replaceAll("\\\\", ".");

        // TODO: Needs to adapt to different project environments, or give the option to add a package type
        destinationPackage = destinationPackage.toLowerCase();
        if (destinationPackage.contains("java")) {
            destinationPackage = destinationPackage.substring(
                    (destinationPackage.indexOf("java") + 5), (destinationPackage.length() - 1));
        } else if (destinationPackage.contains("src")) {
            destinationPackage = destinationPackage.substring(
                    (destinationPackage.indexOf("src") + 4), (destinationPackage.length() - 1));
        } else if (destinationPackage.contains("com")) {
            destinationPackage = destinationPackage.toLowerCase();
            destinationPackage = destinationPackage.substring(
                    (destinationPackage.indexOf("com") + 4), (destinationPackage.length() - 1));
        } else {
            // TODO: Throw correct error here
            System.out.println(" > ERROR: CreateTestClasses > createPackageStatement()\n" +
                    " > An invalid or incompatible path was entered as a destination package!");
            System.exit(0);
        }
        return destinationPackage;
    }

    /**
     * Converts a list of Strings to a usable string.
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
        } catch(Exception e) {
            LOG.severe("CreateTestClasses > listToString()" + e);
        }
        return "";
    }

}
