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

import org.json.JSONArray;
import org.json.JSONException;

import com.malan.cs490project.CurrentExamAdapter;
import com.malan.cs490project.R;

import ExamQuestionClasses.ExamObject;
import ExamQuestionClasses.ExamSqlData;
import NetworkClasses.Login;
import NetworkClasses.ReleaseExam;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/* TODO
 *  - Add functionality for releasing
 *  - Add functionality for deleting
 *  - Add functionality for exam stats
 * */

public class InstructorFragmentTab1 extends Fragment {
	
	View view;
	LinearLayout layout;
	ListView listview;
	String instructor_JSON;
	JSONArray examArray;
	ExamObject single_exam;
	private ExamSqlData InstructorsExamsSql;
	private static List<ExamObject> list_exams = new ArrayList<ExamObject>();		
	public static Login session = new Login();	
	AdapterView.AdapterContextMenuInfo info;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{			

		view = inflater.inflate(R.layout.fragment_instructor_tab1, container, false);
		
		InstructorsExamsSql = new ExamSqlData(getActivity());
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
	    listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		
    	listview.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
    	    {   
    			//DISPLAY THE EXAM HERE IN A SCROLLABLE MANNER    			
//    	    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//    	    	startActivity(browserIntent);
    	    }
    	});
		return view;
	}
	
	@Override
	public void onResume(){
		InstructorsExamsSql.open();
	    super.onResume();
	}
	
	@Override
	public void onStop()
	{
	    super.onStop();	 
	}
	@Override
	public void onPause() {
		InstructorsExamsSql.close();
	    super.onPause();
	  }
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);      
	                    
		info = (AdapterContextMenuInfo) menuInfo;
	 
		menu.setHeaderTitle(list_exams.get(info.position).getName());      
		menu.add(Menu.NONE, v.getId(), 0, "Release");
		menu.add(Menu.NONE, v.getId(), 0, "Delete");                  
		menu.add(Menu.NONE, v.getId(), 0, "Exam Stats");                  
	}
	        
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle().toString().equals("Release")) {
			//RELEASE THIS EXAM AND NOTIFY STUDENTS
			ReleaseExam examToRelease = new ReleaseExam();
			examToRelease.release(info.position);
		}
		else if (item.getTitle().toString().equals("Delete")) {
			//REMOVE THIS EXAM FROM THE LIST AND THE DATABASE
			
			/*
			 * if (getListAdapter().getCount() > 0) {
        		comment = (Comment) getListAdapter().getItem(0);
        		datasource.deleteComment(comment);
        		adapter.remove(comment);
      		}
      		break;*/
			
			list_exams.remove(info.position);
//			InstructorsExamsSql.deleteExam();
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
