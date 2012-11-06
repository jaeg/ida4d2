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
	private double weight;
	
	public Keyword(){
	}
	
	public void setKeyword(String keyword){
		this.keyword = keyword;
	}
	
	/**
	 * TODO: Validate input to make sure weight is between 0 and 1
	 */
	public void setWeight(double weight){
		this.weight = weight;
	}
	
	@Override
	public String toString()
	{
		return keyword;
	}
	

}
