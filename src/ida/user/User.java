package ida.user;

public class User {
	
	private String userName;
	public boolean nameIsSet;
	
	public User(){
		nameIsSet = false;
	}
	
	public void setName(String name){
		userName = name;
		nameIsSet = true;
	}

}
