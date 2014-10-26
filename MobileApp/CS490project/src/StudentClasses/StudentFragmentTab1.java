package StudentClasses;

import com.example.cs490project.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import com.example.cs490project.CurrentExamAdapter;
import com.example.cs490project.ExamObject;
import NetworkClasses.loginFunctions;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.ListView;


public class StudentFragmentTab1 extends Fragment {
	
	View view;
	LinearLayout layout;
	ListView listview;
	String Student_JSON;
	JSONArray examArray;
	ExamObject exams;
	private static ArrayList<ExamObject> list = new ArrayList<ExamObject>();	
	public static loginFunctions session = new loginFunctions();	
	AdapterView.AdapterContextMenuInfo info;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_student_tab1, container, false);

    	Bundle args = getArguments();
    	Student_JSON = args.getString("STUDENT_JSON");

    	try 
    	{
			examArray = new JSONArray(Student_JSON);
			for(int i = 0; i < examArray.length(); i++)
			{
				if(examArray.getJSONObject(i).getString("examTaken").equals("True")){
					exams = new ExamObject();
					exams.setId(examArray.getJSONObject(i).getString("examID"));
					exams.setName(examArray.getJSONObject(i).getString("examName"));	
				}				
				list.add(exams);
			}
			
			layout = (LinearLayout) view;				
			listview = (ListView) view.findViewById(R.id.listView1);
			
			if(list.size() != 0){
				listview.setAdapter(new CurrentExamAdapter(list, getActivity().getBaseContext()));
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
		}
    	catch (JSONException e) 
		{
			Log.w("JSON PARSING ERROR", "Imported JSON String can't be converted to JSON object");
			e.printStackTrace();
		}
    	
		return view;		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);      
	                    
		info = (AdapterContextMenuInfo) menuInfo;
	 
		menu.setHeaderTitle(list.get(info.position).getName());      
		menu.add(Menu.NONE, v.getId(), 0, "Take Exam");
		menu.add(Menu.NONE, v.getId(), 0, "Email Instructor");                  
	}
	        
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Take Exam") {
			//Do your working
		}
		else if (item.getTitle() == "Email Instructor") {
			list.remove(info.position);			
			listview.invalidateViews();
		}
		else     {
			return false;
		}
		return true;
	}
}
