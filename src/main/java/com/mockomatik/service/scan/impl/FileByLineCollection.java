package com.mockomatik.service.scan.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;

public class FileByLineCollection implements Closeable {

    private static final Logger log = LogManager.getLogger(FileByLineCollection.class);

    private String[] fileLines;
    private int bottom = 0;
    private int top = 0;

    public FileByLineCollection(String[] fileLines) {
        if (fileLines != null && fileLines.length > 0) {
            this.fileLines = fileLines;
            this.top = fileLines.length;
        } else {
            log.error("Failed to init file by line array.");
            throw new IllegalStateException(
                    "File by line collection has failed to initialize.");
        }
    }

    public String drop() {
        String temp = fileLines[bottom];
        fileLines[bottom++] = null;
        return temp;
    }

    public boolean dropUntilFinds(String key) {
        String temp = null;
        while (!isMaxed()) {
            temp = drop();
            if (temp.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMaxed() {
        return bottom >= top;
    }

    public int size() {
        return top;
    }

    @Override
    public void close() throws IOException {
        fileLines = null;
        System.gc();
    }
}
