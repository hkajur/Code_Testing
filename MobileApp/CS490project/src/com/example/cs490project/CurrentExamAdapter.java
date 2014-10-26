package com.example.cs490project;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CurrentExamAdapter extends BaseAdapter{

	private ArrayList<ExamObject> _data;
    Context _c;
    
    public CurrentExamAdapter (ArrayList<ExamObject> data, Context c){
        _data = data;
        _c = c;
    }
   
    public int getCount() {
        return _data.size();
    }
    
    public Object getItem(int position) {
        return _data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
   
    public View getView(int position, View convertView, ViewGroup parent) {
    	View v = convertView;
    	if (v == null)
    	{
    		LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_current_exams, null);
         }
 
    	TextView examName = (TextView)v.findViewById(R.id.EXAMNAME);
    	TextView examStatus = (TextView)v.findViewById(R.id.status);
 
    	ExamObject msg = _data.get(position);
    	
    	examName.setText(msg.EXAM_NAME);
    	examStatus.setText(msg.EXAM_STATUS);                             
    	
        return v;
}
}
