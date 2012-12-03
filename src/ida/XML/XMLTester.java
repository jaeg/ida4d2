package ida.XML;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * A utility for writing to the XML database.
 * Used by KeywordUtility.
 * 
 * @author Matt
 *
 */

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
		ResponseDatabase parser = new ResponseDatabase();
		
		LinkedList<String> keywords = new LinkedList<String>();
		keywords.add("I");
		keywords.add("LIKE");
		keywords.add("PIE");
		
		System.out.println(parser.getResponse(keywords));

	}

}
