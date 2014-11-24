package com.malan.cs490project;

/*
 * MainActivity.java
 * 
 * MainActivity activity that displays
 * input for ucid and password. Initial view 
 * when launching app
 * */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import ExamQuestionClasses.ExamObject;
import NetworkClasses.Streamer;
import NetworkClasses.Login;
import SqlClasses.InstructorExamSql;
import SqlClasses.StudentExamSql;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {
	public final static String STUDENT_JSON = "";
	public final static String INSTRUCTOR_JSON = "";
	public SharedPreferences settings;
	public AppProfile cred;
	private InstructorExamSql InstructorsExamsSql;
	private StudentExamSql StudentExamsSql;
	public static Login session = new Login();

	
	private EditText  	username=null;
	private EditText  	password=null;

//===============================================================================================================		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		cred = new AppProfile(MainActivity.this);
		
		InstructorsExamsSql = new InstructorExamSql(this);
		StudentExamsSql = new StudentExamSql(this);
		
		username = (EditText)findViewById(R.id.ucidText);
		password = (EditText)findViewById(R.id.passText);
		
//		username.setText("student2");
		username.setText("professor1");
//		password.setText("!student2");
		password.setText("!professor1");
		
		//DEBUGGING ONLY
		//Checks network connectivity		
		
		/*
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        	new DownloadWebpageTask().execute("http://web.njit.edu/~dm282/cs490/index.php");
        }
        else{
        	//Toast.makeText(getApplicationContext(), "Connectivity error. Check your connections.", Toast.LENGTH_SHORT).show();	
        }
		if(isConnectedToServer("http://web.njit.edu/~dm282/cs490/index.php", 1500))
		{
			Toast.makeText(getApplicationContext(), "Server Online", Toast.LENGTH_SHORT).show();
		}
		else
		{
			//Toast.makeText(getApplicationContext(), "Connectivity error. Check your connections.", Toast.LENGTH_SHORT).show();
		}
        */
	}
	
//===============================================================================================================	

	//USED TO TEST CONNECTION TO LOCAL SERVER
	public boolean isConnectedToServer(String url, int timeout) {
	    try{
	        URL myUrl = new URL(url);
	        URLConnection connection = myUrl.openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.connect();
	        return true;
	    } catch (Exception e) {
	        // Handle your exceptions
	        return false;
	    }
	}
	
//===============================================================================================================	

//	Performs validation of credentials and changes activity based on success of server response
	public void login(View view){
		String ucid = username.getText().toString();
		String pass = password.getText().toString();

		if(pass==null || ucid==null || pass=="" || ucid=="")
		{
	        Toast.makeText(getApplicationContext(), "Missing credentials", Toast.LENGTH_LONG).show();			
		}
		else
		{
			session = new Login(ucid,pass);			
	        AsyncTask<String, Void, String> response = new DownloadWebpageTask().execute(session.getURL(),session.getUcid(), session.getPassword(), session.getTag(), session.getToken());
		}
		
        //DEBUGGING ONLY
        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
		//textbox.setText(response.toString());
	}

	
//NETWORKING THREAD=========================================================================================================	
	
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> 
    {
    	@Override
    	protected String doInBackground(String... urls) 
    	{
    		String response = null;	         
			
			//PARAMETERS TO SEND
			Map<String, String> params = new HashMap<String, String>();
			params.put("ucid", urls[1]);
			params.put("password", urls[2]);	        
			params.put("tag", urls[3]);	        
			params.put("token", urls[4]);
			
			//TRY TO POST PARAMETERS TO SERVER AND GET RESPONSE
			try {
				Streamer.sendPostRequest(urls[0], params);
			    response = Streamer.readSingleLineRespone();
			} catch (IOException ex) {
				Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
			    ex.printStackTrace();
			}
			//CLOSE CONNECTION AND RETURN THE JSON REPONSE
			Streamer.disconnect();	        
			return response;
    	}
//    	###################################################################################################################
    	
		@Override
		protected void onPostExecute(String result) 
		{
			JSONObject response;
			
			
			//STUDENT: dm282 / test		
			//TEACHER: professor1 / !professor1
			
			try {
				response = new JSONObject(result);
				
				/*
				"userType" : "Student",
				"userName" : "John",
				"userID" : "1",
				"Backend_Login" : "Success",
				"exams"	: []
				*/
				if(response.get("Backend_Login").toString().equals("Success") && response.get("userType").toString().equals("Student"))
				{
					StudentExamsSql.open();
					ExamObject exams;
					JSONArray StudentsExamArr = new JSONArray(response.get("exams").toString());

					Log.i("MainActivity: ", "userID: "+ response.get("userID").toString());
					Log.i("MainActivity: ", "Type: "+ response.get("userType").toString());
					Log.i("STUDENT_JSON SAYS: ", response.get("exams").toString());
					
					for(int i = 0; i < StudentsExamArr.length(); i++)
					{
						exams = new ExamObject();
						//examId, examName, examStat
						exams = StudentExamsSql.createCurrentExam(
								StudentsExamArr.getJSONObject(i).getString("examID"),
								StudentsExamArr.getJSONObject(i).getString("examName"),
								StudentsExamArr.getJSONObject(i).getString("examTaken")
								);				
					}
					StudentExamsSql.close();
					
					cred.setID(response.get("userID").toString());
					
					Intent intent = new Intent(MainActivity.this,StudentPanel.class);
//					intent.putExtra("USER_ID", response.get("userID").toString());
					MainActivity.this.startActivity(intent);
				}//END STUDENT
				
				/*
				"userType" :  "Teacher", 
				"userName" : "joe",
				"userID" : "11",
				"Backend_Login" : "Success",
				"exams" : []
				*/
				else if(response.get("Backend_Login").toString().equals("Success") && response.get("userType").toString().equals("Teacher"))
				{
					InstructorsExamsSql.open();
					ExamObject exams;
					JSONArray InstructorsExamArr = new JSONArray(response.get("exams").toString());

					Log.i("MainActivity: ", "userID: "+ response.get("userID").toString());
					Log.i("MainActivity: ", "Type: "+ response.get("userType").toString());
					Log.i("INSTRUCTOR_JSON SAYS: ", response.get("exams").toString());
					
					for(int i = 0; i < InstructorsExamArr.length(); i++)
					{
						exams = new ExamObject();
						if(InstructorsExamArr.getJSONObject(i).getString("examReleased").equals("True")){
							//examId, examName, examStat
							exams = InstructorsExamsSql.createExam(
									InstructorsExamArr.getJSONObject(i).getString("examID"),
									InstructorsExamArr.getJSONObject(i).getString("examName"),
									"Released"
									);
						}
						else{
							exams = InstructorsExamsSql.createExam(
									InstructorsExamArr.getJSONObject(i).getString("examID"),
									InstructorsExamArr.getJSONObject(i).getString("examName"),
									"Unreleased"
									);
						}
					}
					InstructorsExamsSql.close();
					Intent intent = new Intent(MainActivity.this,InstructorPanel.class);
					MainActivity.this.startActivity(intent);
				}//END INSTRUCTOR
				else
					Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Server has not responded. Try again.", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}					
		}		
    }//END ASYNC CLASS
}