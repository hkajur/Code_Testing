package com.example.cs490project;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentTab1 extends Fragment {
	

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.tab, container, false);
		TextView textview = (TextView) view.findViewById(R.id.tabtextview);
		
//		Intent intent = getIntent();
//		String back = intent.getStringExtra(MainActivity.BACKLOGIN);
			
		
//		textview.setText(back);
		textview.setText("back");
		return view;
	}
}