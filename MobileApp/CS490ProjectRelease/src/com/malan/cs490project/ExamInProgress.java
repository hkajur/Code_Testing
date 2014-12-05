package com.malan.cs490project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.malan.cs490project.R;

import ExamQuestionClasses.SubmitQuestionObject;
import NetworkClasses.Login;
import NetworkClasses.Streamer;
import SqlClasses.StudentExamSql;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ExamInProgress extends Activity{
	
	Intent intent;
	AppProfile creds;
	Login session;
	String userID;
	String examID;
	String response = "";
	String JSON;
	JSONObject JSON_OBJECT;
	JSONArray JSON_ARRAY;
	LinearLayout layout;
	RadioGroup group;
	RadioButton answers;
	TextView question;
	EditText answer;
	Button submit;
	List<SubmitQuestionObject> submissions;
	Map<String,RadioGroup> radio_responses;
	Map<String,EditText> text_responses;
	StudentExamSql StudentExamsSql;
	boolean valid;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_in_progress);
		
		session = new Login();
		StudentExamsSql = new StudentExamSql(this);
        StudentExamsSql.open();
		
		creds = new AppProfile(ExamInProgress.this);
		intent = getIntent();
		examID = intent.getStringExtra("EXAM_ID");
		userID = creds.getValue(AppProfile.PREF_ID);
		
		submissions = new ArrayList<SubmitQuestionObject>();
		radio_responses = new HashMap<String, RadioGroup>();
		text_responses = new HashMap<String, EditText>();

		
		AsyncTask<String, Void, String> thread_response = new GettingQuestions().execute(
				examID,
				userID,
				"conductExams");
		try {
			JSON = thread_response.get().toString();
			
			Log.i("ExamInProgress", "ExamID: " + examID + " / userID: " + creds.getValue(AppProfile.PREF_ID));
			Log.i("ExamInProgress", "Response: " + JSON);
			
			JSON_OBJECT = new JSONObject(JSON);
			JSON_ARRAY = new JSONArray(JSON_OBJECT.get("questions").toString());

			
			for(int i = 0; i < JSON_ARRAY.length(); i++)
			{
				int temp_placeholder=0;
				layout = (LinearLayout) findViewById(R.id.container);
				
				String questionID = JSON_ARRAY.getJSONObject(i).getString("questionID");
				
				SubmitQuestionObject tempQuestion = new SubmitQuestionObject();
				tempQuestion.setQuestionId(questionID);
				submissions.add(tempQuestion);
				
				question = new TextView(this);
				question.setText(JSON_ARRAY.getJSONObject(i).getString("question"));
				question.setPadding(11, 35, 11, 35);
				layout.addView(question);
				temp_placeholder = question.getId();
				if(JSON_ARRAY.getJSONObject(i).getString("question_type").equals("MC") ||
					JSON_ARRAY.getJSONObject(i).getString("question_type").equals("TF")){

					group = new RadioGroup(this);
					radio_responses.put(questionID, group);
					
					JSONArray choiceArr = (JSONArray) (JSON_ARRAY.getJSONObject(i).getJSONArray("choices"));
					for(int j=0 ; j<choiceArr.length() ; j++){
						answers = new RadioButton(this);
						answers.setText(choiceArr.getJSONObject(j).getString("choice"));
						group.addView(answers,
							    new RadioGroup.LayoutParams(
							        RadioGroup.LayoutParams.WRAP_CONTENT,    
							        RadioGroup.LayoutParams.WRAP_CONTENT));						
					}
					layout.addView(group,
						    new LinearLayout.LayoutParams(
						        LinearLayout.LayoutParams.MATCH_PARENT,    
						        LinearLayout.LayoutParams.WRAP_CONTENT));
					temp_placeholder = group.getId();
				}
				else{
	    			answer = new EditText(this);
	    			text_responses.put(questionID, answer);
	    			answer.setHint("Answer here");
	    			temp_placeholder = answer.getId();
	    			layout.addView(answer);
				}
			}
			
			submit = new Button(this);
			submit.setText("Submit");
			submit.setBackground(getResources().getDrawable(R.drawable.flat_selector));
			submit.setLayoutParams(new LayoutParams(
			        ViewGroup.LayoutParams.MATCH_PARENT,
			            ViewGroup.LayoutParams.WRAP_CONTENT));
			layout.addView(submit);
			
		} catch (InterruptedException | ExecutionException | JSONException e) {
			e.printStackTrace();
		} 
		
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				valid = false;
				JSONArray JsonArrSubmit = new JSONArray();
				JSONObject JsonObjSubmit;
				
				for (SubmitQuestionObject i : submissions){
					JsonObjSubmit = new JSONObject();
					if(radio_responses.get(i.getQuestionId()) != null){
						RadioGroup temp = radio_responses.get(i.getQuestionId());
						if(temp.getCheckedRadioButtonId() != -1){
							RadioButton selected = (RadioButton) findViewById(temp.getCheckedRadioButtonId());
							i.setUserAnswer(selected.getText().toString());
							valid = true;
							Log.w("ExamInProgress", "State of validity: " + valid);
						}
						else{
							Log.w("ExamInProgress", "Missing Radio INPUT");
							valid = false;
							Log.w("ExamInProgress", "State of validity: " + valid);
							break;
						}
					}
					if(text_responses.get(i.getQuestionId()) != null){
						EditText temp = text_responses.get(i.getQuestionId().toString());
						if(temp.getText().toString() != null){
							i.setUserAnswer(temp.getText().toString());
							valid = true;
							Log.w("ExamInProgress", "State of validity: " + valid);
						}
						else{
							i.setUserAnswer("No-Response-Submitted");
							valid = true;
							Log.w("ExamInProgress", "State of validity: " + valid);							
						}
					}				
					try {
						JsonObjSubmit.put("questionID", i.getQuestionId());
						JsonObjSubmit.put("userAnswer", i.getUserAnswer());
					} catch (JSONException e) {
						e.printStackTrace();
					}					
					JsonArrSubmit.put(JsonObjSubmit);
				}

				Log.i("ExamInProgress", "Submitting: " + JsonArrSubmit.toString());
				
				AsyncTask<String, Void, String> thread_response = null;
				if(valid){
					thread_response = new SubmitExam().execute(
							examID,
							userID,
							JsonArrSubmit.toString(),
							"submitExam");
				}
				try {
					Log.i("ExamInProgress", "Response: " + thread_response.get());
					Log.i("ExamInProgress", "Exam " + examID + " submitted");
					if(StudentExamsSql.removeExam(examID))
						Log.i("ExamInProgress", "Exam " + examID + " submited and altered");
					StudentExamsSql.close();
					finish();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				} catch (NullPointerException e){
					Log.i("ExamInProgress", "Response: Exam NOT submitted. Missing Radio Input");
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExamInProgress.this);
					alertDialog.setTitle("Missing Answers");
					alertDialog.setCancelable(false);
					alertDialog.setMessage("You are missing answers to one or more multiple choice questions.");
					alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
					alertDialog.setNeutralButton("Ok",
					        new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int which) {					            	
					            	dialog.dismiss();
					            }
					        });
					alertDialog.show();
				}
			}
        });
		
	}

//######################################################################################################	
	private class GettingQuestions extends AsyncTask<String, Void, String> {
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
//######################################################################################################	
	private class SubmitExam extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) 
		{
			response = "";
			String[] raw_response = {};	         
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("examID", urls[0]);
			params.put("userID", urls[1]);
			params.put("questionAnswer", urls[2]);
			params.put("tag", urls[3]);
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
