package ExamQuestionClasses;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ExamSqlData{

	private SQLiteDatabase database;
	private ExamSqlDataBase dbHelper;
	private String[] allColumns = { 
			ExamSqlDataBase.EXAM_ID,
			ExamSqlDataBase.EXAM_NAME,
			ExamSqlDataBase.GRADES_RELEASED 			
	};
	
	public ExamSqlData(Context context) {
		dbHelper = new ExamSqlDataBase(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

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

	public String[] getSingleExam(ExamObject exam){
		exam.getId();
		exam.getName();
		return allColumns;
	}	
	
	public void deleteExam(ExamObject exam) {
		String id = exam.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(
				ExamSqlDataBase.INSTRUCTOR_EXAMS, 
				ExamSqlDataBase.EXAM_ID + " = " + id, 
				null);
	}

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

	private ExamObject cursorToExam(Cursor cursor) {
		ExamObject exam = new ExamObject();
		exam.setId(cursor.getString(0));
		exam.setName(cursor.getString(1));
		exam.setStatus(cursor.getString(2));
		return exam;
	}		 
}
