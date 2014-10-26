package InstructorClasses;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import com.example.cs490project.CurrentExamAdapter;
import com.example.cs490project.ExamObject;
import com.example.cs490project.R;
import NetworkClasses.loginFunctions;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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

public class InstructorFragmentTab1 extends Fragment {
	
	View view;
	LinearLayout layout;
	ListView listview;
	String instructor_JSON;
	JSONArray examArray;
	private boolean Paused;
	ArrayList<ExamObject> list = new ArrayList<ExamObject>();
	
	AdapterView.AdapterContextMenuInfo info;	
	
	public static loginFunctions session = new loginFunctions();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_instructor_tab1, container, false);
		
    	Bundle args = getArguments();
    	instructor_JSON = args.getString("INSTRUCTOR_JSON");
    	    	
    	try 
    	{
			examArray = new JSONArray(instructor_JSON);
			for(int i = 0; i < examArray.length(); i++)
			{
				ExamObject exams = new ExamObject();
				exams.setId(examArray.getJSONObject(i).getString("examID"));
				exams.setName(examArray.getJSONObject(i).getString("examName"));
				if(examArray.getJSONObject(i).getString("examReleased").equals("True")){
					exams.setStatus("Released");	
				}
				else{
					exams.setStatus("Unreleased");
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
    	
		return view;
	}
	
	@Override
	public void onPause()
	{
	    super.onPause();
	    this.Paused = true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);      
	                    
		info = (AdapterContextMenuInfo) menuInfo;
	 
		menu.setHeaderTitle(list.get(info.position).getName());      
		menu.add(Menu.NONE, v.getId(), 0, "Release");
		menu.add(Menu.NONE, v.getId(), 0, "Delete");                  
	}
	        
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Release") {
			//Do your working
		}
		else if (item.getTitle() == "Delete") {
			list.remove(info.position);			
			listview.invalidateViews();
		}
		else     {
			return false;
		}
		return true;
	}	
}