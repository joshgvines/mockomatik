package com.application.classes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        boolean validateInput;

        do {
            System.out.print("\nEnter test package path: ");
            packageToTestPath = sc.nextLine();
        } while (!(inputValidation(packageToTestPath)));

        do {
            System.out.print("\nEnter location for new tests: ");
            packageForNewTest = sc.nextLine();
        } while (!(inputValidation(packageForNewTest)));

         runTestProcess( packageToTestPath, packageForNewTest );

    }

    // Overkill input string sanitization lol
    public boolean inputValidation(String input) {
        // sentinel exit must be 'EXIT' , must not contain too much space , or have a erroneous length
        if (input.contains("EXIT") && (input.length()) <= 5 && (input.length()) >= 4) {
            System.out.println("\n > Good Bye!");
            System.exit(0);
        // path must not be null
        } else if (input.equals(null) || input.isEmpty() || input.equals(" ")) {
            System.out.println("\n > input cannot be null");
            return false;
        // path must contain '\' , must be greater thant 4 char long , & must be less than 261 char long
        } else if (!(input.contains("\\")) || (input.length() <= 4) || (input.length() >=  261)) {
            System.out.println(" > invalid path!");
            System.out.print(" > You Entered : ");
            System.out.println(input);
            return false;
        } else {
            return true;
        }
        return false;
    }

    public void runTestProcess(String packageToTestPath, String packageForNewTest) {
        scanConstructor.checkIfConstructorIsValid( packageToTestPath );
        List<String> returnedValidConstructorList = scanConstructor.getConstructor();
        List<String> returnedValidArgumentList = scanConstructor.getArgumentList();

        if (returnedValidConstructorList != null) {
            List<String> fileName = scanConstructor.getFileName();

            createTests.createTest( returnedValidConstructorList,
                    returnedValidArgumentList, packageForNewTest, fileName);

            validateTests.runTests( packageForNewTest );
        }
    }

}
