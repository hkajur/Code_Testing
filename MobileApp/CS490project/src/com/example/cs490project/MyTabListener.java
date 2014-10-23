package com.example.cs490project;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.Tab;

public class MyTabListener implements ActionBar.TabListener {
	Fragment fragment;
	
	public MyTabListener(Fragment fragment) {
		this.fragment = fragment;
	}
		
	@Override
	public void onTabSelected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.fragment_container, fragment);
	}

	@Override
	public void onTabUnselected(android.app.ActionBar.Tab tab,FragmentTransaction ft) {
		ft.remove(fragment);		
	}

	@Override
	public void onTabReselected(android.app.ActionBar.Tab tab,FragmentTransaction ft) {
		// nothing done here
		
	}
}