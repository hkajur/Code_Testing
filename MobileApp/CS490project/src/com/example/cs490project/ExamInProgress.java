package com.example.cs490project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import NetworkClasses.AuthenticatorConnection;
import NetworkClasses.PostAsync;
import NetworkClasses.loginFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ExamInProgress extends Activity{

	String exam_id;
	String user_id;
	String response;
	String string_questions;
	loginFunctions session;
	JSONObject JSONresponse;
	TextView examName;
	ArrayList<QuestionObject> list = new ArrayList<QuestionObject>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_in_progress);
		examName = (TextView)findViewById(R.id.ExamNameView);
		
		
		Intent intent = getIntent();
		exam_id = intent.getStringExtra("EXAM_ID");
		user_id = intent.getStringExtra("USER_ID");
		
		
		AsyncTask<String, Void, String>  getExams = new ExamRetrieval().execute(session.getURL(),user_id,exam_id,"conductExams");
		
		try {
			response = getExams.get();
			JSONresponse = new JSONObject(response);
			examName.setText(JSONresponse.getString("examName"));
			
			string_questions = JSONresponse.get("questions").toString();
			JSONArray questions = new JSONArray(string_questions);
			
			
			
			for (int i=0; i < questions.length() ; i++) 
			{
				try
				{
					JSONObject tempJSON = questions.getJSONObject(i);
					QuestionObject tempQuestion = new QuestionObject(
							tempJSON.getString("questionID"),
							tempJSON.getString("question_type"),
							tempJSON.getString("question")
							);
					JSONArray tempChoices = new JSONArray(tempJSON.getJSONArray("choices"));
					for(int j=0 ; j<tempChoices.length() ; j++)
					{
						JSONObject blah = tempChoices.getJSONObject(j);
						tempQuestion.addToChoices(blah.getString("choice"));
					}
					list.add(tempQuestion);
				}
				catch(Exception e){
					e.printStackTrace();
				}				
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

//###################################################################################################################	
	
    private class ExamRetrieval extends AsyncTask<String, Void, String> 
    {
    	@Override
    	protected String doInBackground(String... urls) 
    	{
    		try {
    			return downloadUrl(urls[0],urls[1],urls[2],urls[3]);
    		}
    		catch (IOException e){
    			return "Cannot connect. Server is not responding.";
    		}
    	}
    	
		@Override
		protected void onPostExecute(String result) 
		{
		}
		
				
		private String downloadUrl(String myurl, String user_id, String exam_id, String tag) throws IOException {
		    String[] responseArr = null;
		    String response = "";
		    
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("examID", exam_id);
	        params.put("userID", user_id);
	        params.put("token", session.getToken());
	        params.put("tag", tag);
	         
	        try {
	        	AuthenticatorConnection.sendPostRequest(myurl, params);
	            responseArr = AuthenticatorConnection.readMultipleLinesRespone();
	        } catch (IOException ex) {
	        	Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
	            ex.printStackTrace();
	        }
	        for(String i : responseArr)
	        	response += i;
	        AuthenticatorConnection.disconnect();	        
			return response;
		}
    }//END ASYNC CLASS	

//###################################################################################################################    
    
    private class QuestionObject {
    	
    	public String ID;
    	public String TYPE;
    	public String QUESTION;
    	public String[] CHOICES = new String[4];
    	private int counter = 0;
    	
    	public QuestionObject(String id, String type, String question){
        	ID = id;
        	TYPE = type;
        	QUESTION = question;
    	}
    	
    	public void addToChoices(String choice){
    		CHOICES[counter] = choice;
    		counter++;
    	}
    	
    }
}
