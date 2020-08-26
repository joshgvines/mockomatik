package com.mockomatik.service.create;

import java.util.List;

public class CreateUtility {

    /**
     * Converts a list of Strings to a usable string.
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        try {
            if (!list.isEmpty()) {
                String listToString = list.toString();
                listToString = listToString.replaceAll("\\[", "");
                listToString = listToString.replaceAll("]", "");
                // Differentiate between variables and constructor arguments
                if (listToString.contains("private ") || listToString.contains("import ")) {
                    listToString = listToString.replaceAll(", ", "");
                }
                return listToString + "\n";
            }
        } catch (Exception e) {
            System.out.println("CreateTestConstructors > listToString()" + e);
        }
        return "";
    }

    /**
     * Force fields to private modifier when needed
     *
     * @param line
     * @return
     */
    public static String convertToPrivateModifier(String line) {
        if (line.contains(" public ")) {
            line = line.replaceAll("public ", "private ");
        }
        if (line.contains(" protected ")) {
            line = line.replaceAll("protected ", "private ");
        }
        if (!line.contains(" private ")) {
            line = "private " + line;
            line = line.replaceAll("\\s+", " ");
            line = "\t" + line;
        }
        return line;
    }

}
