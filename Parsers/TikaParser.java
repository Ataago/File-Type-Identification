// Parser for XML to get a extension, tika-comment and tika-type from tika.xml
// Stores all the values in Json format where key is extension and tika-comment and tika-type as values.
//
// Reference https://github.com/apache/tika/blob/master/tika-core/src/main/resources/org/apache/tika/mime/tika-mimetypes.xml
// 


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
			
			// Selecting all mime-type as the reference tag in which we can get the rest values with extensions
			NodeList nList = doc.getElementsByTagName("mime-type");

			// JSON array to store tika data in json file.
			JSONArray fileDetailsList = new JSONArray();
			
			// Iterate over each mime-type in xml file
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);   
				
				// if mime-type is an element
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					
					// Inside the mime-type get all the glob elements
					NodeList globNL = eElement.getElementsByTagName("glob");
					String extension = "";
					String tikaComment = "";
					String tikaType="";
					
					if (globNL != null) {
						int N = globNL.getLength();
						
						// Iterate over each Glob element to get the extension
						for(int i=0; i<N; i++) {
							Node globN = globNL.item(i);
							extension = ((Element) globN).getAttribute("pattern");
							tikaType = eElement.getAttribute("type");
							
							// Handling _comment element if it has multiple values or null values
							NodeList commentNL = eElement.getElementsByTagName("_comment");
							if (commentNL!=null) {
								Node commentN = commentNL.item(0);
								if (commentN!=null)
									tikaComment = commentN.getTextContent();
							}

							
							// JSON values (fileDetails) to put in the file
							JSONObject fileDetails = new JSONObject();
							fileDetails.put("tikaType", tikaType);
							fileDetails.put("tikaComment", tikaComment);
							
							// removing * from extension
							extension = extension.replaceAll("\\*", "");
							
							// JSON object for the key (extension) with fileDetails as values
							JSONObject fileObject = new JSONObject();
							fileObject.put(extension, fileDetails);
							
							// Adding each row to the json file.
							fileDetailsList.add(fileObject);
						}
					}
				}
			}
			
			// Writing to JSON file all of the key values
	        try (FileWriter file = new FileWriter("tikaSource.json")) {
	            file.write(fileDetailsList.toJSONString());
	            file.flush();
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

