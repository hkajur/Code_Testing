package SqlClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import ExamQuestionClasses.QuestionObject;
import NetworkClasses.Login;
import NetworkClasses.Streamer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class QuestionSql {

	private SQLiteDatabase database;
	private QuestionSqlDatabase dbHelper;
	private String[] allColumns = {
			QuestionSqlDatabase.QUESTION_ID, 
			QuestionSqlDatabase.QUESTION_TYPE, 
			QuestionSqlDatabase.QUESTION,
	};
	private Login session = new Login();
//--------------------------------------------------------------------------------------------------------------	
	public QuestionSql(Context context) {
		dbHelper = new QuestionSqlDatabase(context);
	}
//--------------------------------------------------------------------------------------------------------------
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
//--------------------------------------------------------------------------------------------------------------
	public void close() {
		dbHelper.close();
	}
//--------------------------------------------------------------------------------------------------------------
	public boolean submitExam(ArrayList<String> questionsForSubmit, String exam_name) throws JSONException, InterruptedException, ExecutionException{
		String temp = questionsForSubmit.toString();
		
		AsyncTask<String, Void, String> response_thread = new submitExamThread().execute(temp, 
				exam_name, 
				"createExam");
		
		Log.i("QuestionSql", "Exam " + exam_name + " submitted");
		Log.i("QuestionSql", "Exam_name: " + exam_name + " / Questions: " + temp);
		
		Log.i("QuestionSql", "Returned: " + response_thread.get());
		
		JSONObject returned = new JSONObject(response_thread.get());
		if(returned.getString("examCreated").equals("Success")){
			Log.i("QuestionSql", "Response: " + exam_name + " == SUCCESS");
			
			
			return true;
		}
		else{
			Log.w("QuestionSql", "Response: " + exam_name + " == FAILED");
			Log.w("QuestionSql", "Error: " + returned.getString("Error"));
			return false;
		}
	}

//--------------------------------------------------------------------------------------------------------------
	public boolean createQuestion(String points, String questionType, String[] responses, String question) {
		
		AsyncTask<String, Void, String> response_thread = null;
		
		switch (questionType){
			case "MC":
				response_thread = new submitQuestionThread().execute(responses.toString(), "MultipleChoiceQuestionInsert");
				break;
			case "TF":
				response_thread = new submitQuestionThread().execute(responses.toString(), "TrueFalseChoiceQuestionInsert");
				break;
			case "SH":
				response_thread = new submitQuestionThread().execute(responses.toString(), "ShortAnswerQuestionInsert");
				break;
			case "PR":
				response_thread = new submitQuestionThread().execute(responses.toString(), "ProgramQuestionInsert");
				break;
		}
			
		try {
			String response = response_thread.get();
			JSONObject JSON = new JSONObject(response);
			if(JSON.getString("questionCreated").equals("Success")){
				return true;
			}
			else
				return false;
		} catch (InterruptedException | ExecutionException | JSONException e) {
			e.printStackTrace();
		}
		return false; 
	}

//--------------------------------------------------------------------------------------------------------------
	public QuestionObject createQuestion(String questionId,String questionType,String question) {
		ContentValues values = new ContentValues();

		Cursor cursor = database.query(
				QuestionSqlDatabase.DISPLAY_QUESTIONS,
				allColumns,
				QuestionSqlDatabase.QUESTION_ID + " = " + questionId,
				null,
				null,
				null,
				null
				);
		
		if(cursor.moveToFirst()){
		    return null;
		}else{
			values.put(QuestionSqlDatabase.QUESTION_ID, questionId);
			values.put(QuestionSqlDatabase.QUESTION_TYPE, questionType);
			values.put(QuestionSqlDatabase.QUESTION, question);
			long insertId = database.insert(
					QuestionSqlDatabase.DISPLAY_QUESTIONS, 
					null,
					values
					);
			cursor = database.query(
					QuestionSqlDatabase.DISPLAY_QUESTIONS,
			        allColumns, 
			        QuestionSqlDatabase.LOCAL_ID + " = " + insertId, 
			        null,
			        null, 
			        null, 
			        null
					);
			cursor.moveToFirst();
			QuestionObject newQuestion = cursorToQuestion(cursor);
			cursor.close();
			return newQuestion;
		}
	}
//--------------------------------------------------------------------------------------------------------------
	public void deleteQuestion(QuestionObject question) {
		String id = question.getId();
//		String response="";
		//Set Params

		/* ENABLE ONCE SERVER SET UP
		AsyncTask<String, Void, String> response_thread = new NetworkThread().execute(id,"DeleteExam");
		try {
			response = response_thread.get();
		} 
		catch (InterruptedException e) 
		{
			Log.w("ExamSqlData", "Interrupted connection while deleting exam");
			e.printStackTrace();
		}
		catch (ExecutionException e) 
		{
			Log.w("ExamSqlData", "Execution malfunction while deleting exam");
			e.printStackTrace();
		}
			
		//If exam deleted successfully online, delete on mobile version
		if(response.equals("Success")){
			Log.i("ExamSqlData: ","Exam deleted with id: " + id);
			database.delete(
					ExamSqlDataBase.INSTRUCTOR_EXAMS, 
					ExamSqlDataBase.EXAM_ID + " = " + id, 
					null);	        	
		}
		else{
			Log.i("ExamSqlData: ","Error deleting exam with exam_id: " + id);
		}	
		*/	
		Log.i("QuestionSqlData: ","Question deleted with id: " + id);
		database.delete(
				QuestionSqlDatabase.DISPLAY_QUESTIONS, 
				QuestionSqlDatabase.QUESTION_ID + " = " + id, 
				null);	        	
	}	
//--------------------------------------------------------------------------------------------------------------
	public List<QuestionObject> getAllQuestions() {
		List<QuestionObject> questions = new ArrayList<QuestionObject>();

		Cursor cursor = database.query(
				QuestionSqlDatabase.DISPLAY_QUESTIONS,
				allColumns, 
				null, 
				null, 
				null, 
				null, 
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			QuestionObject question = cursorToQuestion(cursor);
			questions.add(question);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return questions;
	}
//--------------------------------------------------------------------------------------------------------------
	private QuestionObject cursorToQuestion(Cursor cursor) {
		QuestionObject question = new QuestionObject(
				cursor.getString(0),//questionid
				cursor.getString(1),//question
				cursor.getString(2)//correct
				);
		return question;
	}
	
//################################################################################################	
	
	private class submitExamThread extends AsyncTask<String, Void, String> {	
		@Override
		protected String doInBackground(String... urls) 
		{
			String response = ""; 
			
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("token", session.getToken());				
			params.put("questionId", urls[0]);
			params.put("examName", urls[1]);
			params.put("tag", urls[2]);
			
			
			//Send request to server to delete exam with following id
			try {
				Streamer.sendPostRequest(session.getURL(), params);
				response = Streamer.readSingleLineRespone();
			} catch (IOException ex) {
				Log.w("QuestionsSqlData", "Could not connect to server");
				ex.printStackTrace();
			}
			Streamer.disconnect();	        
			return response;
		}		
	}//END ASYNC CLASS	
//################################################################################################	
	
	private class submitQuestionThread extends AsyncTask<String, Void, String> {	
		@Override
		protected String doInBackground(String... urls) 
		{
			String response = ""; 
			
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("token", session.getToken());				
			params.put("questionId", urls[0]);
			params.put("examName", urls[1]);
			params.put("tag", urls[2]);
			
			
			//Send request to server to delete exam with following id
			try {
				Streamer.sendPostRequest(session.getURL(), params);
				response = Streamer.readSingleLineRespone();
			} catch (IOException ex) {
				Log.w("QuestionsSqlData", "Could not connect to server");
				ex.printStackTrace();
			}
			Streamer.disconnect();	        
			return response;
		}		
	}//END ASYNC CLASS	
}
