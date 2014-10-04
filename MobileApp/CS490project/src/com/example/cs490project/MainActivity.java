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
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {
	public final static String NAME = "com.example.cs490project.MESSAGE";
	public final static String TYPE = "com.example.cs490project.MESSAGE";
	
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
		textbox.setText("");
		
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
//		textbox.setText(response.toString());
	}

	
//NETWORKING THREAD=========================================================================================================	
	
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> 
    {
    	private Activity activity;
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
			String success = null;
			String error = null;
			String tag = null;
			String name = null;
			String type = null;			
			
			try {
				response = new JSONObject(result);
				if(response.get("success").toString().equals("1"))
				{
					Toast.makeText(getApplicationContext(), "Loggin in..", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(MainActivity.this,Dashboard.class);
					intent.putExtra(NAME, response.get("name").toString());
					intent.putExtra(TYPE, response.get("type").toString());
					MainActivity.this.startActivity(intent);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_LONG).show();
				}
				
				error = response.getString("error");
				tag = response.getString("tag");
				name = response.getString("name");
				type = response.getString("type");
			} catch (JSONException e) {
				e.printStackTrace();
			}
						
//			Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();
		}

//		###################################################################################################################
	
		private void accountConfirmed(String success, String error, String name, String type){
			
			if(success.equals("1"))
			{
				Toast.makeText(getApplicationContext(), "Loggin in..", Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(activity, Dashboard.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 			
				intent.putExtra(NAME, name);
				intent.putExtra(TYPE, type);
				activity.startActivity(intent);
				finish();
				
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_LONG).show();	
			}
		}
		
//    	###################################################################################################################		
           
		private String downloadUrl(String myurl,String ucid,String password,String tag,String token) throws IOException {
		    String response = null;	         
	         
		    //PARAMETERS TO SEND
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("ucid", ucid);
	        params.put("password", password);
	        params.put("tag", tag);
	        params.put("token", token);
	         
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