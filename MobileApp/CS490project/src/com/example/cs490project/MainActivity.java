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
import org.json.JSONObject;


public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.cs490project.MESSAGE";
	
	private EditText  	username=null;
	private EditText  	password=null;
	TextView textbox;
	private Button 		login;
//	CHECK
//===============================================================================================================		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
//	CHECK
//===============================================================================================================		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText)findViewById(R.id.ucidText);
		password = (EditText)findViewById(R.id.passText);
		login = (Button)findViewById(R.id.button1);
		textbox = (TextView)findViewById(R.id.textView1);
		
		//DEBUGGING ONLY
		//Checks network connectivity		
		
		/*
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        	new DownloadWebpageTask().execute("http://10.200.170.128/cstest/index.php");
        }
        else{
        	Toast.makeText(getApplicationContext(), "Connectivity error. Check your connections.", Toast.LENGTH_SHORT).show();	
        }
        */
        
	}
	
//===============================================================================================================	

//	Performs validation of credentials and changes activity based on success of server response
	public void login(View view){
		String ucid = username.getText().toString();
		String pass = password.getText().toString();
		
		loginFunctions session = new loginFunctions(ucid,pass);				

        AsyncTask<String, Void, String> response = new DownloadWebpageTask().execute(session.getURL(),session.getUcid(), session.getPassword(), session.getTag(), session.getToken());
//		Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
		textbox.setText(response.toString());
	}

	
//NETWORKING THREAD=========================================================================================================	
	
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> 
    {
    	@Override
    	protected String doInBackground(String... urls) 
    	{
    		try {
    			return downloadUrl(urls[0],urls[1],urls[2],urls[3],urls[4]);
    		}
    		catch (IOException e){
    			return "Cannot connect. Server is not responding.";
    		}
    	}
//    	###################################################################################################################
    	
		@Override
		protected void onPostExecute(String result) 
		{
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		}

//    	###################################################################################################################		
           
		private String downloadUrl(String myurl,String ucid,String password,String tag,String token) throws IOException {
		    InputStream is = null;
		    String response = null;
	       /*
		    try {
	        	AuthenticatorConnection.sendGetRequest(myurl);
	            response = AuthenticatorConnection.readSingleLineRespone();	           
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        AuthenticatorConnection.disconnect();
	        */
	         
	         
	        // test sending POST request
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("ucid", ucid);
	        params.put("password", password);
	        params.put("tag", tag);
	        params.put("token", token);
	         
	        try {
	        	AuthenticatorConnection.sendPostRequest(myurl, params);
	            response = AuthenticatorConnection.readSingleLineRespone();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        AuthenticatorConnection.disconnect();
	        
			return response;
		}
    }
}