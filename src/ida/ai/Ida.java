package ida.ai;

import ida.Logger;
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
		//Voice.sayIt(response);
	}
}
