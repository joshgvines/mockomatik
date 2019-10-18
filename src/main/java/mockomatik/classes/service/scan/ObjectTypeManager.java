package mockomatik.classes.service.scan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ObjectTypeManager {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    // Temporary Type list
    public List<String> otherTypes = new ArrayList<>();
    public final String[] commonTypes = new String[]
    {
            " String " , " char "   , " int "     , " Integer " ,
            " double " , " Double " , " float "   , " Float "   ,
            " long "   , " Long "   , " short "   , " Short "   ,
            " byte "   , " Byte "   , " boolean " , " Boolean " ,
    };

    public ObjectTypeManager(String filePath) {
        File typesFile = new File(filePath);
        if (typesFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(typesFile))) {
                String line;
                while((line = br.readLine()) != null) {
                    line = line.trim();
                    otherTypes.add(" " + line + " ");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOG.severe("ObjectTypeManger has failed" + e);
                System.exit(0);
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
