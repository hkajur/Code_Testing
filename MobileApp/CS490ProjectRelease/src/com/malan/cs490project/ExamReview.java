package com.malan.cs490project;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import NetworkClasses.Login;
import NetworkClasses.Streamer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExamReview extends Activity{
	
	LinearLayout container;
	LinearLayout questionRow;
	LinearLayout response_container;
	TextView question;
	TextView answer;
	TextView comment;
	AppProfile creds;
	Intent intent;
	String examID;
	String userID;
	String response = "";
	String JSON;
	JSONObject JSON_OBJECT;
	JSONArray JSON_ARRAY;
	Login session;
	int color;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_past_exams_breakdown);
	
		session = new Login();
		
		creds = new AppProfile(ExamReview.this);
		intent = getIntent();
		examID = intent.getStringExtra("EXAM_ID");
		userID = creds.getValue(AppProfile.PREF_ID);

		/*
		container = (LinearLayout) findViewById(R.id.container);
		response_container = (LinearLayout) findViewById(R.id.responseContainer);
		question = (TextView) findViewById(R.id.question);
		answer = (TextView) findViewById(R.id.answer);
		comment = (TextView) findViewById(R.id.comment);
		*/
		container = (LinearLayout) findViewById(R.id.container);
		questionRow = new LinearLayout(this);
		response_container = new LinearLayout(this);
		question = new TextView(this);
		answer = new TextView(this);
		comment = new TextView(this);
		
		
		AsyncTask<String, Void, String> thread_response = new GetExams().execute(
				examID,
				userID,
				"gradedFeedback");
		
		try {
			JSON = thread_response.get().toString();
			Log.i("ExamInProgress", "ExamID: " + examID + " / userID: " + creds.getValue(AppProfile.PREF_ID));
			Log.i("ExamInProgress", "Response: " + JSON);

			JSON_OBJECT = new JSONObject(JSON);
			JSON_ARRAY = new JSONArray(JSON_OBJECT.get("exam").toString());

			for(int i=0 ; i<JSON_ARRAY.length() ; i++){
				if(JSON_ARRAY.getJSONObject(i).getString("userCorrect").equals("Correct"))
					color = Color.GREEN;
				else
					color = Color.RED;
				question.setText(JSON_ARRAY.getJSONObject(i).getString("question"));
				answer.setText(JSON_ARRAY.getJSONObject(i).getString("studentAnswer"));
				answer.setTextColor(color);
				if(JSON_ARRAY.getJSONObject(i).getString("comment") != null)
					comment.setText(JSON_ARRAY.getJSONObject(i).getString("comment"));
				else
					comment.setText("");
				
				response_container.addView(answer);
				response_container.addView(comment);
				questionRow.addView(question, 						    new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,    
						LinearLayout.LayoutParams.WRAP_CONTENT));
				questionRow.addView(response_container);
				container.addView(questionRow);
			}
			
		} catch (InterruptedException | ExecutionException | JSONException e) {
			e.printStackTrace();
		}

	}
//######################################################################################################	
	private class GetExams extends AsyncTask<String, Void, String> {
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
				Streamer.sendPostRequest(session.getURL(), params);
				raw_response = Streamer.readMultipleLinesRespone();
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
