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
		String jsonobject = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
	    textView.setText(jsonobject);

	    // Set the text view as the activity layout
	    setContentView(textView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
