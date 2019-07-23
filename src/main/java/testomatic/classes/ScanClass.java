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
    private List<String> constructorList = new ArrayList<>();
    private List<List<String>> primaryVariableList = new ArrayList<>();
    private List<List<String>> primaryImportList = new ArrayList<>();

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
            String line;

            for (File file : dir.listFiles()) {
                setFileName(file);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                List<String> importList = new ArrayList<>();
                List<String> variableList = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    if (line.contains("import") && !line.contains(fileName)) {
                        importList.add(line + "\n");
                    }
                    if (line.contains("String ") || line.contains("int ") || line.contains("boolean ") ||
                        line.contains("double ") || line.contains("Integer ") || line.contains("Boolean ") ) {
                        if (!line.contains(fileName) && !line.contains(".") && !line.contains("()")) {
                            variableList.add(line + "\n");
                        }
                    }
                    if (line.contains("public " + fileName + "(")) {
                        readValidConstructor(line, br);
                    }
                }
                primaryVariableList.add(variableList);
                primaryImportList.add(importList);
            }
        } catch (IOException e) {
            System.err.println("\n > ERROR: Method: scanClassForContent(), File Name: " + fileName + " " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\n > ERROR: Method: scanClassForContent(), File Name: " + fileName + " " + e);
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
    private void readValidConstructor(String line, BufferedReader br) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(line + "\n");
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
                if (line.contains("}")) {
                    constructorList.add(sb.toString());
                    fileNameList.add(fileName);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println(" > ERROR: setConstructorArguments, file name: " + fileName + " " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(" > ERROR: setConstructorArguments, file name: " + fileName + " " + e);
            e.printStackTrace();
        }
    }

    public List<String> getConstructor() {
        return constructorList;
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
