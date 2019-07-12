package testomatic.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanClass {
    private String fileName;
    private List<String> fileNameList = new ArrayList<>();
    private List<String> constructorList = new ArrayList<>();
    private List<String> argumentList = new ArrayList<>();
    private List<List<String>> primaryImportCollection = new ArrayList<>();

    public ScanClass() {

    }

    /**
     * Checks if the file being scanned contains a valid constructor to be tested
     * @param packageToTestPath
     */
    public void scanClassForContent(String packageToTestPath) {
        try {
            File dir = new File(packageToTestPath);
            String line;

            for (File file : dir.listFiles()) {
                setFileName(file);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                List<String> importList = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    if (line.contains("import")) {
                        importList.add(line + "\n");
                    }
                    if (line.contains("int") || line.contains("String") || line.contains("boolean")) {
                        argumentList.add(line);
                    }
                    if (line.contains("public " + fileName + "(")) {
                        readValidConstructor(line, br);
                    }
                }
                primaryImportCollection.add(importList);
            }
        } catch (IOException e) {
            System.err.println("\n > ERROR: Method: scanClassForContent(), File Name: " + fileName + " " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\n > ERROR: Method: scanClassForContent(), File Name: " + fileName + " " + e);
            e.printStackTrace();
        }
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

    public List<String> getArgumentList()  {
        return argumentList;
    }

    public List<String> getFileName() {
        return fileNameList;
    }

    public List<List<String>> getPrimaryImportCollection() {
        return primaryImportCollection;
    }
}
