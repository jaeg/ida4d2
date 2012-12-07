package ida.XML;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * A testing class to check to see if the response database is being read
 * properly.
 * 
 */

public class XMLTester {
	public static void main(String[] args) {
		ResponseDatabase parser;
		try {
			parser = new ResponseDatabase();
			LinkedList<String> keywords = new LinkedList<String>();
			keywords.add("I");
			keywords.add("LIKE");
			keywords.add("PIE");

			System.out.println(parser.getResponse(keywords));
		} catch (ParserConfigurationException e) {
			System.out.println("\nParserConfigurationException!!!");
			System.out.println("DocumentBuilder can't build with that config!\n");
			e.printStackTrace();
			System.exit(1);
		} catch (SAXException e) {
			System.out.println("\nSAXException!!!");
			System.out.println("Something is wrong with the document!\n");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("\nIOException!!!\n");
			e.printStackTrace();
			System.exit(1);
		}

	}
}
