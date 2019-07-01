package com.application.classes;

import java.util.List;
import java.util.Scanner;

public class InputController {
    private ScanConstructor scanConstructor = new ScanConstructor();
    private CreateTests createTests = new CreateTests();
    private ValidateTests validateTests = new ValidateTests();
    private Scanner sc = new Scanner(System.in);

    public void runProgram() {
        boolean running = true;
        while (running) {
            running = userDecision();
        }
    }

    public boolean userDecision() {
        String packageToTestPath;
        String packageForNewTest;

        System.out.print("\nEnter test package path: ");
        packageToTestPath = sc.nextLine();
        if (packageToTestPath == null) {
            System.out.println("Package to test path cannot be null!");
            return false;
        }
        System.out.print("\nEnter location for new tests: ");
        packageForNewTest = sc.nextLine();
        if (packageForNewTest == null) {
            System.out.println("Location for new tests path cannot be null!");
            return false;
        }
        runTestProcess( packageToTestPath, packageForNewTest );
        return true;
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
