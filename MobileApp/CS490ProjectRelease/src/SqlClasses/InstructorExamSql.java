package SqlClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.malan.cs490project.AppProfile;

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

public class InstructorExamSql{

	private SQLiteDatabase database;
	private InstructorExamSqlDatabase dbHelper;
	private String[] allColumns = { 
			InstructorExamSqlDatabase.EXAM_ID,
			InstructorExamSqlDatabase.EXAM_NAME,
			InstructorExamSqlDatabase.GRADES_RELEASED 			
	};
	private Login session = new Login();
//--------------------------------------------------------------------------------------------------------------	
	public InstructorExamSql(Context context) {
		dbHelper = new InstructorExamSqlDatabase(context);
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
		
		//TODO ADD NETWORK FUNCTIONALITY HERE
		
		ContentValues values = new ContentValues();

		Cursor cursor = database.query(
				InstructorExamSqlDatabase.INSTRUCTOR_EXAMS,
				allColumns,
				InstructorExamSqlDatabase.EXAM_ID + " = " + examId,
				null,
				null,
				null,
				null
				);
		
		if(cursor.moveToFirst()){
		    return null;
		}else{
			values.put(InstructorExamSqlDatabase.EXAM_ID, examId);
			values.put(InstructorExamSqlDatabase.EXAM_NAME, examName);
			values.put(InstructorExamSqlDatabase.GRADES_RELEASED, examStat);
			long insertId = database.insert(
					InstructorExamSqlDatabase.INSTRUCTOR_EXAMS, 
					null,
					values
					);
			cursor = database.query(
					InstructorExamSqlDatabase.INSTRUCTOR_EXAMS,
			        allColumns, 
			        InstructorExamSqlDatabase.LOCAL_ID + " = " + insertId, 
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
	public void releaseExam(Context context, ExamObject exam){
		AppProfile creds = new AppProfile(context);
		String userID = creds.getValue(AppProfile.PREF_ID);
		JSONObject temp;
		
		String id = exam.getId();
		String response="";
		ContentValues values = new ContentValues();
		
		// ENABLE ONCE SERVER SET UP
		AsyncTask<String, Void, String> response_thread = new NetworkThread().execute(
				exam.getId(),
				userID,
				"ReleaseExam");		
		
		try {
			response = response_thread.get();
			
			temp = new JSONObject(response);
			Log.i("ExamSqlData", response);
			
			if(temp.getString("Update").equals("Success")){
				Log.i("ExamSqlData", "Exam " + exam.getName() + " released");
				values.put(InstructorExamSqlDatabase.GRADES_RELEASED, "Released");
				database.update(
					InstructorExamSqlDatabase.INSTRUCTOR_EXAMS, 
					values, 
					InstructorExamSqlDatabase.EXAM_ID + " = " + exam.getId(), 
					null);
				exam.setStatus("Released");
			}
			else{
				Log.i("ExamSqlData: ","Error releasing exam with exam_id: " + id);
			}	
			
		} catch (InterruptedException | ExecutionException | JSONException e) {
			Log.w("ExamSqlData", "Interrupted connection while releasing exam");
			e.printStackTrace();
		} 
		//If exam deleted successfully online, delete on mobile version
		
	}
//--------------------------------------------------------------------------------------------------------------
	public void deleteExam(Context context, ExamObject exam) {
		String id = exam.getId();
		String response="";
		
		AppProfile creds = new AppProfile(context);
		String userID = creds.getValue(AppProfile.PREF_ID);

		
		AsyncTask<String, Void, String> response_thread = new NetworkThread().execute(exam.getId(),userID,"DeleteExam");
		try {
			response = response_thread.get();
		} catch (InterruptedException | ExecutionException e) {
			Log.w("ExamSqlData", "Interrupted connection while deleting exam");
			e.printStackTrace();
		}
		
		//If exam deleted successfully online, delete on mobile version
		if(response.equals("Success")){
			Log.i("ExamSqlData: ","Exam deleted with id: " + id);
			database.delete(
					InstructorExamSqlDatabase.INSTRUCTOR_EXAMS, 
					InstructorExamSqlDatabase.EXAM_ID + " = " + id, 
					null);	        	
		}
		else{
			Log.i("ExamSqlData: ","Error deleting exam with exam_id: " + id);
		}	
	}
//--------------------------------------------------------------------------------------------------------------
	public List<ExamObject> getAllExams() {
		List<ExamObject> comments = new ArrayList<ExamObject>();

		Cursor cursor = database.query(
				InstructorExamSqlDatabase.INSTRUCTOR_EXAMS,
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
			String response =""; 
			
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("token", session.getToken());
			params.put("examID", urls[0]);
			params.put("userID", urls[1]);
			params.put("tag", urls[2]);
			
			
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
		}
	}//END ASYNC CLASS	
}
