package ida.Utilities;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Allows the response.xml database to be written to
 *
 */
public class XMLWriter {
	public static void writeResponseToFile(String path, String keywords[], String messages[]) throws Exception {
		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		final Document doc = docBuilder.parse(path);

		final Node root = doc.getFirstChild();

		final Node responseNode = doc.createElement("Response");
		final Node keywordsNode = doc.createElement("Keywords");
		final Node messagesNode = doc.createElement("Messages");
		for (String keyword : keywords) {
			Node keywordNode = doc.createElement("Keyword");
			keywordNode.setTextContent(keyword);
			Attr weight = doc.createAttribute("weight");
			Random generator = new Random();
			double weightValue = 0.4 + (0.6 - 0.4) * generator.nextDouble();
			weight.setValue(Double.toString(weightValue));
			keywordNode.getAttributes().setNamedItem(weight);
			keywordsNode.appendChild(keywordNode);
		}

		for (String message : messages) {
			Node messageNode = doc.createElement("Message");
			messageNode.setTextContent(message);
			messagesNode.appendChild(messageNode);
		}

		responseNode.appendChild(keywordsNode);
		responseNode.appendChild(messagesNode);

		root.appendChild(responseNode);

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);

		String xmlString = result.getWriter().toString();

		File file = new File(path);
		PrintWriter out = new PrintWriter(file);
		out.println(xmlString);
		out.close();
	}

	public static void saveXML(Document doc) throws Exception {

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);

		String xmlString = result.getWriter().toString();

		File file = new File("responses.xml");
		PrintWriter out = new PrintWriter(file);
		out.println(xmlString);
		out.close();

	}

}
