package ida.Utilities;

import ida.XML.ResponseDatabase;
import ida.responses.Response;

/**
 * Identifies special sentences and gives special responses.
 * 
 * TODO: Eventually, these special phrases should be external.
 *
 */
public class LanguageUtility {

	private String sentence;

	public LanguageUtility() {
		this.sentence = null;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public boolean specialSentence() {
		if (sentence.contains("I like") || sentence.contains("I hate") || sentence.contains("My name is")) {
			return true;
		}
		return false;
	}

	public Response respond() {
		return new Response(findResponse());
	}

	private String findResponse() {
		if (sentence.contains("I like")) {
			String[] pieces = sentence.split("I like ", 2);
			String[] strips = pieces[1].split("[\\p{P}]");
			return "Why do you like " + strips[0] + "?";
		} else if (sentence.contains("I hate")) {
			String[] pieces = sentence.split("I hate ", 2);
			String[] strips = pieces[1].split("[\\p{P}]");
			return "Why do you hate " + strips[0] + "?";
		} else if (sentence.contains("My name is")) {
			String[] pieces = sentence.split("My name is ", 2);
			String[] strips = pieces[1].split("[\\p{P}]");
			if (ResponseDatabase.user.getName().equals("Human"))
				ResponseDatabase.user.setName(strips[0]);
			else
				return "You already told me your name was " + ResponseDatabase.user.getName()
						+ ", are you trying to pull a fast one on me?";
			return strips[0] + " is a strange name, but I guess I'll call you that.";
		}
		return null;
	}

}
