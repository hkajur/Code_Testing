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
import java.util.List;

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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONObject;


public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.cs490project.MESSAGE";
	
	private EditText  	username=null;
	private EditText  	password=null;
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
		
		
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        	new DownloadWebpageTask().execute("http://192.168.1.102/cstest/index.php");
        }
        else{
        	Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();	
        }
	}
	
//===============================================================================================================	

//	Performs validation of credentials and changes activity based on success of server response
	public void login(View view){
		String ucid = username.getText().toString();
		String pass = password.getText().toString();
		
		loginFunctions session = new loginFunctions(ucid,pass);				

        
		if(session.getUcid() == "" || session.getPassword() == ""){
			Toast.makeText(getApplicationContext(), "Missing UCID/Password", Toast.LENGTH_LONG);
		}
		else if(session.getUcid().equals("admin") && session.getPassword().equals("admin")){
			Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(this, Dashboard.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			intent.putExtra(EXTRA_MESSAGE, "NICE TRY");
			startActivity(intent);
			finish();
		}
		else{
			Toast.makeText(getApplicationContext(), "Incorrect UCID or password",Toast.LENGTH_SHORT).show();
		}
	}

	
//NETWORKING THREAD=========================================================================================================	
	
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> 
    {
    	@Override
    	protected String doInBackground(String... urls) 
    	{
    		try {
    			return downloadUrl(urls[0]);
    		}
    		catch (IOException e){
    			return "Unable to retrieve web page. URL may be invalid.";
    		}
    	}
//    	###################################################################################################################
    	
		@Override
		protected void onPostExecute(String result) 
		{
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		}

//    	###################################################################################################################		
           
		private String downloadUrl(String myurl) throws IOException {
		    InputStream is = null;
		    // Only display the first 500 characters of the retrieved
		    // web page content.
		    int len = 500;
		        
		    try {
		        URL url = new URL(myurl);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setReadTimeout(10000 /* milliseconds */);
		        conn.setConnectTimeout(15000 /* milliseconds */);
		        conn.setRequestMethod("GET");
		        conn.setDoInput(true);
		        // Starts the query
		        conn.connect();
		        int response = conn.getResponseCode();
		        is = conn.getInputStream();

		        // Convert the InputStream into a string
//		        String contentAsString = readIt(is, len);
		        String contentAsString = is.toString();
		        return contentAsString;
		        
		    // Makes sure that the InputStream is closed after the app is
		    // finished using it.
		    } finally {
		        if (is != null) {
		            is.close();
		        } 
		    }
		}

//   	###################################################################################################################       
//       THIS IS FINE
       private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
       {
           StringBuilder result = new StringBuilder();
           boolean first = true;

           for (NameValuePair pair : params)
           {
               if (first)
                   first = false;
               else
                   result.append("&");

               result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
               result.append("=");
               result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
           }

           return result.toString();
       }
   }
//===============================================================================================================		
}
