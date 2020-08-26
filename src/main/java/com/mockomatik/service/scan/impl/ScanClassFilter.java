package com.mockomatik.service.scan.impl;

import com.mockomatik.model.TestClassModel;
import com.mockomatik.service.create.CreateUtility;
import com.mockomatik.service.create.TestClassModelBuilder;
import com.mockomatik.service.scan.MethodNameManager;
import com.mockomatik.service.scan.ObjectTypeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScanClassFilter {
    private static final Logger log = LogManager.getLogger(ScanClassFilter.class);

    private String fileName;
    private List<String> classConstructorList;
    private List<String> classMethodList;
    private List<String> classMockedVariableList;
    private List<String> classVariableList;
    private List<String> classImportList;

    private MethodNameManager methodNameManager;

    private static boolean isInsideClass;

    public TestClassModel classScanFilter(File file) throws IOException {
        methodNameManager = new MethodNameManager();
        initClassListsForNewFilter();
        formatAndSetFileName(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file), 16384)) {
            isInsideClass = false;
            String line = null;
            while ((line = br.readLine()) != null) {
                filterLine(br, line);
            }
            addIgnoredLines();
            TestClassModel testClassModel = buildTestClassesModel();
            return testClassModel;
        }
    }

    public void formatAndSetFileName(File file) {
        fileName = file.getName();
        int fileExtensionPosition = fileName.indexOf(".");
        fileName = fileName.substring(0, fileExtensionPosition);
    }

    private void initClassListsForNewFilter() {
        classConstructorList = new ArrayList<>();
        classImportList = new ArrayList<>();
        classMethodList = new ArrayList<>();
        classMockedVariableList = new ArrayList<>();
        classVariableList = new ArrayList<>();
    }

    private TestClassModel buildTestClassesModel() {
        TestClassModel testClassModel = new TestClassModelBuilder()
                .className(fileName)
                .importList(classImportList)
                .constructorList(classConstructorList)
                .variableList(classVariableList)
                .mockedVariableList(classMockedVariableList)
                .methodList(classMethodList)
                .build();
        return testClassModel;
    }

    /**
      * Content filter to gather reusable lines for test templates.
      */
    private void filterLine(BufferedReader br, String line) throws IOException {
               if ( commentFilter( br, line )              ) {
        } else if ( loggerFilter( line )                   ) {
        } else if ( !isInsideClass && importFilter( line ) ) {
        } else if ( constructorFilter( br, line )          ) {
        } else if ( methodFilter( br, line )               ) {
        } else if ( commonTypesFilter( line )              ) {
        } else if ( otherTypesFilter( line )               ) {
//        } else if ( irregularTypesFilter( line )         ) {
        }    else { line = null; }
    }

    private boolean commentFilter(BufferedReader br, String line) throws IOException {
        if (line.contains(" /*")) {
            return ignoreMultiLineComments(br);
        }
        return line.contains(" //");
    }

    private boolean loggerFilter(String line) {
        return line.contains( "log.info("  )
            || line.contains( "log.error(" )
            || line.contains( "log.warn("  );
    }

    private boolean importFilter(String line) {
        if (line.contains("import ") && line.contains(".") && !line.contains(fileName)) {
            return classImportList.add(line + "\n");
        }
        return false;
    }

    private boolean constructorFilter(BufferedReader br, String line) throws IOException {
        if (line.contains("public " + fileName + "(")) {
            return readAndAddValidConstructor(line, br);
        }
        return false;
    }

    private boolean methodFilter(BufferedReader br, String line) throws IOException {
        if (line.contains("public ") && line.contains("(")) {

            if (methodNameManager.addMethodName(extractMethodName(line))) {
                return readAndAddValidMethod(line, br);
            }
        }
        return false;
    }

    private String extractMethodName(String line) {
        String methodName = line.substring(line.indexOf("public ") + 7, line.indexOf("("));
        methodName = methodName.trim();
        methodName = methodName.substring(0, line.indexOf(" "));
        log.info(methodName);
        return methodName;
    }

    private boolean commonTypesFilter(String line) {
        if (ObjectTypeManager.compareCommonTypes(line)) {
            if (!line.contains(fileName)
                && !line.contains("(")
                && !line.contains("return ")
                && !line.contains("{")
                && !line.contains("this.")) {

                line = CreateUtility.convertToPrivateModifier(line);
                return classVariableList.add(line + "\n");
            }
        }
        return false;
    }

    private boolean otherTypesFilter(String line) {
        if (ObjectTypeManager.compareOtherTypes(line)
                && !line.contains("{")
                && !line.contains("return ")
                && !line.contains("this.")) {

            line = CreateUtility.convertToPrivateModifier(line);
            line = line.replaceAll("\\s+", " ");
            return classMockedVariableList.add("\t@Mock" + line + "\n");
        }
        return false;
    }

    private boolean irregularTypesFilter(String line) {
        if (line.contains(";")
                && !line.contains("{")
                && !line.contains("}")
                && !line.contains("package ")) {

            line = CreateUtility.convertToPrivateModifier(line);
            return classVariableList.add(line + "\n");
        }
        return false;
    }

    // TODO: not sure how this is picking up on constructors with comments???
    private void addIgnoredLines() {
        if (classConstructorList.isEmpty()) {
            classConstructorList.add("// Ignored");
        }
        if (classMethodList.isEmpty() || classMethodList == null) {
            classMethodList.add("// Ignored");
        }
        if (classMockedVariableList.isEmpty() || classMethodList == null) {
            classMockedVariableList.add("// Ignored");
        }
    }

    // TODO: refactor
    private boolean readAndAddValidConstructor(String line, BufferedReader bufferedReader) throws IOException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(line + "\n");
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.contains("//") && !commentFilter(bufferedReader, line)) {
                    sb.append(line + "\n");
                    if (line.contains("}")) {
                        isInsideClass = true;
                        return classConstructorList.add(sb.toString());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to read constructor, file name: {} " +
                    "error: {}", fileName, e);
            throw e;
        }
        return false;
    }

    private boolean readAndAddValidMethod(String line, BufferedReader bufferedReader) throws IOException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(line + "\n");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
                if (line.contains("}")) {
                    isInsideClass = true;
                    return classMethodList.add(stringBuilder.toString());
                }
            }
        } catch (Exception e) {
            log.error("Failed to read method(s), file name: {} " +
                    "error: {}", fileName, e);
            throw e;
        }
        return false;
    }

    private boolean ignoreMultiLineComments(BufferedReader bufferedReader) throws IOException {
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("*/")) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Failed to ignore multiLine comment(s), file name: {} " +
                    "error: {}", fileName, e);
            throw e;
        }
        return false;
    }

}
