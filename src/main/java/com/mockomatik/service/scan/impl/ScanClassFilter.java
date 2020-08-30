package com.mockomatik.service.scan.impl;

import com.mockomatik.model.TestClassModel;
import com.mockomatik.service.create.CreateUtility;
import com.mockomatik.service.create.TestClassModelBuilder;
import com.mockomatik.service.scan.MethodNameManager;
import com.mockomatik.service.scan.ObjectTypeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
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
    private boolean isInsideClass;

    public TestClassModel classScanFilter(File file) throws IOException {
        try (FileByLineCollection fileByLineCollection = QuickFormatter.formatForStringArray(file)) {
            methodNameManager = new MethodNameManager();
            initClassListsForNewFilter();
            formatAndSetFileName(file);
            isInsideClass = false;
            while (!fileByLineCollection.isMaxed()) {
                String line = fileByLineCollection.drop();
                filterLine(fileByLineCollection, line);
            }
            addIgnoredLines();
            TestClassModel testClassModel = buildTestClassesModel();
            return testClassModel;
        } catch (Exception e) {
            log.error("Testing collection close");
            throw e;
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

    private void filterLine(FileByLineCollection fileByLineCollection, String line) throws IOException {
               if ( commentFilter( fileByLineCollection, line )     ) {
        } else if ( loggerFilter( line )                            ) {
        } else if ( !isInsideClass && importFilter( line )          ) {
        } else if ( constructorFilter( fileByLineCollection, line ) ) {
        } else if ( methodFilter( fileByLineCollection, line )      ) {
        } else if ( commonTypesFilter( line )                       ) {
        } else if ( otherTypesFilter( line )                        ) {
        }    else { line = null; }
    }

    private boolean commentFilter(FileByLineCollection fileByLineCollection, String line) throws IOException {
        if (line.contains("/*")) {
            return fileByLineCollection.dropUntilFinds("*/");
        }
        return line.contains(" //");
    }

    // TODO: refactor filter to remove this, its not needed.
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

    private boolean constructorFilter(FileByLineCollection fileByLineCollection, String line) throws IOException {
        if (line.contains("public " + fileName + "(")) {
            return readAndAddValidConstructor(line, fileByLineCollection);
        }
        return false;
    }

    private boolean methodFilter(FileByLineCollection fileByLineCollection, String line) throws IOException {
        if (line.contains("public ") && line.contains("(")) {
            if (methodNameManager.addMethodName(extractMethodName(line))) {
                return readAndAddValidMethod(line, fileByLineCollection);
            }
        }
        return false;
    }

    private String extractMethodName(String line) {
        String methodName = line.replace("public", "");
        methodName = methodName.trim();
        int firstSpaceIndex = methodName.indexOf(" ");
        methodName = methodName.substring(firstSpaceIndex, methodName.indexOf("("));

        return methodName;
    }

    private boolean commonTypesFilter(String line) {
        if (ObjectTypeManager.compareCommonTypes(line)) {
            if (!line.contains(fileName) && isValidType(line)) {

                line = CreateUtility.convertToPrivateModifier(line);
                return classVariableList.add(line + "\n");
            }
        }
        return false;
    }

    private boolean otherTypesFilter(String line) {
        if (ObjectTypeManager.compareOtherTypes(line) && isValidType(line)) {

            line = CreateUtility.convertToPrivateModifier(line);
            line = line.replaceAll("\\s+", " ");
            return classMockedVariableList.add("\t@Mock" + line + "\n");
        }
        return false;
    }

    private boolean isValidType(String line) {
        return (!line.contains("(")
                && !line.contains("{")
                && !line.contains("return ")
                && !line.contains("this."));
    }

    // TODO: not sure how this is picking up on constructors with comments???
    private void addIgnoredLines() {
        if (classConstructorList.isEmpty()) {
            classConstructorList.add("");
        }
        if (classMethodList.isEmpty() || classMethodList == null) {
            classMethodList.add("");
        }
        if (classMockedVariableList.isEmpty() || classMethodList == null) {
            classMockedVariableList.add("");
        }
    }

    // TODO: refactor
    private boolean readAndAddValidConstructor(String line, FileByLineCollection fileByLineCollection) throws IOException {
        try {
            StringBuilder sb = new StringBuilder(line + "\n");
            while (!fileByLineCollection.isMaxed()) {
                line = fileByLineCollection.drop();
                if (!line.contains("//") && !commentFilter(fileByLineCollection, line)) {
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

    private boolean readAndAddValidMethod(String line, FileByLineCollection fileByLineCollection) throws IOException {
        try {
            StringBuilder stringBuilder = new StringBuilder(line + "\n");
            while (!fileByLineCollection.isMaxed()) {
                line = fileByLineCollection.drop();
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

}
