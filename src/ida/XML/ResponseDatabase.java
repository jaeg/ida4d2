package ida.XML;

import ida.Logger;
import ida.responses.Response;
import ida.user.User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Our primary functional class, in which all database
 * I/O is performed. Called primarily by Ida class
 *
 */
public class ResponseDatabase {
	private Document doc;
	private NodeList keywordNodes;
	private Node lastResponse;
	private LinkedList<Node> lastSimilarKeywords;
	public int numberOfKeywords;
	public static User user;
	public LinkedList<String> lastKeywordsPulled;
	public LinkedList<Node> lastBestKeywords;
	private double lastWeight;

	/**
	 * Store keyword nodes for searching purposes. Node.getParent() should be
	 * useful in this case.
	 */
	public ResponseDatabase() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse("responses.xml");
		user = new User();

		keywordNodes = doc.getElementsByTagName("Keyword");
	}

	/**
	 * Find keywords similar Group with responses Compare weights for the
	 * response pick best response.
	 */
	public Response getResponse(LinkedList<String> keywordsInput) {

		LinkedList<String> keywords = removeInvalidKeywords(keywordsInput);
		lastKeywordsPulled = keywords;
		// Get the similar keyword nodes
		Logger.log("Keywords from message that are similar to the database: ");
		LinkedList<Node> similarKeywords = getSimilarKeywords(keywords);

		Node bestResponse = getBestResponse(similarKeywords);

		if (bestResponse != null) {
			LinkedList<String> messages = getMessages(bestResponse);

			Random random = new Random();
			String message = messages.get(random.nextInt(messages.size())).replace("&", user.getName())
					.replace("*", "that");

			if (message.contains("[+]")) {
				Logger.log("Positive reinforcement encountered.\n");
				message = message.replace("[+]", "");
				train(lastSimilarKeywords, .1);
			}

			if (message.contains("[-]")) {
				Logger.log("Negative reinforcement encountered.\n");
				message = message.replace("[-]", "");
				train(lastSimilarKeywords, -.1);
			}

			lastResponse = bestResponse;
			lastSimilarKeywords = similarKeywords;
			return new Response(message);
		}

		return new Response("A failure occured in response retrieval.");
	}

	private LinkedList<Node> getSimilarKeywords(LinkedList<String> keywords) {
		LinkedList<Node> similarKeywords = new LinkedList<Node>();
		// ArrayList<String> foundWords = new ArrayList<String>();
		for (int i = 0; i < keywordNodes.getLength(); i++) {
			if (keywords.contains(keywordNodes.item(i).getFirstChild().getNodeValue())) {
				similarKeywords.add(keywordNodes.item(i));
			}
		}

		Logger.log("\n");

		if (similarKeywords.size() == 0) {
			Logger.log("No keywords found\nResorting to fallback message.\n");
			for (int i = 0; i < keywordNodes.getLength(); i++) {
				if (keywordNodes.item(i).getFirstChild().getNodeValue().equals("NOKEYFOUND")) {
					similarKeywords.add(keywordNodes.item(i));
				}
			}
		}
		return similarKeywords;
	}

	@SuppressWarnings("unchecked")
	private Node getBestResponse(LinkedList<Node> similarKeywords) {
		// Get all the health functions for the keywords
		Logger.log("Looking for best response\n");
		double currentHealth = 0.0;
		Node bestResponse = null;
		double best = -1;
		LinkedList<Node> bestKeywords = new LinkedList<Node>();
		LinkedList<Node> currentKeywords = new LinkedList<Node>();
		for (int i = 0; i < similarKeywords.size(); i++) {
			if (i != 0) {
				if (similarKeywords.get(i).getParentNode() != similarKeywords.get(i - 1).getParentNode()) {
					Logger.log("--------\n");
					if (currentHealth >= best) {
						bestKeywords = (LinkedList<Node>) currentKeywords.clone();
						Logger.log("Found a better response.\n");
						bestResponse = similarKeywords.get(i - 1).getParentNode().getParentNode();
						best = currentHealth;
					}
					
					currentKeywords.clear();

					currentHealth = 0;
				}
			}
			currentKeywords.add(similarKeywords.get(i));
			String weight = similarKeywords.get(i).getAttributes().getNamedItem("weight").getNodeValue();
			currentHealth += Double.parseDouble(weight);
			Logger.log("Weight for " + similarKeywords.get(i).getTextContent() + ": " + weight + "\n");
		}

		if (currentHealth >= best) {
			bestKeywords = currentKeywords;
			bestResponse = similarKeywords.get(similarKeywords.size() - 1).getParentNode().getParentNode();
			best = currentHealth;
		}
		
		lastWeight = best;
		numberOfKeywords = bestKeywords.size();
		
		return bestResponse;
	}

	private LinkedList<String> getMessages(Node response) {

		Logger.log("Best response found\n");
		Node messagesNode = response.getChildNodes().item(3);
		int messagesNodeLength = messagesNode.getChildNodes().getLength();
		LinkedList<String> messages = new LinkedList<String>();
		for (int i = 0; i < messagesNodeLength; i++) {
			String content = messagesNode.getChildNodes().item(i).getTextContent();
			if (i % 2 != 0) {
				messages.add(content);
			}
		}
		return messages;
	}

	private void train(LinkedList<Node> keywords, double amount) {
		Logger.log("Training sequence engaged.\n");
		if (lastResponse != null) {
			LinkedList<Node> keywordsList = new LinkedList<Node>();
			Node keywordsNode = lastResponse.getChildNodes().item(1);
			NodeList keywordsNodeChildren = keywordsNode.getChildNodes();
			for (int i = 0; i < keywordsNodeChildren.getLength(); i++) {
				if (i % 2 != 0) {
					keywordsList.add(keywordsNodeChildren.item(i));
				}
			}

			
			for (Node item : keywordsList) {
				if (keywords.contains(item)) {
					NamedNodeMap attribute = item.getAttributes();
					Node weightAttribute = attribute.getNamedItem("weight");
					Double weight = Double.parseDouble(weightAttribute.getNodeValue());
					weight += amount;
					weightAttribute.setNodeValue(weight.toString());
				}
			}

			try {
				saveDOM();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error amending DOM");
			}
		}
	}

	private void saveDOM() throws Exception {
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

	private LinkedList<String> removeInvalidKeywords(LinkedList<String> list) {
		LinkedList<String> newKeywords = new LinkedList<String>();
		LinkedList<String> minorWords = new LinkedList<String>();
		File minorWordsFile = new File("minorWords.txt");
		try {
			Scanner minorWordsReader = new Scanner(minorWordsFile);
			while (minorWordsReader.hasNextLine()) {
				minorWords.add(minorWordsReader.nextLine());
				
			}
			minorWordsReader.close();
		} catch (Exception ex) {
			Logger.log("Unable to obtain minor keywords file.  Operations may be impaired\n");
		}
		Logger.log("Checking for a valid sentence...\n");
		for (String keyword : list) {
			keyword = keyword.toUpperCase();
			keyword = keyword.trim();
			Logger.log("Keyword being tested for validity: " + keyword + "\n");
			if (!minorWords.contains(keyword) && keyword.length()>=2) {
				Logger.log("Added keyword: " + keyword + "\n");
				newKeywords.add(keyword);
			} else {
				Logger.log("Minor word '" + keyword + "' skipped.");
			}
		}

		return newKeywords;
	}

	public LinkedList<Node> getLastSimilarKeywords() {
		return lastSimilarKeywords;
	}
	
	public double getLastWeight()
	{
		return lastWeight;
	}
	
}
