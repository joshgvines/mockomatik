package testomatic.classes;

import java.io.File;
import java.util.List;

public class OutputData {

    public OutputData() {

    }

    public void outputTextFile(List<String> fileNameList) {
        // TODO: also output to text file
        System.out.println("\n _Files Found:_______________");
        if (!fileNameList.isEmpty() && fileNameList != null) {
            for (String fileName : fileNameList) {
                System.out.println(" > " + fileName);
            }
        }
        System.out.println("\n > Done! \n");
    }

}
