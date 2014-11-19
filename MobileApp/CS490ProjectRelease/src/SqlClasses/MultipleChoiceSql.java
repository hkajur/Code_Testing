package SqlClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ExamQuestionClasses.MultipleChoiceQuestionObject;
import NetworkClasses.Login;
import NetworkClasses.Streamer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class MultipleChoiceSql{
	private SQLiteDatabase database;
	private QuestionSqlDatabase dbHelper;
	private String[] allColumns = {  
			QuestionSqlDatabase.QUESTION_ID, 
			QuestionSqlDatabase.QUESTION,
			QuestionSqlDatabase.CORRECT_ANSWER,
			QuestionSqlDatabase.CORRECT_REASON,
			QuestionSqlDatabase.WRONG_ANS1,
			QuestionSqlDatabase.WRONG_RES1,
			QuestionSqlDatabase.WRONG_ANS2,
			QuestionSqlDatabase.WRONG_RES2,
			QuestionSqlDatabase.WRONG_ANS3,
			QuestionSqlDatabase.WRONG_RES3
	};
	private Login session = new Login();
//--------------------------------------------------------------------------------------------------------------	
	public MultipleChoiceSql(Context context) {
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
	public MultipleChoiceQuestionObject createQuestion(
			String questionId, 
			String question,
			String correctAns,
			String correctRes,
			String wrongAns1,
			String wrongRes1,
			String wrongAns2,
			String wrongRes2,
			String wrongAns3,
			String wrongRes3,
			int points
			) {
		ContentValues values = new ContentValues();

		Cursor cursor = database.query(
				QuestionSqlDatabase.MC_QUESTIONS,
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
			values.put(QuestionSqlDatabase.CORRECT_REASON, correctRes);
			values.put(QuestionSqlDatabase.WRONG_ANS1, wrongAns1);
			values.put(QuestionSqlDatabase.WRONG_RES1, wrongRes1);
			values.put(QuestionSqlDatabase.WRONG_ANS2, wrongAns2);
			values.put(QuestionSqlDatabase.WRONG_RES2, wrongRes1);
			values.put(QuestionSqlDatabase.WRONG_ANS3, wrongAns3);
			values.put(QuestionSqlDatabase.WRONG_RES3, wrongRes3);
			values.put(QuestionSqlDatabase.POINTS, points);
			long insertId = database.insert(
					QuestionSqlDatabase.MC_QUESTIONS, 
					null,
					values
					);
			cursor = database.query(
					QuestionSqlDatabase.MC_QUESTIONS,
			        allColumns, 
			        QuestionSqlDatabase.LOCAL_ID + " = " + insertId, 
			        null,
			        null, 
			        null, 
			        null
					);
			cursor.moveToFirst();
			MultipleChoiceQuestionObject newQuestion = cursorToQuestion(cursor);
			cursor.close();
			return newQuestion;
		}
	}
//--------------------------------------------------------------------------------------------------------------
	public void deleteQuestion(MultipleChoiceQuestionObject question) {
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
		Log.i("MCQuestionSqlData: ","Question deleted with id: " + id);
		database.delete(
				QuestionSqlDatabase.MC_QUESTIONS, 
				QuestionSqlDatabase.QUESTION_ID + " = " + id, 
				null);	        	

	}
//--------------------------------------------------------------------------------------------------------------
	public List<MultipleChoiceQuestionObject> getAllQuestions() {
		List<MultipleChoiceQuestionObject> questions = new ArrayList<MultipleChoiceQuestionObject>();

		Cursor cursor = database.query(
				QuestionSqlDatabase.MC_QUESTIONS,
				allColumns, 
				null, 
				null, 
				null, 
				null, 
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MultipleChoiceQuestionObject question = cursorToQuestion(cursor);
			questions.add(question);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return questions;
	}
//--------------------------------------------------------------------------------------------------------------
	private MultipleChoiceQuestionObject cursorToQuestion(Cursor cursor) {
		MultipleChoiceQuestionObject question = new MultipleChoiceQuestionObject(
				cursor.getString(0),
				cursor.getString(1),
				cursor.getString(2),
				cursor.getString(3),
				cursor.getString(4),
				cursor.getString(5),
				cursor.getString(6),
				cursor.getString(7),
				cursor.getString(8),
				cursor.getString(9),
				cursor.getInt(10)
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
