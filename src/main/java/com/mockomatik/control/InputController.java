package com.mockomatik.control;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.mockomatik.enums.Command;

/**
 * Demo use case for Test creation
 */
public class InputController {

    private final TestCreationController testCreationController = new TestCreationController();
    private final Scanner scanner = new Scanner(System.in);
    private final InputHistoryManager historyManager = new InputHistoryManager();
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
            command = scanner.nextLine();
        } while (!inputValidation(command));
        
        packageToTestPath = getPath("INPUT_PATH: ");
        if (packageExists(packageToTestPath)) {
            packageForNewTest = getPath("OUTPUT_PATH: ");
            if (packageExists(packageForNewTest)) {
                testCreationController.runTestCreationProcess(packageToTestPath, packageForNewTest);
            }
        }
    }

    private boolean packageExists(String packageToTestPath) {
        return packageToTestPath != null && !packageToTestPath.isEmpty();
    }

    private String getPath(String pathType) {
        String path = null;
        do {
            System.out.print(CONSOLE_NAME + pathType);
            path = scanner.nextLine();
            if (killCheck(path)) {
                return null;
            }
        } while (!pathValidation(path));
        historyManager.addToHistory(path);
        return path;
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
            exitProgram();
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

    private void exitProgram() {
        System.out.println(" > Good Bye!");
        scanner.close();
        System.exit(0);
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

}
