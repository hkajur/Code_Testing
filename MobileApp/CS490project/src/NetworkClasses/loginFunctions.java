package NetworkClasses;

/*
 * loginFunctions.java
 * 
 * loginFunctions object that takes in 
 * ucid, password, and tag. Can conduct 
 * login given the above credentials
 * */

public class loginFunctions {

	private String user_ucid;
	private String user_password;
	private String token = "0xACA021";
//	private static String loginURL = "http://192.168.1.102/cstest/index.php";		//HOME SERVER
//  private static String loginURL = "http://10.200.168.116/cstest/index.php";		//LOCAL SERVER AT SCHOOL
	private static String loginURL = "http://web.njit.edu/~dm282/cs490/index.php";
//    tag set to login by default
	private static String tag = "login";
    
	public loginFunctions()
	{
		user_ucid = "root";
		user_password = "default";
	}
	
	public loginFunctions(String username, String password)
	{
		user_ucid = username;
		user_password = password;
	}

//====================================================================================================	

	public String getUcid(){return user_ucid;}
	public String getPassword(){return user_password;}
	public String getToken(){return token;}
	public String getTag(){return tag;}
	public String getURL(){return loginURL;}
	
	public void setUcid(String username){user_ucid = username;}
	public void setPassword(String password){user_password = password;}
	public void setToken(String newToken){token = newToken;}
	public void setTag(String newTag){tag = newTag;}
	
//====================================================================================================	
}