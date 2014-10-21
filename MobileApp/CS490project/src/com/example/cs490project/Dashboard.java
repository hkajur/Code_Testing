package com.example.cs490project;

/*
 * Dashboard.java
 * 
 * Dashboard activity that shows 
 * response from server upon successful 
 * login
 * */

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Dashboard extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		Intent intent = getIntent();
		String back = intent.getStringExtra(MainActivity.BACKLOGIN);
		
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
	    textView.setText(back);

	    // Set the text view as the activity layout
	    setContentView(textView);
		
	}
}
