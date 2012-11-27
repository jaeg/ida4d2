package ida.user;

import ida.Logger;

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
		Logger.log("Keywords from message: ");
		LinkedList<String> list = new LinkedList<String>();
		for (String word: userInput.split("[\\p{P} \\t\\n\\r]")){
			list.add(word.toUpperCase());
			Logger.log(word);
		}
		Logger.log("\n");
		return list;
	}
	
	public void setMessage(String input){
		Logger.log("Message recieved from from user: "+input+"\n");
		this.userInput = input;
	}
	
	@Override
	public String toString(){
		return userInput;
	}

}
