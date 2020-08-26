package com.mockomatik.service.scan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.mockomatik.service.scan.impl.ScanClassImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ObjectTypeManager {

    private static final Logger log = LogManager.getLogger(ScanClassImpl.class);

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
                log.error("Failed to load class types from types.txt");
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
    
    public static void readXml() {
    	try {
    		File xmlFile = new File("src\\main\\resources\\types.xml");
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			
			// Parse types.xml file into document
			Document xmlDocument = documentBuilder.parse(xmlFile);
			System.out.println("Root Element: " + xmlDocument.getDocumentElement().getNodeName());
			
			// Create List of class elements from xml document
			NodeList typeList = xmlDocument.getElementsByTagName("class");
			
			for (int i = 0; i < typeList.getLength(); i++) {
				
				Node type = typeList.item(i);
				
				if (type.getNodeType() == Node.ELEMENT_NODE) {
					Element classElement = (Element) type;
					String className = classElement.getTextContent();
					System.out.println("> " + i + " Class: " + className);
					otherTypes.add(className);
				}
			}
		} catch (Exception e) {
            log.error("Failed to load class object types from types.xml");
		}
    	
    }

}
