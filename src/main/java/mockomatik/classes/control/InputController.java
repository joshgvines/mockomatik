package mockomatik.classes.control;

import mockomatik.classes.model.TestConstructors;
import mockomatik.classes.model.TestMethods;
import mockomatik.classes.model.TestMockObjects;
import mockomatik.classes.service.create.TestClass;
import mockomatik.classes.service.scan.ObjectTypeManager;
import mockomatik.classes.service.scan.ScanClass;
import mockomatik.classes.service.ValidateClass;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class InputController {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private  final Scanner sc = new Scanner(System.in);

    private final TestClass testClass = new TestClass();
    private final TestConstructors testConstructors = new TestConstructors();
    private final TestMethods testMethods = new TestMethods();
    private final TestMockObjects testMockObjects = new TestMockObjects();
    private final ScanClass scanClass = new ScanClass();

    private final ValidateClass validateClass = new ValidateClass();

    public void runProgram() {
        while (true) {
            userInput();
        }
    }

    public void userInput() {
        String packageToTestPath;
        String packageForNewTest;
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
    }

    // TODO: regulate scanner input size before validation method
    // TODO: eventually will need to return and error code instead
    public boolean inputValidation(String input) {
        File testDir = new File(input);
        if (input.equals(null) || input.isEmpty() || input.contains(" ")) {
            System.out.println("\n > Input cannot be null or contain spaces!");
            return false;
        } else if (input.contains("EXIT") && input.length() < 5) {
            System.out.println("\n > Double check project for new mockomatik.classes or erroneous files, Good Bye!");
            sc.close();
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
                testMockObjects.setPrimaryTestMockList(scanClass.getPrimaryTestMockList());

                testClass.createTest(
                        testMethods, testConstructors, testMockObjects,
                        packageForNewTest, fileName,
                        primaryVariableList, primaryImportList
                );
            }
        } catch (Exception e) {
            LOG.severe("ERROR: InputController > runTestProcess()");
        } finally {
            validateClass.runTests(packageForNewTest);
            System.out.println(" > Done!");
        }
    }

}
