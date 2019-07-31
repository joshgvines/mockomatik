package testomatic.classes.service;

import java.io.File;
import java.util.List;

public class OutputData {

    public OutputData() {

    }

    public void outputConstructorInfo(int primaryIndex, String fileName, List<String> constructorList) {

        System.out.println("_" + primaryIndex + ")__" + fileName + "______________________________\n"
                + constructorList);

    }

    public void outputFilesFound(List<String> fileNameList) {
        // TODO: also output to text file
        boolean successful;
        System.out.println("\n __Files Found:______________________________________");
        if (!fileNameList.isEmpty() && fileNameList != null) {
            for (String fileName : fileNameList) {
                System.out.println(" > " + fileName + " : " + "if success here");
            }
        }
        System.out.println("\n > Done! \n");
    }

}
