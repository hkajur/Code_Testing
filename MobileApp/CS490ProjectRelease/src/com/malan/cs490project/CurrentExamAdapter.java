package com.malan.cs490project;

import java.util.List;

import ExamQuestionClasses.ExamObject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CurrentExamAdapter extends BaseAdapter{

	private List<ExamObject> data;
    Context c;
    
    public CurrentExamAdapter (List<ExamObject> list_exams, Context c_in){
        data = list_exams;
        c = c_in;
    }
   
    public int getCount() {
        return data.size();
    }
    
    public Object getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
   
    public View getView(int position, View convertView, ViewGroup parent) {
    	View v = convertView;
    	if (v == null)
    	{
    		LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_current_exams, null);
         }
 
    	TextView examName = (TextView)v.findViewById(R.id.EXAMNAME);
    	TextView examStatus = (TextView)v.findViewById(R.id.status);
 
    	if(data.get(position) != null){
    		ExamObject msg = data.get(position);
    		examName.setText(msg.EXAM_NAME);
        	examStatus.setText(msg.EXAM_STATUS);
    	}
    	                             
    	
        return v;
    }
}
