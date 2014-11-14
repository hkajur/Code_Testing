package com.malan.cs490project;

import java.util.ArrayList;

import ExamQuestionClasses.QuestionObject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CurrentQuestionAdapter extends BaseAdapter{

	private ArrayList<QuestionObject> data;
    Context c;
    
    public CurrentQuestionAdapter (ArrayList<QuestionObject> datain, Context cin){
        data = datain;
        c = cin;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
   
    /*
    public View getView(int position, View convertView, ViewGroup parent) {
    	View v = convertView;
    	if (v == null)
    	{
    		LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.simple_list_item, null);
         }
 
    	TextView questionName = (TextView)v.findViewById(R.id.QuestionsPlace);
 
    	if(data.get(position) != null){
    		QuestionObject msg = data.get(position);
    		questionName.setText(msg.);
    	}
    	                             
    	
        return v;
    }
*/	
}
