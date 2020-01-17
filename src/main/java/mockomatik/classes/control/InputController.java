package mockomatik.classes.control;

import mockomatik.classes.model.TestConstructors;
import mockomatik.classes.model.TestMethods;
import mockomatik.classes.model.TestMembers;
import mockomatik.classes.service.create.CreateTestClass;
import mockomatik.classes.service.scan.ObjectTypeManager;
import mockomatik.classes.service.scan.ScanClass;
import mockomatik.classes.service.ValidateClass;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class InputController {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final Scanner sc = new Scanner(System.in);

    private final CreateTestClass createTestClass = new CreateTestClass();
    private final TestConstructors testConstructors = new TestConstructors();
    private final TestMethods testMethods = new TestMethods();
    private final TestMembers testMembers = new TestMembers();

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
            runTestCreationProcess(packageToTestPath, packageForNewTest);
        } else {
            System.out.println(" > Run Canceled...");
        }
    }

    // TODO: regulate scanner input size before validation method
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

    private void runTestCreationProcess(String packageToTestPath, String packageForNewTest) {
        try {
            ScanClass scanClass = new ScanClass();
            if (scanClass.scanClassForContent(packageToTestPath)) {

                List<List<String>> primaryImportList = scanClass.getPrimaryImportList();
                List<String> fileNames = scanClass.getFileNameList();

                //
                testConstructors.setPrimaryConstructorList(scanClass.getPrimaryConstructorList());
                testMethods.setPrimaryTestMethodList(scanClass.getPrimaryTestMethodList());
                testMembers.setPrimaryTestMockList(scanClass.getPrimaryTestMockList());
                testMembers.setPrimaryVariablesList(scanClass.getPrimaryTestVariableList());

                createTestClass.createTest(
                        testMethods, testConstructors, testMembers,
                        packageForNewTest, fileNames, primaryImportList
                );
            }
        } catch (Exception e) {
            LOG.severe("ERROR: InputController > runTestCreationProcess() " + e);
        } finally {
            validateClass.runTests(packageForNewTest);
            System.out.println(" > Done!");
        }
    }

}
