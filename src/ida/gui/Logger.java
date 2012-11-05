package ida.gui;

import ida.responses.Keyword;
import ida.user.UserMessage;

import java.util.ArrayList;

import com.google.common.collect.Lists;

/**
 * Takes UserMessage, finds the keywords, and
 * sends those keywords to the IdaSearcher;
 * 
 * @author Matt
 *
 */
public class Logger {
	
	private UserMessage userMessage;
	private ArrayList<Keyword> keywords;
	
	public Logger(UserMessage userMessage){
		this.userMessage = userMessage;
		this.keywords = Lists.newArrayList();
		splitMessageIntoKeywords();
	}
	
	/**
	 * Split the user message into Keywords.
	 * Store those keywords in the ArrayList.
	 * ArrayList can be sent to Searcher.
	 * 
	 */
	/**
	 * TODO: Write this method!
	 */
	private void splitMessageIntoKeywords(){
		
	}

}
