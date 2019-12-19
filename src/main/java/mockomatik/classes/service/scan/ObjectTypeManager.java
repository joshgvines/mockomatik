package mockomatik.classes.service.scan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ObjectTypeManager {

    private final static Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private ObjectTypeManager() {
    }

    // Temporary Type list
    public static List<String> otherTypes = new ArrayList<>();
    public static final String[] commonTypes = new String[]
    {
            " String " , " char "   , " int "     , " Integer " ,
            " double " , " Double " , " float "   , " Float "   ,
            " long "   , " Long "   , " short "   , " Short "   ,
            " byte "   , " Byte "   , " boolean " , " Boolean " ,
    };

    public static void load() {
        File typesFile = new File("src\\main\\resources\\types.txt");
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

    public static boolean compareOtherTypes(String str) {
        for (String read : otherTypes) {
            if (str.contains(read)) {
                return true;
            }
        }
        return false;
    }

    public static boolean compareCommonTypes(String str) {
        for (String read : commonTypes) {
            if (str.contains(read)) {
                return true;
            }
        }
        return false;
    }

}
