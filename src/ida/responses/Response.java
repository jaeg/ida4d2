package ida.responses;

public class Response {
	
	private String response;
	
	public Response(String response){
		this.response = response;
	}
	
	@Override
	public String toString(){
		return response;
	}

}
