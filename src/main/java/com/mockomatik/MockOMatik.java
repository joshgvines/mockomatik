package com.mockomatik;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mockomatik.control.InputController;
import com.mockomatik.service.scan.ObjectTypeManager;

public class MockOMatik {

    final static Logger log = LogManager.getLogger(MockOMatik.class);

    public static void main(String[] args) {

    	ObjectTypeManager.readXml();
        Logo.runLogo();

        try {
            Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
            System.out.println("  > Your Location: " + path);
            System.out.println("  > Type '/exit' To Quit Program.");
            System.out.println("  > Type '/help' For Options.\n");

            final InputController inputController = new InputController();
            inputController.runProgram();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to load application", e);
        } finally {
            System.out.println("\n > Exiting Application \n");
            System.exit(0);
        }
    }

}
