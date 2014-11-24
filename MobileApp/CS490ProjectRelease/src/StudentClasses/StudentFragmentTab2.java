package StudentClasses;

import com.malan.cs490project.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.malan.cs490project.CurrentExamAdapter;

import ExamQuestionClasses.ExamObject;
import NetworkClasses.Login;
import SqlClasses.StudentExamSql;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class StudentFragmentTab2 extends Fragment {
	
	View view;
	LinearLayout layout;
	ListView listview;
	ExamObject single_exam;
	private StudentExamSql StudentExamsSql;
	private static List<ExamObject> list_exams = new ArrayList<ExamObject>();		
	public static Login session = new Login();	
	AdapterView.AdapterContextMenuInfo info;	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		
        View view = inflater.inflate(R.layout.fragment_student_tab2, container, false);
        
        StudentExamsSql = new StudentExamSql(getActivity());
        StudentExamsSql.open();
		
		list_exams = StudentExamsSql.getReleasedExams();

		layout = (LinearLayout) view;				
		listview = (ListView) view.findViewById(R.id.listView1);		    
		if(list_exams.size()!=0){
			listview.setAdapter(
					new CurrentExamAdapter(
							list_exams, 
							getActivity().getBaseContext()
						)
					);
		}
		else{
			TextView tv = new TextView(container.getContext());
            tv.setBackgroundResource(R.drawable.exam_background);
            tv.setPadding(15, 10, 10, 15);
            tv.setTextSize(15);
            tv.setText("No Past Exams");
            tv.setGravity(Gravity.CENTER);
            layout.addView(tv);        	
		}
		
		registerForContextMenu(listview);

        
        return view;

		/*
		
		view = inflater.inflate(R.layout.fragment_student_tab2, container, false);

    	Bundle args = getArguments();
    	Student_JSON = args.getString("STUDENT_JSON");
    	

    	
    	try 
    	{
			examArray = new JSONArray(Student_JSON);
			for(int i = 0; i < examArray.length(); i++)
			{
				if(examArray.getJSONObject(i).getString("examTaken").equals("False")){
					exams = new ExamObject();
					exams.setId(examArray.getJSONObject(i).getString("examID"));
					exams.setName(examArray.getJSONObject(i).getString("examName"));	
				}				
				list.add(exams);
			}
			
			layout = (LinearLayout) view;				
			listview = (ListView) view.findViewById(R.id.listView1);
			
			listview.setAdapter(new CurrentExamAdapter(list, getActivity().getBaseContext()));
			

		    
		    registerForContextMenu(listview);		    
		    listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		}
    	catch (JSONException e) 
		{
			Log.w("JSON PARSING ERROR", "Imported JSON String can't be converted to JSON object");
			e.printStackTrace();
		}
    	
		if(list.size()!=0){
			listview.setAdapter(new CurrentExamAdapter(list, getActivity().getBaseContext()));
		}
		else{
			TextView tv = new TextView(container.getContext());
            tv.setBackgroundResource(R.drawable.exam_background);
            tv.setPadding(15, 10, 10, 15);
            tv.setTextSize(15);
            tv.setText("No Past Exams");
            tv.setGravity(Gravity.CENTER);
            layout.addView(tv);        	
		}
    	
    	
		return view;
		*/		
	}
	
	@Override
	public void onStop() {
	    super.onStop();
	} 

	@Override
	public void onPause(){
		super.onPause();
	}
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
	}
}
