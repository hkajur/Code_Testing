package com.malan.cs490project;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.malan.cs490project.R;

import ExamQuestionClasses.SubmitQuestionObject;
import NetworkClasses.Login;
import NetworkClasses.Streamer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class ExamInProgress extends Activity{

	//TODO
	//	- Add network to retrieve
	//  - Remove upon submission
	//  - Get JSON to populate interface
	// to conduct: conductExams
	// to submit: submitExam
	
	Intent intent;
	AppProfile creds;
	Login session;
	String userID;
	String examID;
	String response = "";
	String JSON;
	List<SubmitQuestionObject> submissions;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_in_progress);
		
		session = new Login();
		
		creds = new AppProfile(ExamInProgress.this);
		intent = getIntent();
		examID = intent.getStringExtra("EXAM_ID");
		userID = creds.getValue(AppProfile.PREF_ID);
		
		AsyncTask<String, Void, String> thread_response = new GettingQuestions().execute(examID,userID,"getExamQuestions");
		try {
			JSON = thread_response.get().toString();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		Log.i("ExamInProgress", "ExamID: " + examID + " / userID: " + creds.getValue(AppProfile.PREF_ID));
		Log.i("ExamInProgress", "Response: " + JSON);
		
	}

//######################################################################################################	
	private class GettingQuestions extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) 
		{
			String[] raw_response = {};	         
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("examID", urls[0]);
			params.put("userID", urls[1]);			
			params.put("tag", urls[2]);
			params.put("token", session.getToken());
			
			try {
				if(session.getURL() == null)
					Log.w("ExamInProgress", "URL MISSING");
				if(params == null)
					Log.w("ExamInProgress", "PARAM MISSING");
				else{
				Streamer.sendPostRequest(session.getURL(), params);
				raw_response = Streamer.readMultipleLinesRespone();
				}
				
			} catch (IOException ex) {
				Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
				ex.printStackTrace();
			}
			for(String i : raw_response){
				response+=i;
			}
			Streamer.disconnect();
			return response;
		}		
	}//END ASYNC CLASS	
}
