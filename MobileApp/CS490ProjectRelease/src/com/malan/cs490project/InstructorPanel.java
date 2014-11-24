package com.malan.cs490project;


import InstructorClasses.ViewPagerAdapterInstructor;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class InstructorPanel extends FragmentActivity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);        		
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_user_panel);
 
        // Locate the viewpager in activity_main.xml
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);        
        
        // Set the ViewPagerAdapter into ViewPager
        viewPager.setAdapter(new ViewPagerAdapterInstructor(getSupportFragmentManager()));
    }
}