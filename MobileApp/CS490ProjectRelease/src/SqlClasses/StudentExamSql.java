package SqlClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class StudentExamSql {
	private String response;
	private SQLiteDatabase database;
	private StudentExamSqlDatabase dbHelper;
	private String[] allCurrentColumns = { 
			StudentExamSqlDatabase.EXAM_ID,
			StudentExamSqlDatabase.EXAM_NAME, 			
	};
	private String[] allPastColumns = { 
			StudentExamSqlDatabase.EXAM_ID,
			StudentExamSqlDatabase.EXAM_NAME, 			
			StudentExamSqlDatabase.GRADES, 			
	};
	private Login session = new Login();
//--------------------------------------------------------------------------------------------------------------	
	public StudentExamSql(Context context) {
		dbHelper = new StudentExamSqlDatabase(context);
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
	@SuppressWarnings("resource")
	public ExamObject createCurrentExam(String examId, String examName, String examStat) {
		ContentValues current_values = new ContentValues();
		
		Cursor current_cursor = database.query(
				StudentExamSqlDatabase.CURRENT_STUDENT_EXAMS,
				allCurrentColumns,
				StudentExamSqlDatabase.EXAM_ID + " = " + examId,
				null,
				null,
				null,
				null
				);
		
		
		if(current_cursor.moveToFirst()){
			return null;
		}
		//Student has NOT taken exam
		else if(examStat.equals("False")){
			current_values.put(StudentExamSqlDatabase.EXAM_ID, examId);
			current_values.put(StudentExamSqlDatabase.EXAM_NAME, examName);			
			long insertId = database.insert(
					StudentExamSqlDatabase.CURRENT_STUDENT_EXAMS, 
					null,
					current_values
					);
			current_cursor = database.query(
					StudentExamSqlDatabase.CURRENT_STUDENT_EXAMS,
			        allCurrentColumns, 
			        StudentExamSqlDatabase.LOCAL_ID + " = " + insertId, 
			        null,
			        null, 
			        null, 
			        null
					);
			current_cursor.moveToFirst();
			ExamObject newExam = cursorToExam(current_cursor,false);
			current_cursor.close();
			return newExam;
		}		
		return null;
	}
//--------------------------------------------------------------------------------------------------------------
	@SuppressWarnings("resource")
	public ExamObject createPastExam(String examId, String examName, String examStat, String grade) {
		ContentValues past_values = new ContentValues();

		Cursor past_cursor = database.query(
				StudentExamSqlDatabase.PAST_STUDENT_EXAMS,
				allPastColumns,
				StudentExamSqlDatabase.EXAM_ID + " = " + examId,
				null,
				null,
				null,
				null
				);

		
		if(past_cursor.moveToFirst()){
		    return null;
		}
		//Student HAS taken exam
		else if(examStat.equals("True")){
			past_values.put(StudentExamSqlDatabase.EXAM_ID, examId);
			past_values.put(StudentExamSqlDatabase.EXAM_NAME, examName);
			past_values.put(StudentExamSqlDatabase.GRADES, grade);
			long insertId = database.insert(
					StudentExamSqlDatabase.PAST_STUDENT_EXAMS, 
					null,
					past_values
					);
			past_cursor = database.query(
					StudentExamSqlDatabase.PAST_STUDENT_EXAMS,
					allPastColumns, 
			        StudentExamSqlDatabase.LOCAL_ID + " = " + insertId, 
			        null,
			        null, 
			        null, 
			        null
					);
			past_cursor.moveToFirst();
			ExamObject newExam = cursorToExam(past_cursor,true);
			past_cursor.close();
			return newExam;			
		}
		return null;
	}
//--------------------------------------------------------------------------------------------------------------
	public String[] getSingleExam(ExamObject exam){
		String[] temp = {exam.getId(), exam.getName()};
		return temp;
	}	
//--------------------------------------------------------------------------------------------------------------
	public List<ExamObject> getReleasedExams() {
		List<ExamObject> exams = new ArrayList<ExamObject>();
		
		Cursor cursor = database.query(
				StudentExamSqlDatabase.PAST_STUDENT_EXAMS,
				allPastColumns, 
				null, 
				null, 
				null, 
				null, 
				null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ExamObject exam = cursorToExam(cursor,true);
			exams.add(exam);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return exams;
	}
//--------------------------------------------------------------------------------------------------------------
	public List<ExamObject> getCurrentExams() {
		List<ExamObject> exams = new ArrayList<ExamObject>();

		Cursor cursor = database.query(
				StudentExamSqlDatabase.CURRENT_STUDENT_EXAMS,
				allCurrentColumns, 
				null, 
				null, 
				null, 
				null, 
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ExamObject exam = cursorToExam(cursor, false);
			exams.add(exam);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return exams;
	}
//--------------------------------------------------------------------------------------------------------------
	private ExamObject cursorToExam(Cursor cursor, boolean past) {
		ExamObject exam = new ExamObject();
		exam.setId(cursor.getString(0));
		exam.setName(cursor.getString(1));
		Log.i("StudentExamSql", "Cursor:" + cursor.getString(0) + " " + cursor.getString(1));
		if(past)
			exam.setGrade(cursor.getString(2));
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
