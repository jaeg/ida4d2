package ida.parser;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class XMLTester
{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
	{
		XMLParser parser = new XMLParser();
		
		LinkedList<String> keywords = new LinkedList<String>();
		keywords.add("I");
		keywords.add("LIKE");
		keywords.add("PIE");
		
		System.out.println(parser.getResponse(keywords));

	}

}
