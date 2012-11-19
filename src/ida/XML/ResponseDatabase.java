package ida.XML;

import ida.responses.Response;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Random;

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

public class ResponseDatabase
{
	Document doc;
	NodeList keywordNodes;
	Node lastResponse;
	LinkedList<Node> lastSimilarKeywords;

	/*
	 * Store keyword nodes for searching purposes. Node.getParent() should be
	 * useful in this case.
	 */
	public ResponseDatabase() throws ParserConfigurationException,
			SAXException, IOException
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse("responses.xml");

		keywordNodes = doc.getElementsByTagName("Keyword");
	}

	/**
	 * Find keywords similar Group with responses Compare weights for the
	 * response pick best response.
	 */
	public Response getResponse(LinkedList<String> keywords)
	{

		// Get the similar keyword nodes
		LinkedList<Node> similarKeywords = new LinkedList<Node>();
		for (int i = 0; i < keywordNodes.getLength(); i++)
		{
			if (keywords.contains(keywordNodes.item(i).getFirstChild()
					.getNodeValue()))
			{
				similarKeywords.add(keywordNodes.item(i));
			}
		}

		if (similarKeywords.size() == 0)
		{
			for (int i = 0; i < keywordNodes.getLength(); i++)
			{
				if (keywordNodes.item(i).getFirstChild().getNodeValue()
						.equals("NOKEYFOUND"))
				{
					similarKeywords.add(keywordNodes.item(keywordNodes
							.getLength() - 1));
				}
			}

		}

		// Get all the health functions for the keywords
		double currentHealth = 0.0;
		Node bestResponse = null;
		double best = -1;

		for (int i = 0; i < similarKeywords.size(); i++)
		{
			if (i != 0)
			{
				if (similarKeywords.get(i).getParentNode() != similarKeywords
						.get(i - 1).getParentNode())
				{
					if (currentHealth >= best)
					{
						bestResponse = similarKeywords.get(i - 1)
								.getParentNode().getParentNode();
						best = currentHealth;
					}
					currentHealth = 0;
				}
			}
			String weight = similarKeywords.get(i).getAttributes()
					.getNamedItem("weight").getNodeValue();
			currentHealth += Double.parseDouble(weight);
		}

		if (currentHealth >= best)
		{
			bestResponse = similarKeywords.get(similarKeywords.size() - 1)
					.getParentNode().getParentNode();
			best = currentHealth;
		}

		if (bestResponse != null)
		{
			Node messagesNode = bestResponse.getChildNodes().item(3);
			int messagesNodeLength = messagesNode.getChildNodes().getLength();
			LinkedList<String> messages = new LinkedList<String>();
			for (int i = 0; i < messagesNodeLength; i++)
			{
				String content = messagesNode.getChildNodes().item(i)
						.getTextContent();
				if (i % 2 != 0)
				{
					messages.add(content);
				}
			}
			Random random = new Random();
			String message = messages.get(random.nextInt(messages.size()))
					.replace("&", "Human").replace("*", "that");

			if (message.contains("[+]"))
			{
				message = message.replace("[+]", "");
				train(lastSimilarKeywords, .1);
			}

			if (message.contains("[-]"))
			{
				message = message.replace("[-]", "");
				train(lastSimilarKeywords, -.1);
			}

			lastResponse = bestResponse;
			lastSimilarKeywords = similarKeywords;
			return new Response(message);
		}

		return new Response("A failure occured in response retrieval.");
	}

	private void train(LinkedList<Node> keywords, double amount)
	{
		if (lastResponse != null)
		{
			LinkedList<Node> keywordsList = new LinkedList<Node>();
			Node keywordsNode = lastResponse.getChildNodes().item(1);
			NodeList keywordsNodeChildren = keywordsNode.getChildNodes();
			for (int i = 0; i < keywordsNodeChildren.getLength(); i++)
			{
				if (i % 2 != 0)
				{
					keywordsList.add(keywordsNodeChildren.item(i));
				}
			}

			for (Node item : keywordsList)
			{
				if (keywords.contains(item))
				{
					NamedNodeMap attribute = item.getAttributes();
					Node weightAttribute = attribute.getNamedItem("weight");
					Double weight = Double.parseDouble(weightAttribute
							.getNodeValue());
					weight += amount;
					weightAttribute.setNodeValue(weight.toString());
				}
			}

			try
			{
				saveDOM();
			} catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null, "Error amending DOM");
			}
		}
	}

	private void saveDOM() throws Exception
	{
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
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
		// System.out.println(xmlString);
	}

}
