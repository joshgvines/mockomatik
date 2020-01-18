package mockomatik.classes.service;

import java.util.List;

import jgv.java.SHJLogger.Priority;
import jgv.java.SHJLogger.SHJLogger;

// TODO: output to text file
public class OutputData {

    private SHJLogger logger = new SHJLogger(this.getClass().toString());

    public void outputConstructorInfo(int primaryIndex, String fileName, List<String> constructorList) {

        System.out.println("_" + primaryIndex + ")__" + fileName + "______________________________\n"
                + constructorList);

    }

    public void outputFilesFound(List<String> fileNameList) {
        System.out.println("\n __Files Found:______________________________________");
        if (!fileNameList.isEmpty() && fileNameList != null) {
            for (String fileName : fileNameList) {
                System.out.println(" > " + fileName + " : " + "if success here");
                logger.log(Priority.OKAY, "Run output here!");
            }
        }
    }

}
