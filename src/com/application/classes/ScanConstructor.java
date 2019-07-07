package com.application.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScanConstructor {
    private String fileName;
    private List<String> fileNameList = new ArrayList<>();
    private List<String> constructorList = new ArrayList<>();
    private List<String> argumentList = new ArrayList<>();

    public ScanConstructor() {

    }

    /**
     * Checks if the file being scanned contains a valid constructor to be tested
     * @param packageToTestPath
     */
    public void checkIfConstructorIsValid(String packageToTestPath) {
        try {
            File dir = new File(packageToTestPath);
            for (File file : dir.listFiles()) {

                setFileName(file);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("int") || line.contains("String") || line.contains("boolean")) {
                        argumentList.add(line);
                    } else if (line.contains("public " + fileName + "(")) {
                        readValidConstructor( line, br);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("\n > ERROR: Method: checkIfConstructorIsValid(), File Name: " + fileName + " " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\n > ERROR: Method: checkIfConstructorIsValid(), File Name: " + fileName + " " + e);
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
}
