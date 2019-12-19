package mockomatik.classes.service.scan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ScanClass {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private String fileName;
    private List<String> fileNameList = new ArrayList<>();
    private List<List<String>> primaryTestVariableList = new ArrayList<>();
    private List<List<String>> primaryImportList = new ArrayList<>();
    private List<List<String>> primaryConstructorList = new ArrayList<>();
    private List<List<String>> primaryTestMethodList = new ArrayList<>();
    private List<List<String>> primaryTestMockList = new ArrayList<>();

    /**
     * Checks if the file being read into buffer contains a valid constructor and arguments to be tested
     * then uses '.add(line)' for appropriate arrayList.
     * @param packageToTestPath
     */
    public boolean scanClassForContent(String packageToTestPath) {
        try {
            File dir = new File(packageToTestPath);
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().contains(".java")) {
                    setFileName(file);

                    List<String> constructorList = new ArrayList<>();
                    List<String> testMethodList = new ArrayList<>();
                    List<String> testMockList = new ArrayList<>();
                    List<String> variableList = new ArrayList<>();
                    List<String> importList = new ArrayList<>();

                    String line;
                    boolean defaultConstructor = true;
                    // While loop acts as a content filter to gather reusable lines for test templates
                    try (BufferedReader br = new BufferedReader(new FileReader(file), 16384)) {
                        while ((line = br.readLine()) != null) {
                            // Check for single line comments
                            if (line.contains("//")) {
                                continue;
                                // Check for multiline comments
                            } else if (line.contains("/*")) {
                                ignoreMultiLineComments(br);
                                // Check for imports
                            } else if (line.contains("import ") && line.contains(".") && !line.contains(fileName)) {
                                importList.add(line + "\n");
                                // Check for constructor
                            } else if (line.contains("public " + fileName + "(")) {
                                defaultConstructor = readValidConstructor(line, br, constructorList);
                                // Check for methods
                            } else if (line.contains("public ") && line.contains("(")) {
                                readValidMethod(line, br, testMethodList);
                                // Check for valid primitive and String variables
                            } else if (ObjectTypeManager.compareCommonTypes(line)) {
                                if (!line.contains(fileName) && !line.contains("(") && !line.contains("this.")) {
                                    line = forcePrivate(line);
                                    variableList.add(line + "\n");
                                }
                                // Compare to list of types in types.txt
                            } else if (ObjectTypeManager.compareOtherTypes(line)) {
                                line = forcePrivate(line);
                                line = line.replaceAll("\\s+", " ");
                                testMockList.add("\t@Mock" + line + "\n");
                            } else if (line.contains(";") && !line.contains("{")
                                    && !line.contains("}") && !line.contains("package ")) {
                                line = forcePrivate(line);
                                variableList.add(line + "\n");
                            } else {
                                System.out.println(line);
                            }
                        }
                        // TODO: not sure how this is picking up on constructors with comments?????
                        if (defaultConstructor) {
                            constructorList.add("// Ignored");
                        } if (testMethodList.isEmpty() || testMethodList == null) {
                            testMethodList.add("// Ignored");
                        } if (testMockList.isEmpty() || testMethodList == null) {
                            testMockList.add("// Ignored");
                        }
                        fileNameList.add(fileName);
                        primaryTestMethodList.add(testMethodList);
                        primaryTestVariableList.add(variableList);
                        primaryImportList.add(importList);
                        primaryConstructorList.add(constructorList);
                        primaryTestMockList.add(testMockList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.severe("\n > ERROR: Method: scanClassForContent(), File Name: " + fileName + " " + e);
        }
        if (this.fileNameList != null && !this.fileNameList.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Force fields to private modifier when needed
     * @param line
     * @return
     */
    private String forcePrivate(String line) {
        if (line.contains(" public ")) {
            line = line.replaceAll("public ", "private ");
        } if (line.contains(" protected ")) {
            line = line.replaceAll("protected ", "private ");
        } if (!line.contains(" private ")) {
            line = "private " + line;
            line = line.replaceAll("\\s+", " ");
            line = "\t" + line;
        }
        return line;
    }


    /**
     * Remove file extension and set the name of the file being read to string to fileName
     * @param file
     */
    private void setFileName(File file) {
        fileName = file.getName();
        int fileExtensionPosition = fileName.indexOf(".");
        fileName = fileName.substring(0, fileExtensionPosition);
    }

    /**
     * Set the contents of a found valid constructor to constructorArguments
     * @param line
     * @param br
     */
    private boolean readValidConstructor(String line, BufferedReader br, List<String> constructorList) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(line + "\n");
            while ((line = br.readLine()) != null) {
                if (!line.contains("//") && !line.contains("/*") && !line.contains("*/")) {
                    sb.append(line + "\n");
                    if (line.contains("}")) {
                        constructorList.add(sb.toString());
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.severe(" > ERROR: setConstructorArguments > file name: " + fileName + " " + e);
        }
        return true;
    }

    /**
     * Ignores all line until the end of a multiline comment is found.
     * @param br
     */
    private void ignoreMultiLineComments(BufferedReader br) {
        try {
            String line;
            while((line = br.readLine()) != null) {
                if(line.contains("*/")) {
                    break;
                }
            }
        } catch (Exception e) {
            LOG.severe(" > ERROR: ignoreMultiLineComments() > file name: " + fileName + " " + e);
        }
    }

    /**
     * Reads a method to to potentially be tested
     * @param line
     * @param br
     */
    private void readValidMethod(String line, BufferedReader br, List<String> testMethodList) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(line + "\n");
            while((line = br.readLine()) != null) {
                sb.append(line + "\n");
                if (line.contains("}")) {
                    testMethodList.add(sb.toString());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.severe(" > ERROR: ignoreMultiLineComments() > file name: " + fileName + " " + e);
        }
    }

    public List<String> getFileNameList() {
        return fileNameList;
    }

    public List<List<String>> getPrimaryTestVariableList()  {
        return primaryTestVariableList;
    }

    public List<List<String>> getPrimaryImportList() {
        return primaryImportList;
    }

    public List<List<String>> getPrimaryTestMethodList() {
        return primaryTestMethodList;
    }

    public List<List<String>> getPrimaryConstructorList() {
        return primaryConstructorList;
    }

    public List<List<String>> getPrimaryTestMockList() {
        return primaryTestMockList;
    }

}
