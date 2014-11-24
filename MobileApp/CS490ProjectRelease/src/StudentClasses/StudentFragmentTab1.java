package StudentClasses;

import com.malan.cs490project.ExamInProgress;
import com.malan.cs490project.R;

import android.support.v4.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class StudentFragmentTab1 extends Fragment {
	
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
        View view = inflater.inflate(R.layout.fragment_student_tab1, container, false);

        StudentExamsSql = new StudentExamSql(getActivity());
        StudentExamsSql.open();
		
		list_exams = StudentExamsSql.getCurrentExams();

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
        
    	listview.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
    	    {   
    			ExamObject temp = list_exams.get(position);

    			//pass exam id and user id
    			
    			Intent intent = new Intent(getActivity().getBaseContext(),ExamInProgress.class);    			 	
    			intent.putExtra("EXAM_ID", temp.getId());
				startActivity(intent);
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
}
