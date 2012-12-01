package ida.responses;

/**
 * 
 * Store Keywords taken from UserMessage.
 * Used to store weight and the word itself.
 * 
 * @author Matt
 *
 */

public class Keyword {
	
	private String keyword;
	
	public Keyword(){
	}
	
	public void setKeyword(String keyword){
		this.keyword = keyword;
	}
	
	
	@Override
	public String toString()
	{
		return keyword;
	}
	

}
