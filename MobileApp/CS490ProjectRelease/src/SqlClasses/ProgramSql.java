package SqlClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ExamQuestionClasses.ProgramQuestionObject;
import NetworkClasses.Login;
import NetworkClasses.Streamer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;


public class ProgramSql {

	private SQLiteDatabase database;
	private QuestionSqlDatabase dbHelper;
	private String[] allColumns = {  
			QuestionSqlDatabase.QUESTION_ID, 
			QuestionSqlDatabase.QUESTION,
			QuestionSqlDatabase.CORRECT_ANSWER,
			QuestionSqlDatabase.POINTS,
	};
	private Login session = new Login();
//--------------------------------------------------------------------------------------------------------------	
	public ProgramSql(Context context) {
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
	public ProgramQuestionObject createQuestion(
			String questionId, 
			String question,
			String correctAns,
			int points
			) {
		ContentValues values = new ContentValues();

		Cursor cursor = database.query(
				QuestionSqlDatabase.PROGRAM_QUESTIONS,
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
			values.put(QuestionSqlDatabase.QUESTION, question);
			values.put(QuestionSqlDatabase.CORRECT_ANSWER, correctAns);
			values.put(QuestionSqlDatabase.POINTS, points);
			long insertId = database.insert(
					QuestionSqlDatabase.PROGRAM_QUESTIONS, 
					null,
					values
					);
			cursor = database.query(
					QuestionSqlDatabase.PROGRAM_QUESTIONS,
			        allColumns, 
			        QuestionSqlDatabase.LOCAL_ID + " = " + insertId, 
			        null,
			        null, 
			        null, 
			        null
					);
			cursor.moveToFirst();
			ProgramQuestionObject newQuestion = cursorToQuestion(cursor);
			cursor.close();
			return newQuestion;
		}
	}
//--------------------------------------------------------------------------------------------------------------
	public void deleteQuestion(ProgramQuestionObject question) {
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
		Log.i("ProgramQuestionSqlData: ","Question deleted with id: " + id);
		database.delete(
				QuestionSqlDatabase.PROGRAM_QUESTIONS, 
				QuestionSqlDatabase.QUESTION_ID + " = " + id, 
				null);	        	

	}
//--------------------------------------------------------------------------------------------------------------
	public List<ProgramQuestionObject> getAllQuestions() {
		List<ProgramQuestionObject> questions = new ArrayList<ProgramQuestionObject>();

		Cursor cursor = database.query(
				QuestionSqlDatabase.PROGRAM_QUESTIONS,
				allColumns, 
				null, 
				null, 
				null, 
				null, 
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ProgramQuestionObject question = cursorToQuestion(cursor);
			questions.add(question);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return questions;
	}
//--------------------------------------------------------------------------------------------------------------
	private ProgramQuestionObject cursorToQuestion(Cursor cursor) {
		ProgramQuestionObject question = new ProgramQuestionObject(
				cursor.getString(0),//questionid
				cursor.getString(1),//question
				cursor.getString(2),//correct
				cursor.getInt(3)//points
				);
		return question;
	}
	
//################################################################################################	
	
	private class NetworkThread extends AsyncTask<String, Void, String> {	
		@Override
		protected String doInBackground(String... urls) 
		{
			try {
				return postUrl(urls[0],urls[1]);
			}
			catch (IOException e){
				return "Cannot connect. Server is not responding.";
			}
		}

		private String postUrl(String id, String tag) throws IOException {	         
			String response =""; 
			
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("token", session.getToken());
			params.put("tag", tag);
			params.put("id", id);	
			
			//Send request to server to delete exam with following id
			try {
				Streamer.sendPostRequest(session.getURL(), params);
				response = Streamer.readSingleLineRespone();
			} catch (IOException ex) {
				Log.w("ExamSqlData", "Could not connect to server");
				ex.printStackTrace();
			}
			Streamer.disconnect();	        
			return response;
		}//END downloadUrl FUNCTION
	}//END ASYNC CLASS	
}
