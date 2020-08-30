package com.mockomatik.service.scan.impl;

import java.io.File;
import java.io.IOException;

import com.mockomatik.model.TestClassModel;
import com.mockomatik.service.scan.ScanClass;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ScanClassImpl implements ScanClass {
    private static final Logger log = LogManager.getLogger(ScanClassImpl.class);

    private String fileName;
    private ClassCollection classCollection;
    private ScanClassFilter scanClassFilter;

    @Override
    public ClassCollection scanDirectoryForJavaFiles(String packageToTestPath) {
        try {
            File packageToTestDirectory = new File(packageToTestPath);
            if (!packageToTestIsValid(packageToTestDirectory)) {
                return null;
            }
            runClassFilterForJavaClasses(packageToTestDirectory);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Unable to scan file: {}", fileName);
        }
        if (classCollectionIsValid()) {
            return classCollection;
        }
        log.warn("No valid Java classes found.");
        return null;
    }

    private void runClassFilterForJavaClasses(File packageToTestDirectory) throws IOException {
        classCollection = new ClassCollection(packageToTestDirectory.listFiles().length);
        scanClassFilter = new ScanClassFilter();
        for (File file : packageToTestDirectory.listFiles()) {
            if (file.isFile() && file.getName().contains(".java")) {
                fileName = file.getName();

                TestClassModel testClassModel = scanClassFilter.classScanFilter(file);
                classCollection.push(testClassModel);
            }
        }
    }

    private boolean classCollectionIsValid() {
        return classCollection != null && !classCollection.isEmpty();
    }

    private boolean packageToTestIsValid(File packageToTestDirectory) {
        boolean isValidPackage = true;
        if (packageToTestDirectory == null) {
            log.warn("Package for test was null: {}", packageToTestDirectory.getAbsolutePath());
            isValidPackage = false;
        } else if (!packageToTestDirectory.exists()) {
            log.warn("Package for test did not exists: {}", packageToTestDirectory.getAbsolutePath());
            isValidPackage = false;
        } else if (!packageToTestDirectory.isDirectory()) {
            log.warn("Package is not a directory: {}", packageToTestDirectory.getAbsolutePath());
            isValidPackage = false;
        }
        return isValidPackage;
    }

}