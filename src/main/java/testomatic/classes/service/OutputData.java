package testomatic.classes.service;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

// TODO: output to text file
public class OutputData {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void outputConstructorInfo(int primaryIndex, String fileName, List<String> constructorList) {

        System.out.println("_" + primaryIndex + ")__" + fileName + "______________________________\n"
                + constructorList);

    }

    public void outputFilesFound(List<String> fileNameList) {
        System.out.println("\n __Files Found:______________________________________");
        if (!fileNameList.isEmpty() && fileNameList != null) {
            for (String fileName : fileNameList) {
                System.out.println(" > " + fileName + " : " + "if success here");
            }
        }
    }

}
