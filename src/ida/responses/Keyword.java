package ida.responses;

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

}
