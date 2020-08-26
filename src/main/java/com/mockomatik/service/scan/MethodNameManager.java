package com.mockomatik.service.scan;

import com.mockomatik.service.scan.impl.ScanClassFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MethodNameManager {

    private static final Logger log = LogManager.getLogger(MethodNameManager.class);

    private static List<String> methodNamesList;

    private static int numberOfFoundMethods;
    private static int numberOfNonDuplicateMethods;

    public MethodNameManager() {
        methodNamesList = new ArrayList<>();
    }

    public boolean addMethodName(String methodName) {
        for (String tempMethodName : methodNamesList) {
            if (methodName.equals(tempMethodName)) {
                log.info("Found duplicate method name: {}", methodName);
                numberOfFoundMethods++;
                numberOfNonDuplicateMethods++;
                return false;
            }
        }
        numberOfFoundMethods++;
        return methodNamesList.add(methodName);
    }

    public void closeList() {
        methodNamesList = null;
    }

    public int getNumberOfFoundMethods() {
        return numberOfFoundMethods;
    }

    public int getNumberOfNonDuplicateMethods() {
        return numberOfNonDuplicateMethods;
    }

}
