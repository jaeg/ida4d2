package ida.user;

/**
 * Stores information on the user, which Ida4D2 will remember.
 *
 */
public class User {

	private String userName;

	public User() {
		userName = "Human";
	}

	public void setName(String name) {
		this.userName = name;
	}

	public String getName() {
		return userName;
	}

}
