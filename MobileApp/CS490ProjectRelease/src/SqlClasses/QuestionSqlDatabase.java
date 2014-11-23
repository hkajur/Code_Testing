package SqlClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuestionSqlDatabase extends SQLiteOpenHelper{

	public static final String DISPLAY_QUESTIONS = "DISPLAYquestions";
	public static final String MC_QUESTIONS = "MCquestions";
	public static final String TF_QUESTIONS = "TFquestions";
	public static final String SHORT_QUESTIONS = "SHORTquestions";
	public static final String PROGRAM_QUESTIONS = "PROGRAMquestions";

	//For questions
	public static final String LOCAL_ID = "Local_id";
	public static final String QUESTION_ID = "Question_id";
	public static final String QUESTION_TYPE = "Question_type";
	public static final String QUESTION = "Question";

	//For Multiple Choice
	public static final String CORRECT_ANSWER = "correct_ans";
	public static final String CORRECT_REASON = "correct_res";
	public static final String WRONG_ANS1 = "wrong_ans1";
	public static final String WRONG_RES1 = "wrong_res1";
	public static final String WRONG_ANS2 = "wrong_ans2";
	public static final String WRONG_RES2 = "wrong_res2";
	public static final String WRONG_ANS3 = "wrong_ans3";
	public static final String WRONG_RES3 = "wrong_res3";
	public static final String POINTS = "points";

	private static final String DATABASE_NAME = "questions.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DISPLAY_QUESTIONS_TABLE_CREATE = "create table "
			+ DISPLAY_QUESTIONS
			+ "(" 
			+ LOCAL_ID 			+ " integer primary key autoincrement, " 
			+ QUESTION_ID 		+ " text not null unique," 
			+ QUESTION_TYPE		+ " text not null,"
			+ QUESTION 			+ " text not null"
			+ ");";
	private static final String MC_QUESTIONS_TABLE_CREATE = "create table "
			+ MC_QUESTIONS
			+ "(" 
			+ LOCAL_ID 			+ " integer primary key autoincrement, " 
			+ QUESTION_ID 		+ " text not null unique," 
			+ QUESTION 			+ " text not null"
			+ ");";
	private static final String TF_QUESTIONS_TABLE_CREATE = "create table "
			+ TF_QUESTIONS
			+ "(" 
			+ LOCAL_ID 			+ " integer primary key autoincrement, " 
			+ QUESTION_ID 		+ " text not null unique," 
			+ QUESTION 			+ " text not null,"
			+ CORRECT_ANSWER 	+ " text not null,"
			+ CORRECT_REASON 	+ " text not null,"
			+ WRONG_ANS1 		+ " text not null,"
			+ WRONG_RES1  		+ " text not null,"
			+ POINTS	  		+ " integer not null"
			+ ");";
	private static final String SHORT_QUESTIONS_TABLE_CREATE = "create table "
			+ SHORT_QUESTIONS
			+ "(" 
			+ LOCAL_ID 			+ " integer primary key autoincrement, " 
			+ QUESTION_ID 		+ " text not null unique," 
			+ QUESTION 			+ " text not null,"
			+ CORRECT_ANSWER 	+ " text not null,"
			+ POINTS	  		+ " integer not null"			
			+ ");";	
	private static final String PROGRAM_QUESTIONS_TABLE_CREATE = "create table "
			+ PROGRAM_QUESTIONS
			+ "(" 
			+ LOCAL_ID 			+ " integer primary key autoincrement, " 
			+ QUESTION_ID 		+ " text not null unique," 
			+ QUESTION 			+ " text not null,"
			+ CORRECT_ANSWER 	+ " text not null,"
			+ POINTS	  		+ " integer not null"			
			+ ");";	
	//Initialize class 
	public QuestionSqlDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//Create table 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DISPLAY_QUESTIONS_TABLE_CREATE);
		db.execSQL(MC_QUESTIONS_TABLE_CREATE);
		db.execSQL(TF_QUESTIONS_TABLE_CREATE);
		db.execSQL(SHORT_QUESTIONS_TABLE_CREATE);
		db.execSQL(PROGRAM_QUESTIONS_TABLE_CREATE);
	}

	//When upgrade in place, destroy old table and populate new one
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(QuestionSqlDatabase.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");

			db.execSQL("DROP TABLE IF EXISTS " + DISPLAY_QUESTIONS);
			db.execSQL("DROP TABLE IF EXISTS " + MC_QUESTIONS);
			db.execSQL("DROP TABLE IF EXISTS " + TF_QUESTIONS);
			db.execSQL("DROP TABLE IF EXISTS " + SHORT_QUESTIONS);
		    db.execSQL("DROP TABLE IF EXISTS " + PROGRAM_QUESTIONS);
		    onCreate(db);
	}
}
