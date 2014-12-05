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
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExamReview extends Activity{
	
	LinearLayout container;
	GridLayout questionRow;	
	TextView question;
	TextView answer;
	TextView comment;
	AppProfile creds;
	Intent intent;
	String examID;
	String userID;
	String response;
	String JSON;
	JSONObject JSON_OBJECT;
	JSONArray JSON_ARRAY;
	Login session;
	int color;
	GridLayout.Spec Column;
	GridLayout.Spec Row;
	GridLayout.Spec ansColumn;
	GridLayout.Spec ansRow;
	GridLayout.Spec comColumn;
	GridLayout.Spec comRow;
	Display display;
	Point size;
	int width;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_past_exams_breakdown);
		
		session = new Login();
		
		creds = new AppProfile(ExamReview.this);
		intent = getIntent();
		examID = intent.getStringExtra("EXAM_ID");
		userID = creds.getValue(AppProfile.PREF_ID);
	
		container = (LinearLayout) findViewById(R.id.container);
		
		display = getWindowManager().getDefaultDisplay();
		size = new Point();
		display.getSize(size);
		int width = size.x;	
		
		Log.i("WIDTH OF SCREEN", "WIDTH OF CONTAINER: "  + width);
		
		AsyncTask<String, Void, String> thread_response = new GetExams().execute(
				examID,
				userID,
				"gradedFeedback");
		
		try {
			JSON = thread_response.get().toString();
			Log.i("ExamReview", "ExamID: " + examID + " / userID: " + creds.getValue(AppProfile.PREF_ID));
			Log.i("ExamReview", "Response: " + JSON);

			JSON_OBJECT = new JSONObject(JSON);
			JSON_ARRAY = new JSONArray(JSON_OBJECT.get("exam").toString());

			for(int i=0 ; i<JSON_ARRAY.length() ; i++){
				questionRow = new GridLayout(this);
				question = new TextView(this);					//Question
				answer = new TextView(this);					//User's answer
				comment = new TextView(this);					//Comment about user's answer
								
				answer.setWidth(width/2);
				comment.setWidth(width/2);
				
				Column = GridLayout.spec(0, 2);
	    		Row = GridLayout.spec(0);
	    		ansColumn = GridLayout.spec(0, GridLayout.BASELINE);
	    		ansRow = GridLayout.spec(1);
				comColumn = GridLayout.spec(1, GridLayout.BASELINE);
	    		comRow = GridLayout.spec(1);
								
				
				//Set color of response, based on correct answer
				if(JSON_ARRAY.getJSONObject(i).getString("userCorrect").equals("True"))
					color = Color.GREEN;
				else
					color = Color.RED;
				
				//Set textViews to put into grid
				question.setText(JSON_ARRAY.getJSONObject(i).getString("question"));
				answer.setText(JSON_ARRAY.getJSONObject(i).getString("studentAnswer"));
				answer.setTextColor(color);	
				if(JSON_ARRAY.getJSONObject(i).getString("comment") != null)
					comment.setText(JSON_ARRAY.getJSONObject(i).getString("comment"));
				else if(JSON_ARRAY.getJSONObject(i).getString("comment").equals("null") || JSON_ARRAY.getJSONObject(i).getString("comment")==null)
					comment.setText("No Comment");
				
				
				
				questionRow.addView(question, new GridLayout.LayoutParams(Row, Column));
				questionRow.addView(answer,new GridLayout.LayoutParams(ansRow, ansColumn));				
				questionRow.addView(comment,new GridLayout.LayoutParams(comRow, comColumn));
				
				
				//Alter the row containing question info
				questionRow.setPadding(10, 20, 10, 20);
				if(i%2 == 0)
					questionRow.setBackgroundResource(R.color.PRback);
						
				//Add row to list of questions
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
			response = "";
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
