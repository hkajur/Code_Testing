package com.malan.cs490project;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppProfile extends Application{

	public static final String PREFS_NAME = "AppProfile";
	public static final String PREF_ID = "user_ID";
	private SharedPreferences settings;

	public AppProfile(Context context){
		this.settings = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
	}
	
	public String getValue(String value) {
		return this.settings.getString(value, "");
	}
	
	public void setID(String id){
		settings.edit().putString(PREF_ID, id).commit();
	}
	
	public boolean isID(String key){
		if(settings.contains(key))
			return true;
		else
			return false;
	}
}

