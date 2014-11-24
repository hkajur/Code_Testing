package StudentClasses;

import com.malan.cs490project.AppProfile;
import com.malan.cs490project.ExamInProgress;
import com.malan.cs490project.ExamReview;
import com.malan.cs490project.R;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.malan.cs490project.CurrentExamAdapter;

import ExamQuestionClasses.ExamObject;
import NetworkClasses.Login;
import NetworkClasses.Streamer;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class StudentFragmentTab2 extends Fragment {
	
	View view;
	LinearLayout layout;
	ListView listview;
	AppProfile creds;
	String userID;
	String examID;
	String response="";
	String JSON;
	JSONObject JSON_OBJECT;
	JSONArray JSON_ARRAY;
	ExamObject single_exam;	
	private static List<ExamObject> list_exams = new ArrayList<ExamObject>();		
	public static Login session = new Login();	
	AdapterView.AdapterContextMenuInfo info;	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		
        View view = inflater.inflate(R.layout.fragment_student_tab2, container, false);
        
        creds = new AppProfile(getActivity().getBaseContext());
        userID = creds.getValue(AppProfile.PREF_ID);
        AsyncTask<String, Void, String> thread_response = new GettingExams().execute(
        		creds.getValue(AppProfile.PREF_ID),
        		"gradedExams");
		
		try 
    	{
			JSON_OBJECT = new JSONObject(thread_response.get().toString());
			JSON_ARRAY = new JSONArray(JSON_OBJECT.get("exams").toString());
			for(int i = 0; i < JSON_ARRAY.length(); i++)
			{				
				single_exam = new ExamObject();
				single_exam.setId(JSON_ARRAY.getJSONObject(i).getString("examID"));
				single_exam.setName(JSON_ARRAY.getJSONObject(i).getString("examName"));
				list_exams.add(single_exam);
			}
		}
    	catch (JSONException e) 
		{
			Log.w("JSON PARSING ERROR", "Imported JSON String can't be converted to JSON object");
			e.printStackTrace();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} 
    	
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
		
    	listview.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
    	    {   
    			ExamObject temp = list_exams.get(position);

    			Intent intent = new Intent(getActivity().getBaseContext(),ExamReview.class);    			 	
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

	//######################################################################################################	
		private class GettingExams extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... urls) 
			{
				String[] raw_response = {};	         
				Map<String, String> params = new HashMap<String, String>();				   
				params.put("userID", urls[0]);
				params.put("tag", urls[1]);
				params.put("token", session.getToken());
				
				try {
					Streamer.sendPostRequest(session.getURL(), params);
					raw_response = Streamer.readMultipleLinesRespone();
				} catch (IOException ex) {
					Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
					ex.printStackTrace();
				}
				for(String i : raw_response){
					response+=i;
				}
				Streamer.disconnect();
				return response;
			}		
		}//END ASYNC CLASS	

}
