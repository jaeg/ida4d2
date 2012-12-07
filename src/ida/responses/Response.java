package ida.responses;

/**
 * Store response that Ida will give the user.
 * 
 */

public class Response {

	private String response;

	public Response(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return response;
	}

}
