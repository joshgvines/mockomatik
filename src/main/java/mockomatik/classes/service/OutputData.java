package mockomatik.classes.service;

import java.util.List;

import io.jgv.logger.impl.SHJLoggerImpl;

// TODO: output to text file
public class OutputData {

    private SHJLoggerImpl logger =SHJLoggerImpl.getLogger();

    public void outputConstructorInfo(int primaryIndex, String fileName, List<String> constructorList) {

        System.out.println("_" + primaryIndex + ")__" + fileName + "______________________________\n"
                + constructorList);

    }

    public void outputFilesFound(List<String> fileNameList) {
        System.out.println("\n __Files Found:______________________________________");
        if (!fileNameList.isEmpty() && fileNameList != null) {
            for (String fileName : fileNameList) {
                System.out.println(" > " + fileName + " : " + "if success here");
                logger.okay("Run output here!");
            }
        }
    }

}
