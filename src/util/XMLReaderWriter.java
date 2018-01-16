package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReaderWriter {

	protected static String address = null;
	protected static boolean link;
	protected static boolean publicStats;

	public static boolean readXML(String xml) {
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			dom = db.parse(xml);

			Element doc = dom.getDocumentElement();

			// get address
			address = getTextValue(doc, "address");
			
			// get link
			link = Boolean.parseBoolean(getTextValue(doc, "link"));
			
			// get public-stats
			publicStats = Boolean.parseBoolean(getTextValue(doc, "public-stats"));
			
			return true;

		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		} catch (SAXException se) {
			System.out.println(se.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		return false;
	}

	public static void writeXML(String xml) {
		Document dom;
		Element e = null;
		Element aE = null;
		Element lE = null;
		Element pE = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();

			// create preferences root element
			e = dom.createElement("preferences");
			
			// create address element
			aE = dom.createElement("address");

			// create link element
			lE = dom.createElement("link");
			
			// create public-stats element
			pE = dom.createElement("public-stats");
			
			// add elements to root element
			e.appendChild(aE);
			e.appendChild(lE);
			e.appendChild(pE);
			
			// add preferences data
			aE.appendChild(dom.createTextNode(address));
			lE.appendChild(dom.createTextNode(Boolean.toString(link)));
			pE.appendChild(dom.createTextNode(Boolean.toString(publicStats)));

			dom.appendChild(e);

			try {
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

				tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(xml)));

			} catch (TransformerException te) {
				System.out.println(te.getMessage());
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		} catch (ParserConfigurationException pce) {
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		}
	}

	private static String getTextValue(Element doc, String tag) {
		String value = null;
		NodeList nl;
		nl = doc.getElementsByTagName(tag);
		if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
			value = nl.item(0).getFirstChild().getNodeValue();
		}
		return value;
	}

}
