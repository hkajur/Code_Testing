package com.example.cs490project;

import InstructorClasses.InstructorFragmentTab1;
import InstructorClasses.InstructorFragmentTab2;
import InstructorClasses.InstructorFragmentTab3;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class InstructorPanel extends ActionBarActivity{
		ActionBar.Tab tab1, tab2, tab3;
		Fragment fragmentTabLEFT = new InstructorFragmentTab1();
		Fragment fragmentTabMIDDLE = new InstructorFragmentTab2();
		Fragment fragmentTabRIGHT = new InstructorFragmentTab3();

		@Override
		 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_instructor);
	        	        
//	        Intent intent = getIntent();
//			String back = intent.getStringExtra(MainActivity.BACKLOGIN);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        
	        tab1 = actionBar.newTab().setText("Outstanding");
	        tab2 = actionBar.newTab().setText("Construct");
	        tab3 = actionBar.newTab().setText("Add Questions");
	        
	        tab1.setTabListener(new MyTabListener(fragmentTabLEFT));
	        tab2.setTabListener(new MyTabListener(fragmentTabMIDDLE));
	        tab3.setTabListener(new MyTabListener(fragmentTabRIGHT));
	        
	        actionBar.addTab(tab1);
	        actionBar.addTab(tab2);
	        actionBar.addTab(tab3);
	    }
		
		/*
		@Override
			protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_dashboard);
			Intent intent = getIntent();
			String back = intent.getStringExtra(MainActivity.BACKLOGIN);
		
	    	TextView textView = new TextView(this);
	    	textView.setTextSize(40);
	    	textView.setText("Instructor\n" + back);
	
	    	// Set the text view as the activity layout
	    	setContentView(textView);
		
	}
		*/

	}
