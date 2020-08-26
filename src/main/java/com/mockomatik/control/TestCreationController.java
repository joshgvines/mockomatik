package com.mockomatik.control;

import com.mockomatik.model.TestClassModel;
import com.mockomatik.service.ValidateClass;
import com.mockomatik.service.create.CreateTestClass;
import com.mockomatik.service.scan.impl.ClassCollection;
import com.mockomatik.service.scan.impl.ScanClassImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class TestCreationController {
    private static final Logger log = LogManager.getLogger(TestCreationController.class);

    private final ScanClassImpl scanClass = new ScanClassImpl();
    private final ValidateClass validateClass = new ValidateClass();
    private final CreateTestClass createTestClass = new CreateTestClass();

    protected void runTestCreationProcess(String packageToTestPath, String packageForNewTest) {
        try {
            ClassCollection classCollection = scanClass.scanDirectoryForJavaFiles(packageToTestPath);
            if (classCollection != null) {
                log.info("Found {} valid java class files.", classCollection.size());
                createTestClass.createClass(packageForNewTest, classCollection);
            }
        } catch (Exception e) {
            log.error("Failed to run test creation process.");
            e.printStackTrace();
        } finally {
            testClassValidation(packageForNewTest);
        }
    }

    private void testClassValidation(String packageForNewTest) {
        validateClass.runTests(packageForNewTest);
        log.info("Test Creation Process Completed completed for package: {}",
                packageForNewTest);
    }

}
