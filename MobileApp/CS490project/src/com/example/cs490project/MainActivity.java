package com.example.cs490project;

/*
 * MainActivity.java
 * 
 * MainActivity activity that displays
 * input for ucid and password. Initial view 
 * when launching app
 * */

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {
	public final static String NJITLOGIN = "com.example.cs490project.MESSAGE";
	public final static String BACKLOGIN = "com.example.cs490project.MESSAGE2";
	
	private EditText  	username=null;
	private EditText  	password=null;
	TextView textbox;
	private Button 		login;

//===============================================================================================================		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText)findViewById(R.id.ucidText);
		password = (EditText)findViewById(R.id.passText);
		login = (Button)findViewById(R.id.button1);
		textbox = (TextView)findViewById(R.id.textView1);
		textbox.setText("");
		
		//DEBUGGING ONLY
		//Checks network connectivity		
		
		/*
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        	new DownloadWebpageTask().execute("http://web.njit.edu/~dm282/cs490/index.php");
        }
        else{
        	//Toast.makeText(getApplicationContext(), "Connectivity error. Check your connections.", Toast.LENGTH_SHORT).show();	
        }
		if(isConnectedToServer("http://web.njit.edu/~dm282/cs490/index.php", 1500))
		{
			Toast.makeText(getApplicationContext(), "Server Online", Toast.LENGTH_SHORT).show();
		}
		else
		{
			//Toast.makeText(getApplicationContext(), "Connectivity error. Check your connections.", Toast.LENGTH_SHORT).show();
		}
        */
	}
	
//===============================================================================================================	

	//USED TO TEST CONNECTION TO LOCAL SERVER
	public boolean isConnectedToServer(String url, int timeout) {
	    try{
	        URL myUrl = new URL(url);
	        URLConnection connection = myUrl.openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.connect();
	        return true;
	    } catch (Exception e) {
	        // Handle your exceptions
	        return false;
	    }
	}
	
//===============================================================================================================	

//	Performs validation of credentials and changes activity based on success of server response
	public void login(View view){
		String ucid = username.getText().toString();
		String pass = password.getText().toString();

		if(pass==null || ucid==null || pass=="" || ucid=="")
		{
	        Toast.makeText(getApplicationContext(), "Missing credentials", Toast.LENGTH_LONG).show();			
		}
		else
		{
			loginFunctions session = new loginFunctions(ucid,pass);
	        AsyncTask<String, Void, String> response = new DownloadWebpageTask().execute(session.getURL(),session.getUcid(), session.getPassword(), session.getTag(), session.getToken());
		}
		
        //DEBUGGING ONLY
        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
		//textbox.setText(response.toString());
	}

	
//NETWORKING THREAD=========================================================================================================	
	
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> 
    {
    	@Override
    	protected String doInBackground(String... urls) 
    	{
    		try {
    			return downloadUrl(urls[0],urls[1],urls[2],urls[3],urls[4]);
//    			RETURN ONLY SUCCESS
    		}
    		catch (IOException e){
    			return "Cannot connect. Server is not responding.";
    		}
    	}
//    	###################################################################################################################
    	
		@Override
		protected void onPostExecute(String result) 
		{
			JSONObject response;
			
			/*
			  	{ "NJIT_Login" : "Success", "Backend_Login" : "Failed" }
				{ "NJIT_Login" : "Failed", "Backend_Login" : "Success" }
				{ "NJIT_Login" : "Failed", "Backend_Login" : "Failed" }
				Returns this JSON if there is curl error:
					{ "NJIT_Login" : "Error", "Backend_Login" : "Failed" }
			*/
			
			//STUDENT: dm282 / test		
			//TEACHER: professor1 / !professor1
			
			try {
				response = new JSONObject(result);
				if(response.get("Backend_Login").toString().equals("Success") && response.get("userType").toString().equals("Student"))
				{
					Toast.makeText(getApplicationContext(), "Logging in..", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(MainActivity.this,StudentPanel.class);
					intent.putExtra(BACKLOGIN, "BACKEND LOGIN IS " + response.get("Backend_Login").toString());
					MainActivity.this.startActivity(intent);
				}
				else if(response.get("Backend_Login").toString().equals("Success") && response.get("userType").toString().equals("Teacher"))
				{
					Toast.makeText(getApplicationContext(), "Logging in..", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(MainActivity.this,InstructorPanel.class);
					intent.putExtra(BACKLOGIN, "BACKEND LOGIN IS " + response.get("Backend_Login").toString());
					MainActivity.this.startActivity(intent);
				}
				else
					Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				e.printStackTrace();
			}
						
//			Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();
		}
		
//    	###################################################################################################################		
           
		private String downloadUrl(String myurl,String ucid,String password,String tag,String token) throws IOException {
		    String response = null;	         
	         
		    //PARAMETERS TO SEND
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("ucid", ucid);
	        params.put("password", password);
	        params.put("token", token);
	        params.put("tag", tag);
	         
	        //TRY TO POST PARAMETERS TO SERVER AND GET RESPONSE
	        try {
	        	AuthenticatorConnection.sendPostRequest(myurl, params);
	            response = AuthenticatorConnection.readSingleLineRespone();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        //CLOSE CONNECTION AND RETURN THE JSON REPONSE
	        AuthenticatorConnection.disconnect();	        
			return response;
		}
    }
}