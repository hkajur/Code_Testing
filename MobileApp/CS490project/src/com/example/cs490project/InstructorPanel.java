package com.example.cs490project;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import InstructorClasses.InstructorFragmentTab1;
import InstructorClasses.InstructorFragmentTab2;
import InstructorClasses.InstructorFragmentTab3;
import NetworkClasses.AuthenticatorConnection;
import NetworkClasses.PostAsync;
import NetworkClasses.loginFunctions;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class InstructorPanel extends ActionBarActivity{
	ActionBar.Tab tab1, tab2, tab3;
	Fragment fragmentTabLEFT = new InstructorFragmentTab1();
	Fragment fragmentTabMIDDLE = new InstructorFragmentTab2();
	Fragment fragmentTabRIGHT = new InstructorFragmentTab3();
	loginFunctions session = new loginFunctions(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructor);
		
		Intent intent = getIntent();
		Bundle argsFrag1 = new Bundle();
		argsFrag1.putString("INSTRUCTOR_JSON", intent.getStringExtra("INSTRUCTOR_JSON"));
		
		Bundle argsFrag2 = new Bundle();
		argsFrag2.putString("USER_NAME", intent.getStringExtra("USER_NAME"));
		argsFrag2.putString("USER_ID", intent.getStringExtra("USER_ID"));
		
		AsyncTask<String, Void, String> response = new PostAsync().execute(session.getURL(),intent.getStringExtra("USER_ID"),"getExamQuestions");
		try {
			String newresponse= response.get();
			argsFrag2.putString("QUESTIONS", newresponse);
		} 
		catch (InterruptedException e) 
		{
			Toast.makeText(getBaseContext(), "Retrieval of questions interupted", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		catch (ExecutionException e) 
		{
			Toast.makeText(getBaseContext(), "Execution malfunction", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		
		fragmentTabLEFT.setArguments(argsFrag1);
		fragmentTabMIDDLE.setArguments(argsFrag2);
		fragmentTabRIGHT.setArguments(argsFrag2);
        	
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        
		tab1 = actionBar.newTab().setText("Outstanding");
		tab2 = actionBar.newTab().setText("Construct");
		tab3 = actionBar.newTab().setText("Add Questions");
		
		tab1.setTabListener(new MyTabListener(fragmentTabLEFT));
		tab2.setTabListener(new MyTabListener(fragmentTabMIDDLE));
		tab3.setTabListener(new MyTabListener(fragmentTabRIGHT));
	        
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		actionBar.addTab(tab3);
	}

	private class GettingQuestions extends AsyncTask<String, Void, String> {
		private loginFunctions session = new loginFunctions();
	
		@Override
		protected String doInBackground(String... urls) 
		{
			try {
				return postUrl(urls[0],urls[1], urls[2]);
			}
			catch (IOException e){
				return "Cannot connect. Server is not responding.";
			}
		}
		@Override
		protected void onPostExecute(String result) 
		{
			
		}
		
		private String postUrl(String myurl,String user_id, String tag) throws IOException {
			String[] responseArr = null;	         
			String response =""; 
			
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("user", user_id);
			params.put("tag", tag);
			params.put("token", session.getToken());
			
			try {
				AuthenticatorConnection.sendPostRequest(myurl, params);
				responseArr = AuthenticatorConnection.readMultipleLinesRespone();
			} catch (IOException ex) {
				Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
				ex.printStackTrace();
			}
			for(String i : responseArr){
				response+=i;
			}
			AuthenticatorConnection.disconnect();	        
			return response;
		}//END downloadUrl FUNCTION
	}//END ASYNC CLASS
}