import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class TikaParser {

	public static void main(String[] args) {

		try {
			File inputFile = new File("C:\\Users\\imtia\\Desktop\\tika.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("mime-type");
			//System.out.println("----------------------------");

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
							System.out.println(	"Extension=" + Extension + 
												", TikaType=" + eElement.getAttribute("type") + 
												", TikaComment=" + TikaComment);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

