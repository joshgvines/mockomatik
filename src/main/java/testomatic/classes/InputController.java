package testomatic.classes;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class InputController {
    private testomatic.classes.ScanClass scanClass = new ScanClass();
    private CreateClass createClass = new CreateClass();
    private ValidateClass validateClass = new ValidateClass();
    private Scanner sc = new Scanner(System.in);

    public void runProgram() {
        while (true) {
            userInput();
        }
    }

    public void userInput() {
        String packageToTestPath;
        String packageForNewTest;
        do {
            System.out.println("\nEnter test package path: ");
            packageToTestPath = sc.nextLine();
        } while (!(inputValidation(packageToTestPath)));
        do {
            System.out.println("\nEnter location for new tests: ");
            packageForNewTest = sc.nextLine();
        } while (!(inputValidation(packageForNewTest)));

        runTestProcess(packageToTestPath, packageForNewTest);
    }
    // TODO: regulate scanner input size before validation method?
    public boolean inputValidation(String input) {
        File testDir = new File(input);
        if (input.equals(null) || input.isEmpty() || input.contains(" ")) {
            System.out.println("\n > Input cannot be null or contain spaces!");
            return false;
        } else if (input.contains("EXIT") && input.length() < 5) {
            sc.close();
            System.out.println("\n > Double check project for new classes or erroneous files, Good Bye!");
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
        if (scanClass.scanClassForContent(packageToTestPath)) {
            List<String> returnedValidConstructorList = scanClass.getConstructor();
            List<List<String>> returnedValidVariableList = scanClass.getPrimaryVariableList();
            List<List<String>> returnedValidImportList = scanClass.getPrimaryImportList();

            if (returnedValidConstructorList != null) {
                List<String> fileName = scanClass.getFileName();
                createClass.buildTest(packageForNewTest, fileName, returnedValidConstructorList,
                        returnedValidVariableList, returnedValidImportList);

                validateClass.runTests(packageForNewTest);
            }
        }
    }

}
