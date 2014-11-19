package SqlClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/*TODO
 * - Add network functionality when creating question. Add that to EACH CLASS
 * */

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
	//TODO --SUBMIT FUNCTIONALITY
	public void submitQuestions(ArrayList<String> questionsForSubmit, String exam_name) throws JSONException{
		JSONObject temp = new JSONObject();
		temp.put("examName", exam_name);
		temp.put("questionIDs", questionsForSubmit);
		
		
		Log.i("QuestionSql", "Exam " + exam_name + " submitted");
		Log.i("QuestionSql", "Exam: " + temp.toString());
		//network thread here to send over exam
		//if post successful instructorexam.addexam here with unreleased		
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
		String response=null;
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
	
	private class NetworkThread extends AsyncTask<JSONObject, Void, String> {	
		@Override
		protected String doInBackground(JSONObject... urls) 
		{
			String response = ""; 
			
			/*
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("token", session.getToken());				
			params.put("questionIDs", urls[0].getString(questionId));	
			params.put("exa", urls[0].getString(questionId));	
			params.put("tag", urls[1]);
			
			
			urls[0] is the JSON object
			*/
			
			
			//Send request to server to delete exam with following id
			try {
				Streamer.sendPostRequest(session.getURL(), urls[0]);
				response = Streamer.readSingleLineRespone();
			} catch (IOException ex) {
				Log.w("ExamSqlData", "Could not connect to server");
				ex.printStackTrace();
			}
			Streamer.disconnect();	        
			return response;
		}		
	}//END ASYNC CLASS	
}
