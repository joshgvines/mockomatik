package mockomatik.classes.service.scan;

import java.util.List;

public interface ScanClass {

    /**
     * Checks if the file being read into buffer contains a valid constructor and arguments to be tested
     * then uses '.add(line)' for appropriate arrayList.
     * @param packageToTestPath
     */
    public boolean scanClassForContent(String packageToTestPath);

    public List<String> getFileNameList();

    public List<List<String>> getPrimaryTestVariableList();

    public List<List<String>> getPrimaryImportList();

    public List<List<String>> getPrimaryTestMethodList();

    public List<List<String>> getPrimaryConstructorList();

    public List<List<String>> getPrimaryTestMockList();

}
