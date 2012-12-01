package ida.ai;

import ida.Logger;
import ida.Utilities.LanguageUtility;
import ida.Utilities.XMLWriter;
import ida.XML.ResponseDatabase;
import ida.gui.Gui;
import ida.responses.Response;
import ida.user.UserMessage;

/**
 * Our chatbot. The head of the program.
 * 
 * @author Matt
 * 
 */
public class Ida {

	private UserMessage userMessage;
	private ResponseDatabase responseDatabase;
	private String latestIdaMessage;
	private String latestUserMessage;
	private LanguageUtility NLP;

	public Ida() {
		Logger.log("IDA4D2 Online....\n");
		userMessage = new UserMessage();
		NLP = new LanguageUtility();

		try {
			responseDatabase = new ResponseDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void respondTo(String input) {
		userMessage.setMessage(input);
		Gui.logField.append("ME: " + input + "\n");

		NLP.setSentence(input);

		Response response;
		if (!NLP.specialSentence()) {
			Logger.log("Typical sentence. Searching database.\n");
			response = responseDatabase.getResponse(userMessage.splitMessageIntoKeywords());
		} else {
			Logger.log("Special sentence. Using LanguageUtility.\n");
			response = NLP.respond();
		}
		Gui.logField.append("IDA: " + response + "\n");
		
		Logger.log("IDA responded with: " + response + "\n");

		latestUserMessage = userMessage.toString();
		latestIdaMessage = response.toString();

		// Voice.sayIt(response);
	}

	public void learn(String input) {
		if (latestUserMessage != null && latestIdaMessage != null) {
			String previousIda = latestIdaMessage.toUpperCase();
			String[] keywords = previousIda.split("([.,!?:;'\"-]|\\s)+");
			String[] messages = new String[1];
			messages[0] = input;

			try {
				XMLWriter.writeResponseToFile("responses.xml", keywords, messages);
			} catch (Exception e) {
				Logger.log("Could not find the XML file to write to!");
				e.printStackTrace();
			}
		}
	}
}
