package ida.ai;

import ida.Logger;
import ida.Utilities.XMLWriter;
import ida.XML.ResponseDatabase;
import ida.gui.Gui;
import ida.gui.Voice;
import ida.responses.Response;
import ida.user.User;
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
	private User user;
	private String latestIdaMessage;
	private String latestUserMessage;

	public Ida() {
		Logger.log("TEST");
		userMessage = new UserMessage();
		user = new User();
		try {
			responseDatabase = new ResponseDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void respondTo(String input) {
		userMessage.setMessage(input);
		Gui.logField.append("\nME: " + input);
		Response response = responseDatabase.getResponse(userMessage.splitMessageIntoKeywords());
		Gui.logField.append("\nIDA: " + response);

		latestUserMessage = userMessage.toString();
		latestIdaMessage = response.toString();

		// Voice.sayIt(response);
	}

	public void learn(String input){
		if (latestUserMessage != null && latestIdaMessage != null) {			
			String previousIda = latestIdaMessage.toUpperCase();
			String[] keywords = previousIda.split("\\s+");
			
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
