package ida.XML;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ResponseDatabase {
	Document doc;
	NodeList keywordNodes;

	/*
	 * Store keyword nodes for searching purposes. Node.getParent() should be
	 * useful in this case.
	 */
	public ResponseDatabase() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse("responses.xml");

		keywordNodes = doc.getElementsByTagName("Keyword");
	}

	// TODO Refactor this function. Maybe break it into four functional parts.
	public String getResponse(LinkedList<String> keywords) {
		/*
		 * Find keywords similar Group with responses Compare weights for the
		 * response pick best response.
		 */
		// Get the similar keyword nodes
		LinkedList<Node> similarKeywords = new LinkedList<Node>();
		for (int i = 0; i < keywordNodes.getLength(); i++) {
			if (keywords.contains(keywordNodes.item(i).getFirstChild().getNodeValue())) {
				similarKeywords.add(keywordNodes.item(i));
			}
		}

		// Get all the health functions for the keywords
		double currentHealth = 0.0;
		Node bestResponse = null;
		double best = -1;

		for (int i = 0; i < similarKeywords.size(); i++) {
			if (i != 0) {
				if (similarKeywords.get(i).getParentNode() != similarKeywords.get(i - 1).getParentNode()) {
					if (currentHealth >= best) {
						bestResponse = similarKeywords.get(i - 1).getParentNode().getParentNode();
						best = currentHealth;
					}
					currentHealth = 0;
				}
			}
			String weight = similarKeywords.get(i).getAttributes().getNamedItem("weight").getNodeValue();
			currentHealth += Double.parseDouble(weight);
		}

		if (currentHealth >= best) {
			bestResponse = similarKeywords.get(similarKeywords.size() - 1).getParentNode().getParentNode();
			best = currentHealth;
		}

		// TODO Get a random response
		if (bestResponse != null) {
			System.out.println(bestResponse.getChildNodes().item(3).getChildNodes().item(3).getTextContent());
			String responses[] = bestResponse.getChildNodes().item(3).getTextContent().split("\n");
			Random generator = new Random();
			int rando = generator.nextInt(3 );
			System.out.println(rando);
			return responses[rando];
		}

		return "Failure";
	}

}
