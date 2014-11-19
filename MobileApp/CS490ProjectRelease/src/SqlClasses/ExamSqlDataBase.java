package SqlClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExamSqlDataBase extends SQLiteOpenHelper{

	public static final String INSTRUCTOR_EXAMS = "exams";

	//For exams
	public static final String LOCAL_ID = "Local_id";
	public static final String EXAM_ID = "Exam_id";
	public static final String EXAM_NAME = "name";
	public static final String GRADES_RELEASED = "status";
	
	private static final String DATABASE_NAME = "instructor_exams.db";
	private static final int DATABASE_VERSION = 1;

	
	//String to pass to SQLITE that will create table
	private static final String EXAMS_TABLE_CREATE = "create table "
			+ INSTRUCTOR_EXAMS
			+ "(" 
			+ LOCAL_ID 			+ " integer primary key autoincrement, " 
			+ EXAM_ID 			+ " text not null unique," 
			+ EXAM_NAME 		+ " text not null,"
			+ GRADES_RELEASED 	+ " text not null"
			+");";
	
	//Initialize class 
	public ExamSqlDataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//Create table 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(EXAMS_TABLE_CREATE);
	}

	//When upgrade in place, destroy old table and populate new one
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ExamSqlDataBase.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + INSTRUCTOR_EXAMS);
		    onCreate(db);
	}
}
