package com.example.cs490project;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class loginFunctions {

	private String user_ucid;
	private String user_password;
	private String token = "0xACA021";
	private JSONParser jsonParser;
    private static String loginURL = "http://10.0.2.2/ah_login_api/";
    private static String login_tag = "login";
    
	public loginFunctions(){
		jsonParser = new JSONParser();
	}
	
	public loginFunctions(String username, String password)
	{
		user_ucid = username;
		user_password = password;
		jsonParser = new JSONParser();
	}

//====================================================================================================	

	public String getUcid(){return user_ucid;}
	public String getPassword(){return user_password;}
	public String getToken(){return token;}
	
	public void setUcid(String username){user_ucid = username;}
	public void setPassword(String password){user_password = password;}
	public void setToken(String newToken){token = newToken;}
	
//====================================================================================================
	
    public JSONObject loginUser(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("ucid", user_ucid));
        params.add(new BasicNameValuePair("password", user_password));
        params.add(new BasicNameValuePair("token", token));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        return json;
    }
	
}