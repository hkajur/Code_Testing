package com.malan.cs490project;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import NetworkClasses.ExamRetrieve;
import NetworkClasses.Login;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/*TODO
 *  - Include automatic submission when time runs out
 * */

public class ExamInProgress extends Activity{

	String exam_id;
	String user_id;
	String response;
	String string_questions;
	Login session;
	JSONObject JSONresponse;
	TextView examName;
	ArrayList<QuestionObject> list = new ArrayList<QuestionObject>();
	ExamRetrieve exams;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_in_progress);
		examName = (TextView)findViewById(R.id.ExamNameView);
		
		
		Intent intent = getIntent();
		exam_id = intent.getStringExtra("EXAM_ID");
		user_id = intent.getStringExtra("USER_ID");
		
		
		exams = new ExamRetrieve(exam_id,user_id);
		
		
		
		try {
			response = exams.getResponse();
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
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

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
