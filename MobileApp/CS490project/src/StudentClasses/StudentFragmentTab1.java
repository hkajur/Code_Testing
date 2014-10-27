package StudentClasses;

import com.example.cs490project.R;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import com.example.cs490project.CurrentExamAdapter;
import com.example.cs490project.ExamInProgress;
import com.example.cs490project.ExamObject;
import com.example.cs490project.MainActivity;
import com.example.cs490project.StudentPanel;

import NetworkClasses.loginFunctions;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;


public class StudentFragmentTab1 extends Fragment {
	
	View view;
	LinearLayout layout;
	ListView listview;
	String Student_JSON;
	String user_id;
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
    	user_id = args.getString("USER_ID");
    	
    	try 
    	{
			examArray = new JSONArray(Student_JSON);
			if(list.size() != 0){
				list.clear();
			}
			for(int i = 0; i < examArray.length(); i++)
			{
				exams = new ExamObject();
				exams.setId(examArray.getJSONObject(i).getString("examID"));
				exams.setName(examArray.getJSONObject(i).getString("examName"));
				if(examArray.getJSONObject(i).getString("examTaken").equals("True")){
					list.add(exams);
				}				
			}
			
			layout = (LinearLayout) view;
			listview = (ListView) view.findViewById(R.id.listView1);
			
			if(list.size()!=0){
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
    	
    	listview.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
    	    {   
    			ExamObject temp = list.get(position);

    			
    			/*
    			Intent intent = new Intent(getActivity(), ExamInProgress.class);
				intent.putExtra("EXAM_ID", temp.getId());
				intent.putExtra("USER_ID", user_id);
//				startActivity(intent);
				startActivity(intent);
*/
    	    	
    	    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
    	    	startActivity(browserIntent);
    	    }
    	});
    	
		return view;		
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
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
			startActivity(browserIntent);
		}
		else if (item.getTitle() == "Email Instructor") {
			Intent email = new Intent(Intent.ACTION_SEND);
		    email.putExtra(Intent.EXTRA_EMAIL,new String[] { user_id+"@njit.edu"});
		    email.putExtra(Intent.EXTRA_SUBJECT,"Regarding Exam:" + item.toString());
		    email.putExtra(Intent.EXTRA_TEXT,"sent a message using the contact us ");

		    email.setType("message/rfc822");

		    startActivityForResult(Intent.createChooser(email, "Choose an Email client:"),1);
		}
		else     {
			return false;
		}
		return true;
	}
}
