package testomatic.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScanClass {
    private String fileName;
    private List<String> fileNameList = new ArrayList<>();
    private List<List<String>> primaryVariableList = new ArrayList<>();
    private List<List<String>> primaryImportList = new ArrayList<>();
    private List<List<String>> primaryConstructorList = new ArrayList<>();

    public ScanClass() {

    }

    /**
     * Checks if the file being read into buffer contains a valid constructor and arguments to be tested
     * then uses '.add(line)' for appropriate arrayList.
     * @param packageToTestPath
     */
    public boolean scanClassForContent(String packageToTestPath) {
        try {
            File dir = new File(packageToTestPath);
            // TODO: change bufferedReader size, avoid creating a new bufferedReader every iteration
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().contains(".java")) {
                    setFileName(file);

                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr, 16384);

                    List<String> importList = new ArrayList<>();
                    List<String> variableList = new ArrayList<>();
                    List<String> constructorList = new ArrayList<>();

                    String line;
                    boolean defaultConstructor = false;
                    while ((line = br.readLine()) != null) {
                        if (line.contains("/*")) {
                            ignoreMultiLineComments(br);
                        }
                        if (line.contains("import") && !line.contains(fileName) && !line.contains("//")) {
                            importList.add(line + "\n");
                        }
                        // Check type
                        if (line.contains(" String ") || line.contains(" int ") || line.contains(" Integer ") ||
                                line.contains(" double ")  || line.contains(" Double ") || line.contains(" float ")   ||
                                line.contains(" Float ")   || line.contains(" long ")   || line.contains(" Long ")    ||
                                line.contains(" short ")   || line.contains(" Short ")  || line.contains(" boolean ") ||
                                line.contains(" Boolean ") || line.contains(" char ")   || line.contains(" byte ")    ||
                                line.contains(" Byte ")) {
                            // Check for incompatible characters
                            if (!line.contains(fileName) && !line.contains("(") && !line.contains("this.")) {
                                // Force variables to private modifier
                                if (line.contains("public ")) {
                                    line = line.replaceAll("public ", "private ");
                                }
                                if (line.contains("protected ")) {
                                    line = line.replaceAll("protected ", "private ");
                                }
                                if (!line.contains("private ")) {
                                    line = "private " + line;
                                    line = line.replaceAll("\\s+", " ");
                                    line = "\t" + line;
                                }
                                variableList.add(line + "\n");
                            }
                        }
                        // Check for constructor
                        if (line.contains("public " + fileName + "(") && !line.contains("//") &&
                                !line.contains("/*")  && !line.contains("*/")) {
                            defaultConstructor = readValidConstructor(line, br, constructorList);
                        }
                    }
                    fr.close();
                    br.close();
                    if (!defaultConstructor) {
                        constructorList.add("//");
                    }
                    fileNameList.add(fileName);
                    primaryVariableList.add(variableList);
                    primaryImportList.add(importList);
                    primaryConstructorList.add(constructorList);
                }
            }
        } catch (IOException e) {
            System.out.println("\n > ERROR: Method: scanClassForContent(), File Name: " + fileName + " " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("\n > ERROR: Method: scanClassForContent(), File Name: " + fileName + " " + e);
            e.printStackTrace();
        }
        if (this.fileNameList !=null) {
            return true;
        }
        return false;
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
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(" > ERROR: setConstructorArguments > file name: " + fileName + " " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(" > ERROR: setConstructorArguments > file name: " + fileName + " " + e);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Ignores all line until the end of a multiline comment in  found.
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
        } catch (IOException e) {
            System.err.println(" > ERROR: ignoreMultiLineComments() > file name: " + fileName + " " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(" > ERROR: ignoreMultiLineComments() > file name: " + fileName + " " + e);
            e.printStackTrace();
        }
    }

    public List<List<String>> getConstructor() {
        return primaryConstructorList;
    }

    public List<String> getFileName() {
        return fileNameList;
    }

    public List<List<String>> getPrimaryVariableList()  {
        return primaryVariableList;
    }

    public List<List<String>> getPrimaryImportList() {
        return primaryImportList;
    }
}
