package com.mockomatik.service.scan.filter;

import com.mockomatik.model.TestClassModel;
import com.mockomatik.service.create.CreateUtility;
import com.mockomatik.service.create.TestClassModelBuilder;
import com.mockomatik.service.scan.MethodNameManager;
import com.mockomatik.service.scan.ObjectTypeManager;
import com.mockomatik.service.scan.impl.FileByLineCollection;
import com.mockomatik.service.scan.impl.QuickFormatter;
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

    private CommentFilter  commentFilter = new CommentFilter();
    private MethodNameManager methodNameManager;
    private boolean isInsideClass;

    // TODO: should probably be a string builder
    private String line;

    public TestClassModel classScanFilter(File file) throws IOException {
        isInsideClass = false;
        try (FileByLineCollection fileByLineCollection = QuickFormatter.formatForStringArray(file)) {
            initClassListsForNewFilter();
            formatAndSetFileName(file);

            runFilterByLines(fileByLineCollection);
            // TODO: refactor addIgnoredLines out, its not needed
            addIgnoredLines();
            return buildTestClassesModel();
        } catch (Exception e) {
            log.error("Scanner failed");
            throw e;
        }
    }

    private void runFilterByLines(FileByLineCollection fileByLineCollection) throws IOException {
        while (!fileByLineCollection.isMaxed()) {
            line = fileByLineCollection.dropLne();
            filterLine(fileByLineCollection);
            line = null;
        }
    }

    public void formatAndSetFileName(File file) {
        fileName = file.getName();
        int fileExtensionPosition = fileName.indexOf(".");
        fileName = fileName.substring(0, fileExtensionPosition);
    }

    private void initClassListsForNewFilter() {
        methodNameManager = new MethodNameManager();

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

    private void filterLine(FileByLineCollection fileByLineCollection) throws IOException {
               if ( commentFilter.commentFilter( fileByLineCollection, line )     ) {
        } else if ( loggerFilter()                            ) {
        } else if ( !isInsideClass && importFilter()          ) {
        } else if ( constructorFilter( fileByLineCollection ) ) {
        } else if ( methodFilter( fileByLineCollection )      ) {
        } else if ( commonFilterFromArray( )                      ) {
        } else if ( otherTypesFilter( )                       ) {
        }    else { line = null; }
    }

    // TODO: refactor filter to remove this, its not needed.
    private boolean loggerFilter() {
        return line.contains( "log.info("  )
            || line.contains( "log.error(" )
            || line.contains( "log.warn("  );
    }

    private boolean importFilter() {
        if (line.contains("import ") && line.contains(".") && !line.contains(fileName)) {
            return classImportList.add(line + "\n");
        }
        return false;
    }

    private boolean constructorFilter(FileByLineCollection fileByLineCollection) throws IOException {
        if (line.contains("public " + fileName + "(")) {
            // TODO support mutiline constructor args..
            String argsTestString = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
            argsTestString = argsTestString.trim();

            if (!argsTestString.equals(" ") || !argsTestString.equals("")) {
                String[] constructorArgs = argsTestString.trim().split(",");

                if (!commonFilterFromArray(constructorArgs)) {
                    log.info("commonFilterFromArray failed");
                }
            }
            return readAndAddValidConstructor(fileByLineCollection);
        }
        return false;
    }

    private boolean methodFilter(FileByLineCollection fileByLineCollection) throws IOException {
        if (line.contains("public ") && line.contains("(")) {
            if (methodNameManager.addMethodName(extractMethodName())) {
                return readAndAddValidMethod(fileByLineCollection);
            }
        }
        return false;
    }

    private String extractMethodName() {
        String methodName = line.replace("public", "");
        methodName = methodName.trim();
        int firstSpaceIndex = methodName.indexOf(" ");
        methodName = methodName.substring(firstSpaceIndex, methodName.indexOf("("));

        return methodName;
    }

    private boolean commonFilterFromArray(String[] possibleTypesArray) {
        for (String possibleArg : possibleTypesArray) {
            String tempLine = line;
            line = possibleArg;
            if (!classVariableList.isEmpty()) {
                for (String classVar : classVariableList) {
                    line = CreateUtility.convertToPrivateModifier(line);
                    log.info("classVar : {}, line : {}", classVar, line);
                    if (line.equals(classVar)) {
                        line = line + "_";
                    }
                }
            }
            commonFilterFromArray();
            otherTypesFilter();
            line = tempLine;
        }
        return false;
    }

    private boolean commonFilterFromArray() {
        if (ObjectTypeManager.compareCommonTypes(line)) {
            if (!line.contains(fileName) && isValidType()) {

                line = CreateUtility.convertToPrivateModifier(line);
                if (!line.contains("=")) {
                    if (line.contains("double ")
                            || line.contains("int ")
                            || line.contains("float ")
                            || line.contains("long ")) {
                        line = line + " = 0;";
                    } else {
                        line = line + " = null;";
                    }
                }
                return classVariableList.add(line + "\n");
            }
        }
        return false;
    }

    private boolean otherTypesFilter() {
        if (ObjectTypeManager.compareOtherTypes(line) && isValidType()) {

            line = CreateUtility.convertToPrivateModifier(line);
            line = line.replaceAll("\\s+", " ");
            return classMockedVariableList.add("\t@Mock" + line + "\n");
        }
        return false;
    }

    private boolean isValidType() {
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
    private boolean readAndAddValidConstructor(FileByLineCollection fileByLineCollection) throws IOException {
        try {
            StringBuilder sb = new StringBuilder(line + "\n");
            while (!fileByLineCollection.isMaxed()) {
                line = fileByLineCollection.dropLne();
                if (!line.contains("//") && !commentFilter.commentFilter(fileByLineCollection, line)) {
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

    private boolean readAndAddValidMethod(FileByLineCollection fileByLineCollection) {
        try {
            StringBuilder stringBuilder = new StringBuilder(line + "\n");
            while (!fileByLineCollection.isMaxed()) {
                line = fileByLineCollection.dropLne();
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
