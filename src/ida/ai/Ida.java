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
	private boolean questionAsked;
	private int questionStep;
	private String questionAnswer;

	public Ida() {
		Logger.log("IDA4D2 Online....\n");
		userMessage = new UserMessage();
		NLP = new LanguageUtility();
		questionAsked = false;
		questionStep = 0;

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
		if (input.contains("?") || questionAsked == true)
		{
			response = question(input);
		}
		else if (!NLP.specialSentence()) {
			Logger.log("Typical sentence. Searching database.\n");
			response = responseDatabase.getResponse(userMessage.splitMessageIntoKeywords());
		}
		else {
			Logger.log("Special sentence. Using LanguageUtility.\n");
			response = NLP.respond();
		}
		Gui.logField.append("IDA: " + response + "\n");
		
		Logger.log("IDA responded with: " + response + "\n");

		latestUserMessage = userMessage.toString();
		latestIdaMessage = response.toString();

		//Voice.sayIt(response);
	}

	public void learn(String input) {
		if (latestUserMessage != null && latestIdaMessage != null) {
			String previousIda = latestIdaMessage.toUpperCase();
			String[] keywords = previousIda.split("([.,!?:;\"-]|\\s)+");
			String[] messages = new String[1];
			messages[0] = input;

			try {
				XMLWriter.writeResponseToFile("responses.xml", keywords, messages);
				Logger.log("Saved XML\n");
			} catch (Exception e) {
				Logger.log("Could not find the XML file to write to!");
				e.printStackTrace();
			}
		}
	}
	
	public Response question(String input)
	{
		Logger.log("Questions asked!\n");
		if (questionStep == 0)
		{
			Response response;
			response = responseDatabase.getResponse(userMessage.splitMessageIntoKeywords());
			Logger.log("Number of keywords = "+responseDatabase.numberOfKeywords+"\n");
			if (responseDatabase.numberOfKeywords<2)
			{
				response = new Response("How about you answer that?");
				questionStep = 1;
				questionAsked = true;
			}
			else
			{
				questionAsked = false;
			}
			return response;
		}
		else if (questionStep == 1)
		{
			questionAnswer = input;
			questionStep = 2;
			return new Response("Is that the answer?");
		}
		else 
		{
			String answer = input.toUpperCase();
			questionAsked = false;
			questionStep = 0;
			if (answer.contains("YES") || answer.contains("YEP") || answer.contains("YAH"))
			{
				String keywords[] = responseDatabase.lastKeywordsPulled.toArray(new String[responseDatabase.lastKeywordsPulled.size()]);
				String messages[] = {questionAnswer};
				try {
					XMLWriter.writeResponseToFile("responses.xml", keywords, messages);
					Logger.log("Saved XML\n");
				} catch (Exception e) {
					Logger.log("Could not find the XML file to write to!");
					e.printStackTrace();
				}
				return new Response("Now I know!");
			}
			else
			{
				return new Response("Fine be that way!");
			}
		}
	}
	
	public boolean getQuestionAsked()
	{
		return questionAsked;
	}
}
