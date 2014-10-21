package com.example.cs490project;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class StudentPanel extends ActionBarActivity{
	ActionBar.Tab tab1, tab2, tab3;
	Fragment fragmentTabLEFT = new FragmentTab1();
	Fragment fragmentTabMIDDLE = new FragmentTab2();
	Fragment fragmentTabRIGHT = new FragmentTab3();

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        
        Intent intent = getIntent();
		String back = intent.getStringExtra(MainActivity.BACKLOGIN);
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        tab1 = actionBar.newTab().setText("Outstanding");
        tab2 = actionBar.newTab().setText("Past Exams");
        tab3 = actionBar.newTab().setText("Profile");
        
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
	    textView.setText("Student\n" + back);

	    // Set the text view as the activity layout
	    setContentView(textView);
		
	}
	*/

}
