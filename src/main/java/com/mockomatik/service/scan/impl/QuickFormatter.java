package com.mockomatik.service.scan.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Transformer
public class QuickFormatter {
    private static final Logger log = LogManager.getLogger(QuickFormatter.class);
    private static String fileName;

    public static FileByLineCollection formatForStringArray(File file) {
        if (!isValidFile(file)) {
            log.warn("Invalid file provided to transformer.");
            return null;
        }
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            fileName = file.getName();
            StringBuilder newFileContent = buildStringFromFileLines(br);
            return createFileByClassCollection(newFileContent);
        } catch (IOException ex) {
            System.out.println("Failed to read file");
            ex.printStackTrace();
        }
        log.error("Failed to format file: {} for scan process.", fileName);
        throw new RuntimeException("Failed to format file:" + fileName + "for scan process.");
    }

    private static boolean isValidFile(File file) {
        return (file != null && file.canRead());
    }

    private static FileByLineCollection createFileByClassCollection(StringBuilder newFileContent) {
        String[] newFileContentArray = newFileContent
                .toString()
                .split("\n");
        FileByLineCollection fileByLineCollection = new FileByLineCollection(newFileContentArray);
        return fileByLineCollection;
    }

    private static StringBuilder buildStringFromFileLines(BufferedReader br) throws IOException {
        StringBuilder newFileContent = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            line = line.replaceAll("\\n", " ");
            line = line.replaceAll("\\t", "");
            line = line.replaceAll("\\s", " ");
            newFileContent.append(line + "\n");
        }
        return newFileContent;
    }

    private static File formatThisForFile(File file) {
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            fileName = file.getName();
            List<String> newFileContent = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                line = line.replaceAll("\\n", " ");
                line = line.replaceAll("\\t", "");
                line = line.replaceAll("\\s", " ");
                newFileContent.add(line + "\n");
            }
            return writeFormattedDataToNewFile(newFileContent, file);
        } catch (IOException ex) {
            System.out.println("Failed to read file");
            ex.printStackTrace();
        }
        log.error("Failed to format file: {} for scan process.", fileName);
        throw new RuntimeException();
    }

    private static File writeFormattedDataToNewFile(List<String> newFileContent, File file) {
        String loc = file.getAbsolutePath();
        if (file.exists()) {
            System.out.println(loc);
            file = new File(loc);
            try (FileWriter fOut = new FileWriter(file)) {
                for (String newFileLine : newFileContent) {
                    fOut.write(newFileLine);
                }
                return file;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        throw new RuntimeException("Failed to format file for scan.");
    }

}
