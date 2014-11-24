package SqlClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentExamSqlDatabase extends SQLiteOpenHelper{

	public static final String CURRENT_STUDENT_EXAMS = "current_exams";
	public static final String PAST_STUDENT_EXAMS = "past_exams";

	//For exams
	public static final String LOCAL_ID = "Local_id";
	public static final String EXAM_ID = "Exam_id";
	public static final String EXAM_NAME = "name";
	public static final String GRADES = "grades";
	
	
	private static final String DATABASE_NAME = "student_exams.db";
	private static final int DATABASE_VERSION = 1;

	
	//String to pass to SQLITE that will create table
	private static final String CURRENT_EXAMS_TABLE_CREATE = "create table "
			+ CURRENT_STUDENT_EXAMS
			+ "(" 
			+ LOCAL_ID 	+ " integer primary key autoincrement, " 
			+ EXAM_ID 	+ " text not null unique," 
			+ EXAM_NAME + " text not null"
			+");";
	private static final String PAST_EXAMS_TABLE_CREATE = "create table "
			+ PAST_STUDENT_EXAMS
			+ "(" 
			+ LOCAL_ID 	+ " integer primary key autoincrement, " 
			+ EXAM_ID 	+ " text not null unique," 
			+ EXAM_NAME	+ " text not null,"
			+ GRADES	+ " text not null"
			+");";
	
	//Initialize class 
	public StudentExamSqlDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//Create table 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CURRENT_EXAMS_TABLE_CREATE);
		db.execSQL(PAST_EXAMS_TABLE_CREATE);
	}

	//When upgrade in place, destroy old table and populate new one
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(StudentExamSqlDatabase.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + CURRENT_STUDENT_EXAMS);
			db.execSQL("DROP TABLE IF EXISTS " + PAST_STUDENT_EXAMS);
		    onCreate(db);
	}
}
