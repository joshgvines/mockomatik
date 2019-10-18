package mockomatik.classes.service.scan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ObjectTypeManager {

    private final String filePath = "src\\main\\resources\\types.txt";
    private final File typesFile = new File(filePath);

    // Temporary Type list
    private List<String> otherTypes = new ArrayList<>();
    private final String[] commonTypes = new String[]
    {
            " String " , " char "   , " int "     , " Integer " ,
            " double " , " Double " , " float "   , " Float "   ,
            " long "   , " Long "   , " short "   , " Short "   ,
            " byte "   , " Byte "   , " boolean " , " Boolean " ,
    };

    public void loadTypes() {
        if (typesFile.exists()) {
            try(FileReader fr = new FileReader(typesFile);
                BufferedReader br = new BufferedReader(fr);) {
                String line;
                while((line = br.readLine()) != null) {
                    line = line.trim();
                    otherTypes.add(" " + line + " ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean compareOtherTypes(String str) {
        for (String read : otherTypes) {
            if (str.contains(read)) {
                return true;
            }
        }
        return false;
    }

    public boolean compareCommonTypes(String str) {
        for (String read : commonTypes) {
            if (str.contains(read)) {
                return true;
            }
        }
        return false;
    }

}
