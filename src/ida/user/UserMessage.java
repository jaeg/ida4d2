package ida.user;

import java.util.LinkedList;


/**
 * Data structure that stores the user 
 * input as soon as it is entered.
 * 
 * @author Matt Bennett
 * @since 0.1
 *
 */
public class UserMessage {
	
	private String userInput;
	
	public UserMessage(){
		userInput = null;
	}
	
	public LinkedList<String> splitMessageIntoKeywords(){
		LinkedList<String> list = new LinkedList<String>();
		for (String word: userInput.split("[\\p{P} \\t\\n\\r]")){
			list.add(word.toUpperCase());
		}
		return list;
	}
	
	public void setMessage(String input){
		this.userInput = input;
	}

}
