package InstructorClasses;

/*
 * InstructorFragmentTab1
 * 	- Shows all exams for instructor
 * 	- Allows user to 
 * 			* Release exam
 * 			* Delete exam
 * 			* View exam stats
 *  - OnLogin/OnSwitchTabs:
 *  		* Retrieve all exams from backend
 *  - OnDelete/OnRelease:
 *  		* Post status of exam to backend and remove from list
 *  - OnViewStats:
 *  		* Start activity with statistics about exam
 * */

import java.util.ArrayList;
import java.util.List;

import com.malan.cs490project.CurrentExamAdapter;
import com.malan.cs490project.R;

import ExamQuestionClasses.ExamObject;
import NetworkClasses.Login;
import SqlClasses.InstructorExamSql;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


/* TODO
 *  - Add functionality for exam stats
 * */

public class InstructorFragmentTab1 extends Fragment {
	
	View view;
	LinearLayout layout;
	ListView listview;
	ExamObject single_exam;
	private InstructorExamSql InstructorsExamsSql;
	private static List<ExamObject> list_exams = new ArrayList<ExamObject>();		
	public static Login session = new Login();	
	AdapterView.AdapterContextMenuInfo info;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{			

		view = inflater.inflate(R.layout.fragment_instructor_tab1, container, false);
		
		InstructorsExamsSql = new InstructorExamSql(getActivity());
		InstructorsExamsSql.open();
		
		list_exams = InstructorsExamsSql.getAllExams();

		
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
            tv.setText("No Outstanding Exams");
            tv.setGravity(Gravity.CENTER);
            layout.addView(tv);        	
		}
		
		registerForContextMenu(listview);		    
		return view;
	}
	
	@Override
	public void onResume(){
		InstructorsExamsSql.open();
		
		list_exams = InstructorsExamsSql.getAllExams();

		
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
	    super.onResume();
	}
	
	@Override
	public void onStop()
	{
		InstructorsExamsSql.close();
	    super.onStop();	 
	}
	@Override
	public void onPause() {
		InstructorsExamsSql.close();
	    super.onPause();
	  }
	
	@Override
	public void onDestroyView(){
		InstructorsExamsSql.close();
		super.onDestroyView();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);      
	                    
		info = (AdapterContextMenuInfo) menuInfo;
		
		single_exam = (ExamObject) listview.getItemAtPosition(info.position);
		
		menu.setHeaderTitle(list_exams.get(info.position).getName());
		if(single_exam.getStatus().equals("Unreleased")){
			menu.add(Menu.NONE, v.getId(), 0, "Release");
			menu.add(Menu.NONE, v.getId(), 0, "Delete");                  
			menu.add(Menu.NONE, v.getId(), 0, "Exam Stats");
		}
		else{
			menu.add(Menu.NONE, v.getId(), 0, "Unrelease");
			menu.add(Menu.NONE, v.getId(), 0, "Delete");                  
			menu.add(Menu.NONE, v.getId(), 0, "Exam Stats");			
		}
	}
	        
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle().toString().equals("Release")) {
			//RELEASE THIS EXAM AND NOTIFY STUDENTS
			InstructorsExamsSql.releaseExam(single_exam);
//			single_exam.setStatus("Released");
			listview.invalidateViews();
		}
		else if (item.getTitle().toString().equals("Unrelease")) {
			//RELEASE THIS EXAM AND NOTIFY STUDENTS
			InstructorsExamsSql.unreleaseExam(single_exam);
//			single_exam.setStatus("Unreleased");
			listview.invalidateViews();
		}
		else if (item.getTitle().toString().equals("Delete")) {
			//REMOVE THIS EXAM FROM THE LIST AND THE DATABASE
			
			InstructorsExamsSql.deleteExam(single_exam);
			list_exams.remove(info.position);
			listview.invalidateViews();
		}
		else if (item.getTitle().toString().equals("Exam Stats")) {	
			//OPEN ACTIVITY WITH STATS ABOUT THIS EXAM
		}
		else{
			return false;
		}
		return true;
	}	
}//END CLASS
