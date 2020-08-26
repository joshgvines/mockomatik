package com.mockomatik.service.scan;

import com.mockomatik.service.scan.impl.ClassCollection;

import java.util.List;

public interface ScanClass {

    /**
     * Checks if the file being read into buffer contains a valid constructor and arguments to be tested
     * then uses '.add(line)' for appropriate arrayList.
     * @param packageToTestPath
     */
    public ClassCollection scanDirectoryForJavaFiles(String packageToTestPath);


}
