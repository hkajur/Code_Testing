package com.example.cs490project;

import StudentClasses.StudentFragmentTab1;
import StudentClasses.StudentFragmentTab2;
import StudentClasses.StudentFragmentTab3;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class StudentPanel extends ActionBarActivity{

	ActionBar.Tab tab1, tab2, tab3;
	Fragment fragmentTabLEFT = new StudentFragmentTab1();
	Fragment fragmentTabMIDDLE = new StudentFragmentTab2();
	Fragment fragmentTabRIGHT = new StudentFragmentTab3();

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        		
        Intent intent = getIntent();

		Bundle argsFrag1 = new Bundle();
        argsFrag1.putString("STUDENT_JSON", intent.getStringExtra("STUDENT_JSON"));
        argsFrag1.putString("USER_ID", intent.getStringExtra("USER_ID"));
        
        
        Bundle argsFrag2 = new Bundle();
        argsFrag2.putString("STUDENT_JSON", intent.getStringExtra("STUDENT_JSON"));

        Bundle argsFrag3 = new Bundle();
        argsFrag3.putString("USER_NAME", intent.getStringExtra("USER_NAME"));
        argsFrag3.putString("USER_ID", intent.getStringExtra("USER_ID"));

        fragmentTabLEFT.setArguments(argsFrag1);
        fragmentTabMIDDLE.setArguments(argsFrag2);
        fragmentTabRIGHT.setArguments(argsFrag3);
        
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
}
