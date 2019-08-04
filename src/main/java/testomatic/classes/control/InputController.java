package testomatic.classes.control;

import testomatic.classes.model.TestConstructors;
import testomatic.classes.model.TestMethods;
import testomatic.classes.service.CreateTestClasses;
import testomatic.classes.service.ScanClass;
import testomatic.classes.service.ValidateClass;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class InputController {
    private Scanner sc = new Scanner(System.in);

    private CreateTestClasses createTestClasses = new CreateTestClasses();
    private TestConstructors testConstructors = new TestConstructors();
    private TestMethods testMethods = new TestMethods();
    private ScanClass scanClass = new ScanClass();

    private ValidateClass validateClass = new ValidateClass();

    public void runProgram() {
        while (true) {
            userInput();
        }
    }

    public void userInput() {
        String packageToTestPath;
        String packageForNewTest;
        try {
            do {
                System.out.println("\n > Enter A Path To A Package You Want To Test: ");
                packageToTestPath = sc.nextLine();
                if (packageToTestPath.equals("KILL")) {
                    System.out.println(" > You have not done anything yet...");
                }
            } while (!(inputValidation(packageToTestPath)));
            do {
                System.out.println("\n > Enter A Path To A Destination For New Tests: ");
                packageForNewTest = sc.nextLine();
                if (packageForNewTest.equals("KILL")) {
                    packageToTestPath = "";
                    break;
                }
            } while (!(inputValidation(packageForNewTest)));

            if (!packageForNewTest.equals("KILL")) {
                runTestProcess(packageToTestPath, packageForNewTest);
            } else {
                System.out.println(" > Run Canceled...");
            }
        } catch (Exception e) {
            System.out.println("ERROR: InputController > userInput() " + e);
            e.printStackTrace();
        }
    }

    // TODO: regulate scanner input size before validation method?
    public boolean inputValidation(String input) {
        File testDir = new File(input);
        if (input.equals(null) || input.isEmpty() || input.contains(" ")) {
            System.out.println("\n > Input cannot be null or contain spaces!");
            return false;
        } else if (input.contains("EXIT") && input.length() < 5) {
            sc.close();
            System.out.println("\n > Double check project for new testomatic.classes or erroneous files, Good Bye!");
            System.exit(0);
        } else if (!input.contains("\\") || input.length() < 4 || input.length() > 260 || !input.endsWith("\\")) {
            System.out.println(" > Invalid path! Example: C:\\src\\packagewithclasses\\, [ must end with a '\\' ]");
            return false;
        } else if(!testDir.exists()) {
            System.out.println("\n > The entered directory/location does not exist or is unreachable!");
            return false;
        }
        return true;
    }

    public void runTestProcess(String packageToTestPath, String packageForNewTest) {
        try {
            if (scanClass.scanClassForContent(packageToTestPath)) {
                List<List<String>> primaryVariableList = scanClass.getPrimaryVariableList();
                List<List<String>> primaryImportList = scanClass.getPrimaryImportList();
                List<String> fileName = scanClass.getFileNameList();

                // TODO: experimenting with mvc...
                testConstructors.setPrimaryConstructorList(scanClass.getPrimaryConstructorList());
                testMethods.setPrimaryTestMethodList(scanClass.getPrimaryTestMethodList());

                createTestClasses.createTest(
                        testMethods, testConstructors,
                        packageForNewTest, fileName,
                        primaryVariableList, primaryImportList
                );
            }
        } catch (Exception e) {
            System.out.println("ERROR: InputController > runTestProcess()");
            e.printStackTrace();
        } finally {
            validateClass.runTests(packageForNewTest);
            System.out.println(" > Done!");
        }
    }

}
