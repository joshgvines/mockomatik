package mockomatik.classes.control;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import mockomatik.classes.enums.Command;
import mockomatik.classes.model.TestConstructors;
import mockomatik.classes.model.TestMembers;
import mockomatik.classes.model.TestMethods;
import mockomatik.classes.service.ValidateClass;
import mockomatik.classes.service.create.CreateTestClass;
import mockomatik.classes.service.scan.ScanClass;

public class InputController {

    private final Scanner sc = new Scanner(System.in);
    private final CreateTestClass createTestClass = new CreateTestClass();
    private final TestConstructors testConstructors = new TestConstructors();
    private final TestMethods testMethods = new TestMethods();
    private final TestMembers testMembers = new TestMembers();
    private final ValidateClass validateClass = new ValidateClass();   
    private InputHistoryManager historyManager = new InputHistoryManager();
    private final String CONSOLE_NAME = "Mockom > ";

    public void runProgram() {
        while (true) {
            userInput();
        }
    }

    public void userInput() {
        String command = null;
        String packageForNewTest = null;
        String packageToTestPath = null;
        do {
            System.out.print(CONSOLE_NAME);
            command = sc.nextLine();
        } while (!inputValidation(command));
        
        packageToTestPath = firstPathForCreate();
        if (packageToTestPath != null && !packageToTestPath.isEmpty()) {
            
            packageForNewTest = secondPathForCreate(packageToTestPath);
            if (packageForNewTest != null && !packageForNewTest.isEmpty()) {
                runTestCreationProcess(packageToTestPath, packageForNewTest);
            }
        }
    }
    
    public String firstPathForCreate() {
        String packageToTestPath;
        do {
            System.out.print(CONSOLE_NAME + "INPUT_PATH: ");
            packageToTestPath = sc.nextLine();
            if (killCheck(packageToTestPath)) {
                return null;
            }
        } while (!pathValidation(packageToTestPath));
        historyManager.addToHistory(packageToTestPath);
        return packageToTestPath;
    }
    
    public String secondPathForCreate(String packageToTestPath) {
        String packageForNewTest;
        do {
            System.out.print(CONSOLE_NAME + "OUTPUT_PATH: ");
            packageForNewTest = sc.nextLine();
            if (killCheck(packageForNewTest)) {
                return null;
            }
        } while (!pathValidation(packageForNewTest));
        historyManager.addToHistory(packageForNewTest);
        return packageForNewTest;
    }
    
    private boolean killCheck(String packageForNewTest) {
        if (packageForNewTest.equals(Command.KILL.getCommand())) {
            historyManager.addToHistory(packageForNewTest);
            System.out.println(" > Session killed...");
            return true;
        }
        return false;
    }

    // TODO: regulate scanner input size before validation method
    private boolean inputValidation(String input) {
        if (input.equals(Command.EXIT.getCommand())) {
            System.out.println(" > Good Bye!");
            sc.close();
            System.exit(0);
        } else if (input.equals(Command.KILL.getCommand())) {
            System.out.println(" > No session in progress to /kill");
        } else if (input.equals(Command.HELP.getCommand())) {
            System.out.println(Command.OPTIONS.getCommand());
        } else if (input.equals(Command.HISTORY.getCommand())) {
            historyManager.displayHistory();
        } else if (input.contains(Command.CREATE.getCommand())) {
            historyManager.addToHistory(input);
            return true;
        } else if (input != null && !input.isEmpty()) {
            System.out.println(" > Please enter a valid command, '/help' for options");
        }
        if (input != null && !input.isEmpty()) {
            historyManager.addToHistory(input);
        }
        return false;
    }
    
    private boolean pathValidation(String input) {
        if (!(input.length() < 3) && !(input.length() > 260) && input.endsWith("\\") && !(input.contains("\\\\"))) {            
            File testDir = new File(input);
            if(testDir.exists()) {
                return true;
            }
            System.out.println(" > Path does not exist or is unreachable!");
        } else {
            System.out.println(" > Invalid path! Example: C:\\src\\packagewithclasses\\, [ must end with a '\\' ]");
        }
        System.out.println(input);
        return false;
    }

    private void runTestCreationProcess(String packageToTestPath, String packageForNewTest) {
        try {
            ScanClass scanClass = new ScanClass();
            if (scanClass.scanClassForContent(packageToTestPath)) {

                List<List<String>> primaryImportList = scanClass.getPrimaryImportList();
                List<String> fileNames = scanClass.getFileNameList();
                
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
        	System.out.print("InputController > runTestCreationProcess() ");
        } finally {
            validateClass.runTests(packageForNewTest);
            System.out.println(" > Done!");
        }
    }
}
