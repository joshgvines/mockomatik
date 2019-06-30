package com.application.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScanConstructor {
    private String fileName;
    private List<String> fileNameList = new ArrayList<>();
    private List<String> constructorList = new ArrayList<>();

    public ScanConstructor() {

    }

    /**
     * Checks if the file being scanned contains a valid constructor to be tested
     * @param path
     */
    public void checkIfConstructorIsValid(String path) {
        try {
            File dir = new File(path);
            for (File file : dir.listFiles()) {

                setFileName(file);

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("public " + fileName + "(")) {
                        setValidConstructorArguments( line, br );
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(" > ERROR: checkIfConstructorIsValid, file name: " + fileName + " " + e);
            System.exit(0);
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
    private void setValidConstructorArguments(String line, BufferedReader br) {
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
        } catch (Exception e) {
            System.err.println(" > ERROR: setConstructorArguments, file name: " + fileName + " " + e);
        }
    }

    public List<String> getConstructorArguments() {
        return constructorList;
    }

    public List<String> getFileName() {
        return fileNameList;
    }
}
