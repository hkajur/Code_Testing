package com.example.cs490project;

import InstructorClasses.InstructorFragmentTab1;
import InstructorClasses.InstructorFragmentTab2;
import InstructorClasses.InstructorFragmentTab3;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class InstructorPanel extends ActionBarActivity{
		ActionBar.Tab tab1, tab2, tab3;
		Fragment fragmentTabLEFT = new InstructorFragmentTab1();
		Fragment fragmentTabMIDDLE = new InstructorFragmentTab2();
		Fragment fragmentTabRIGHT = new InstructorFragmentTab3();

		@Override
		 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_instructor);
	        
	        Intent intent = getIntent();
			Bundle argsFrag1 = new Bundle();
            argsFrag1.putString("INSTRUCTOR_JSON", intent.getStringExtra("INSTRUCTOR_JSON"));
                        
            Bundle argsFrag2 = new Bundle();
            argsFrag2.putString("USER_NAME", intent.getStringExtra("USER_NAME"));
            argsFrag2.putString("USER_ID", intent.getStringExtra("USER_ID"));
            
            
            fragmentTabLEFT.setArguments(argsFrag1);
            fragmentTabMIDDLE.setArguments(argsFrag2);
            fragmentTabRIGHT.setArguments(argsFrag2);
        
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            
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
	}
