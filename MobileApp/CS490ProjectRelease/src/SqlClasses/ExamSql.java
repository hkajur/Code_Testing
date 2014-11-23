package SqlClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ExamQuestionClasses.ExamObject;
import NetworkClasses.Login;
import NetworkClasses.Streamer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class ExamSql{

	private String response;
	private SQLiteDatabase database;
	private ExamSqlDataBase dbHelper;
	private String[] allColumns = { 
			ExamSqlDataBase.EXAM_ID,
			ExamSqlDataBase.EXAM_NAME,
			ExamSqlDataBase.GRADES_RELEASED 			
	};
	private Login session = new Login();
//--------------------------------------------------------------------------------------------------------------	
	public ExamSql(Context context) {
		dbHelper = new ExamSqlDataBase(context);
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
	public ExamObject createExam(String examId, String examName, String examStat) {
		ContentValues values = new ContentValues();

		Cursor cursor = database.query(
				ExamSqlDataBase.INSTRUCTOR_EXAMS,
				allColumns,
				ExamSqlDataBase.EXAM_ID + " = " + examId,
				null,
				null,
				null,
				null
				);
		
		if(cursor.moveToFirst()){
		    return null;
		}else{
			values.put(ExamSqlDataBase.EXAM_ID, examId);
			values.put(ExamSqlDataBase.EXAM_NAME, examName);
			values.put(ExamSqlDataBase.GRADES_RELEASED, examStat);
			long insertId = database.insert(
					ExamSqlDataBase.INSTRUCTOR_EXAMS, 
					null,
					values
					);
			cursor = database.query(
					ExamSqlDataBase.INSTRUCTOR_EXAMS,
			        allColumns, 
			        ExamSqlDataBase.LOCAL_ID + " = " + insertId, 
			        null,
			        null, 
			        null, 
			        null
					);
			cursor.moveToFirst();
			ExamObject newExam = cursorToExam(cursor);
			cursor.close();
			return newExam;
		}
	}
//--------------------------------------------------------------------------------------------------------------
	public String[] getSingleExam(ExamObject exam){
		String[] temp = {exam.getId(), exam.getName()};
		return temp;
	}	
//--------------------------------------------------------------------------------------------------------------	
	public void releaseExam(ExamObject exam){
		String id = exam.getId();
		String response=null;
		ContentValues values = new ContentValues();
		
		exam.setStatus("Released");
		
		/*
		values.put(ExamSqlDataBase.GRADES_RELEASED, "Released");
		database.update(
				ExamSqlDataBase.INSTRUCTOR_EXAMS, 
				values, 
				ExamSqlDataBase.EXAM_ID + " = " + exam.getId(), 
				null);
			*/
		Log.i("ExamSqlData", "Exam " + exam.getName() + " released");
		
		// ENABLE ONCE SERVER SET UP
		AsyncTask<String, Void, String> response_thread = new NetworkThread().execute(id,"professor1","ReleaseExam");		
		
		try {
			response = response_thread.get();
		} catch (InterruptedException | ExecutionException e) {
			Log.w("ExamSqlData", "Interrupted connection while releasing exam");
			e.printStackTrace();
		}
		
		//If exam deleted successfully online, delete on mobile version
		if(response.equals("Success")){
			Log.i("ExamSqlData", "Exam " + exam.getName() + " released");
			values.put(ExamSqlDataBase.GRADES_RELEASED, "Released");
			database.update(
				ExamSqlDataBase.INSTRUCTOR_EXAMS, 
				values, 
				ExamSqlDataBase.EXAM_ID + " = " + exam.getId(), 
				null);
		}
		else{
			Log.i("ExamSqlData: ","Error releasing exam with exam_id: " + id);
		}	
		
	}
//--------------------------------------------------------------------------------------------------------------	
	public void unreleaseExam(ExamObject exam){
		ContentValues values = new ContentValues();

		exam.setStatus("Unreleased");
		values.put(ExamSqlDataBase.GRADES_RELEASED, "Unreleased");
		database.update(
				ExamSqlDataBase.INSTRUCTOR_EXAMS, 
				values, 
				ExamSqlDataBase.EXAM_ID + " = " + exam.getId(), 
				null);
		//ADD FUNCTIONALITY TO RELEASE ON THE SERVER		
		Log.i("ExamSqlData", "Exam " + exam.getName() + " unreleased");
		
		
		/* ENABLE ONCE SERVER SET UP
		AsyncTask<String, Void, String> response_thread = new NetworkThread().execute(id,"professor1","UnreleaseExam");
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
			Log.i("ExamSqlData", "Exam " + exam.getName() + " unreleased");
			values.put(ExamSqlDataBase.GRADES_RELEASED, "Unreleased");
			database.update(
				ExamSqlDataBase.INSTRUCTOR_EXAMS, 
				values, 
				ExamSqlDataBase.EXAM_ID + " = " + exam.getId(), 
				null);
		}
		else{
			Log.i("ExamSqlData: ","Error unreleasing exam with exam_id: " + id);
		}	
		*/	

		
		}
//--------------------------------------------------------------------------------------------------------------
	public void deleteExam(ExamObject exam) {
		String id = exam.getId();
		String response=null;
		//Set Params

		/* ENABLE ONCE SERVER SET UP
		AsyncTask<String, Void, String> response_thread = new NetworkThread().execute(id,"professor1","DeleteExam");
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
		Log.i("ExamSqlData: ","Exam deleted with id: " + id);
		database.delete(
				ExamSqlDataBase.INSTRUCTOR_EXAMS, 
				ExamSqlDataBase.EXAM_ID + " = " + id, 
				null);	        	

	}
//--------------------------------------------------------------------------------------------------------------
	public List<ExamObject> getAllExams() {
		List<ExamObject> comments = new ArrayList<ExamObject>();

		Cursor cursor = database.query(
				ExamSqlDataBase.INSTRUCTOR_EXAMS,
				allColumns, 
				null, 
				null, 
				null, 
				null, 
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ExamObject comment = cursorToExam(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return comments;
	}
//--------------------------------------------------------------------------------------------------------------
	private ExamObject cursorToExam(Cursor cursor) {
		ExamObject exam = new ExamObject();
		exam.setId(cursor.getString(0));
		exam.setName(cursor.getString(1));
		exam.setStatus(cursor.getString(2));
		return exam;
	}
	
//################################################################################################	
	
	private class NetworkThread extends AsyncTask<String, Void, String> {	
		@Override
		protected String doInBackground(String... urls) 
		{
			try {
				return postUrl(urls[0],urls[1],urls[2]);
			}
			catch (IOException e){
				return "Cannot connect. Server is not responding.";
			}
		}

		private String postUrl(String exam_id, String user_id, String tag) throws IOException {	         
			String response =""; 
			
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("token", session.getToken());
			params.put("tag", tag);
			params.put("examID", exam_id);
			params.put("userID", user_id);
			
			
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
