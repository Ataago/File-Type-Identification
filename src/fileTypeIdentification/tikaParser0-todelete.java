package tikaParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TikaParser {

	public static void main(String[] args) {

		try {
			File inputFile = new File("tika.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("mime-type");
			//System.out.println("----------------------------");
			
			// Source array for JSON file
			JSONArray fileDetailsList = new JSONArray();
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);            
				// System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					NodeList nl = eElement.getElementsByTagName("_comment");
					String TikaComment = "";
					if (nl!=null) {
						Node n = nl.item(0);
						if (n!=null)
							TikaComment = n.getTextContent();
					}
					nl = eElement.getElementsByTagName("glob");
					String Extension = "";
					if (nl!=null) {
						Node n = nl.item(0);
						if (n!=null) {
							Extension=((Element) n).getAttribute("pattern");
							String TikaType = eElement.getAttribute("type");
							
							
							JSONObject fileDetails = new JSONObject();
							fileDetails.put("TikaType", TikaType);
							fileDetails.put("TikaComment", TikaComment);
							
							// Removing * from Extensions
							Extension = Extension.replaceAll("\\*", "");
							JSONObject fileObject = new JSONObject();
							fileObject.put(Extension, fileDetails);
							
							fileDetailsList.add(fileObject);
							
							System.out.println(	"Extension=" + Extension + 
									", TikaType=" + TikaType + 
									", TikaComment=" + TikaComment);	// Debug line
						}
					}
				}
			}
			
			//Write JSON file
	        try (FileWriter file = new FileWriter("tikaSource.json")) {
	 
	            file.write(fileDetailsList.toJSONString());
	            file.flush();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

