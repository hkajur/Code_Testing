package com.example.cs490project;

/*
 * MainActivity.java
 * 
 * MainActivity activity that displays
 * input for ucid and password. Initial view 
 * when launching app
 * */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;


public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.cs490project.MESSAGE";
	
	private EditText  username=null;
	private EditText  password=null;
	private Button login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText)findViewById(R.id.ucidText);
		password = (EditText)findViewById(R.id.passText);
		login = (Button)findViewById(R.id.button1);
	}

//	Performs validation of credentials and changes activity based on success of server response
	public void login(View view){
		String ucid = username.getText().toString();
		String pass = password.getText().toString();
		
		loginFunctions session = new loginFunctions(ucid,pass);		
//		JSONObject jsonObject = session.loginUser();		
		
//		if(jsonObject != null){
		if(session.getUcid().equals("admin") && session.getPassword().equals("admin")){
			Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(this, Dashboard.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
//			String jsonString = jsonObject.toString();
//			intent.putExtra(EXTRA_MESSAGE, jsonString);
			startActivity(intent);
			finish();
		}
		else{
			Toast.makeText(getApplicationContext(), "Incorrect UCID or password",Toast.LENGTH_SHORT).show();
		}
		
//	   	Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
//	   	Toast.makeText(getApplicationContext(), "Incorrect UCID or password",Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
      // 	Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}