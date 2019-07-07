package com.application.classes;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class InputController {
    private ScanConstructor scanConstructor = new ScanConstructor();
    private CreateTests createTests = new CreateTests();
    private ValidateTests validateTests = new ValidateTests();
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
            System.out.print("\nEnter test package path: ");
            packageToTestPath = sc.nextLine();
        } while (!(inputValidation(packageToTestPath)));
        do {
            System.out.print("\nEnter location for new tests: ");
            packageForNewTest = sc.nextLine();
        } while (!(inputValidation(packageForNewTest)));

        runTestProcess(packageToTestPath, packageForNewTest);
    }
    // TODO regulate scanner input size before validation method?
    public boolean inputValidation(String input) {
        File testDir = new File(input);
        if (input.equals(null) || input.isEmpty() || input.contains(" ")) {
            System.out.println("\n > Input cannot be null or contain spaces!");
            return false;
        } else if (input.contains("EXIT") && input.length() < 5) {
            System.out.println("\n > Double check project for new classes, Good Bye!");
            System.exit(0);
        } else if (!input.contains("\\") || input.length() < 4 || input.length() > 260) {
            System.out.println(" > Invalid path! Example: C:\\src\\packagewithclasses\\, [ must end in a '\\' ]");
            return false;
        } else if(!testDir.exists()) {
            System.out.println("\n > The entered directory/location does not exist or is unreachable!");
            return false;
        }
        return true;
    }

    public void runTestProcess(String packageToTestPath, String packageForNewTest) {
        scanConstructor.checkIfConstructorIsValid(packageToTestPath);
        List<String> returnedValidConstructorList = scanConstructor.getConstructor();
        List<String> returnedValidArgumentList = scanConstructor.getArgumentList();

        if (returnedValidConstructorList != null) {
            List<String> fileName = scanConstructor.getFileName();

            createTests.buildTest(returnedValidConstructorList,
                    returnedValidArgumentList, packageForNewTest, fileName);

            validateTests.runTests(packageForNewTest);
        }
    }

}
