package refdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XmlUtils {
	
	public static Document createDocument(File file) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();		
		Document doc = docBuilder.parse(new InputSource(new FileInputStream(file)));
		return doc;
	}
	
	public static Document createDocument() throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();		
		Document doc = docBuilder.newDocument();
		return doc;
	}
	
	public static Element addElement(Document doc, Node parent, String name) {
		Element child = doc.createElement(name);
		parent.appendChild(child);
		return child;
	}
	
	public static void addAttribute(Document doc, Element element, String name, String value) {
		Attr attr = doc.createAttribute(name);
		attr.setValue(value);
		element.setAttributeNode(attr);		
	}
	
	public static void output(Document doc, OutputStream outputStream) throws Exception {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		tFactory.setAttribute("indent-number", 4);  
		
		Transformer transformer = tFactory.newTransformer();
		Properties outputProperties = new Properties();
		outputProperties.put(OutputKeys.INDENT, "yes");
		outputProperties.put("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperties(outputProperties);		
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new OutputStreamWriter(outputStream, "UTF-8"));
		transformer.transform(source, result); 				
	}
}

