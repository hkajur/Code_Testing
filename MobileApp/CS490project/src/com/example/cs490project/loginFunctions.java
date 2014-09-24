package com.example.cs490project;

public class loginFunctions {

	private String user_ucid;
	private String user_password;
	private String token = "0xACA021";
	
	public loginFunctions(){}
	
	public loginFunctions(String username, String password)
	{
		user_ucid = username;
		user_password = password;
	}
	
	public String getUcid(){return user_ucid;}
	public String getPassword(){return user_password;}
	public String getToken(){return token;}
	
	public void setUcid(String username){user_ucid = username;}
	public void setPassword(String password){user_password = password;}
	public void setToken(String newToken){token = newToken;}
	
	
	
}
