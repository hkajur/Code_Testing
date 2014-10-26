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
	
/* THIS COULD WORK IF VIEW PAGER WASN'T BEING SUCH A CUNT
   public class StudentPanel extends FragmentActivity implements ActionBar.TabListener{
	
	private ViewPager viewPager;
    private TabsPagerAdapterStudent mAdapter;
    private ActionBar actionBar;
    
    private String[] tabs = { "Outstanding","Past Exams","Profile" };
    Intent intent = getIntent();
	String back = intent.getStringExtra(MainActivity.BACKLOGIN);
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapterStudent(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
 
        /**
         * on swiping the viewpager make respective tab selected
         * *
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
 
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
 
	*/

	
	/*PREVIOUSLY
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
