package InstructorClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.malan.cs490project.R;

import ExamQuestionClasses.QuestionObject;
import NetworkClasses.Login;
import NetworkClasses.Streamer;
import SqlClasses.QuestionSql;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/*TODO
 * - Include due DATE/TIME functionality
 * - Include time per exam functionality
 * */

public class InstructorFragmentTab2 extends Fragment implements OnClickListener{
	
	Questions_ListViewAdapter listviewadapter;
	ListView listview;
	EditText examNameView;	
	
	String response = "";
	
	Login session;
	private QuestionSql questionsSql;
	String JSON;
	JSONObject JSON_OBJECT;
	JSONArray JSON_ARRAY;
	
	ArrayList<QuestionObject> questions;
	
	String examName;
	ArrayList<String> questionsForSubmit;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
        View view = inflater.inflate(R.layout.fragment_instructor_tab2, container, false);

		examNameView = (EditText) view.findViewById(R.id.examName);		
		listview = (ListView) view.findViewById(R.id.listView1);
		
		session = new Login();
		questionsSql = new QuestionSql(getActivity().getBaseContext());
		AsyncTask<String, Void, String> thread_response = new GettingQuestions().execute("professor1","getExamQuestions");
		try {
			questionsSql.open();
			questions = new ArrayList<QuestionObject>();
			JSON = thread_response.get().toString();
//			Log.i("InstructorFragmentTab2", JSON);
			JSON_OBJECT = new JSONObject(JSON);
			JSON_ARRAY = new JSONArray(JSON_OBJECT.get("questions").toString());
//			Log.i("InstructorFragmentTab2", JSON_ARRAY.toString());
			for(int i = 0; i < JSON_ARRAY.length(); i++)
			{
				QuestionObject temp_question = questionsSql.createQuestion(
						JSON_ARRAY.getJSONObject(i).getString("questionID"),
						JSON_ARRAY.getJSONObject(i).getString("questionType"),
						JSON_ARRAY.getJSONObject(i).getString("question")
						);
				questions.add(temp_question);
			}

		} catch (InterruptedException | ExecutionException | JSONException e) {
			Log.w("InstructorFragmentTab2", "Error parsing JSON");
			e.printStackTrace();
		}
		listviewadapter = new Questions_ListViewAdapter(getActivity().getBaseContext(), R.layout.question_list_item,questionsSql.getAllQuestions());		
		listview.setAdapter(listviewadapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listview.setMultiChoiceModeListener(new MultiChoiceModeListener() {
       	 
			public void onItemCheckedStateChanged(ActionMode mode,int position, long id, boolean checked) {
                final int checkedCount = listview.getCheckedItemCount();    // Capture total checked items
                mode.setTitle(checkedCount + " Selected");	                // Set the CAB title according to total checked items
                listviewadapter.toggleSelection(position);					// Calls toggleSelection method from ListViewAdapter Class
            }
 
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                case R.id.delete:
                    // Calls getSelectedIds method from ListViewAdapter Class
                    SparseBooleanArray selectedForDel = listviewadapter.getSelectedIds();
                    for (int i = (selectedForDel.size() - 1); i >= 0; i--) {
                        if (selectedForDel.valueAt(i)) {
                            QuestionObject selecteditem = listviewadapter.getItem(selectedForDel.keyAt(i));                            
                            questionsSql.deleteQuestion(selecteditem);
                            listviewadapter.remove(selecteditem);
                        }
                    }
                    // Close CAB
                    mode.finish();
                    return true;
                    
                case R.id.submit:
                	questionsForSubmit = new ArrayList<String>();
                    SparseBooleanArray selected = listviewadapter.getSelectedIds();
                    // Captures all selected ids with a loop
                    for (int i = (selected.size() - 1); i >= 0; i--) {
                        if (selected.valueAt(i)) {
                            QuestionObject selecteditem = listviewadapter.getItem(selected.keyAt(i));
                            // Remove selected items following the ids
                            questionsForSubmit.add(selecteditem.getId());
                            if(examNameView.getText().toString().equals("")){
                            	examName = "Untitled";                            	
                            }
                            else{
                            	examName = examNameView.getText().toString();
                            }
                        }
                    }
                    try {
						questionsSql.submitQuestions(questionsForSubmit, examName);
					} catch (JSONException e) {
						Log.w("InstructorFragmentTab2", "Error submitting questions for exam: " + examName);
						e.printStackTrace();
					}
                    Toast.makeText(getActivity().getBaseContext(), "Exam: " + examName + " submitted", Toast.LENGTH_SHORT).show();
                    
                    // Close CAB
                    mode.finish();
                    return true;

                default:                    	
                	return false;
                }
            }
 
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.questions_menu, menu);
                return true;
            }
 
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                listviewadapter.removeSelection();
            }
 
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
        });

        
		return view;


//		Toast.makeText(getActivity().getBaseContext(), "Press and hold to select", Toast.LENGTH_SHORT).show();
	}
	
	
	
	@Override
	public void onResume() {
		questionsSql.open();
		super.onResume();
	} 

	@Override
	public void onStop() {
		questionsSql.close();
	    super.onStop();
	} 
	
	@Override
	public void onPause(){
		questionsSql.close();
		super.onPause();
	}
	
	@Override
	public void onDestroyView(){
		questionsSql.close();
		super.onDestroyView();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
//######################################################################################################	
	private class GettingQuestions extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) 
		{
			try {
				return postUrl(urls[0],urls[1]);
			}
			catch (IOException e){
				return "Cannot connect. Server is not responding.";
			}
		}		
		private String postUrl(String user_id, String tag) throws IOException {
			String[] raw_response = {};	         
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("user", user_id);
			params.put("tag", tag);
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
		}//END downloadUrl FUNCTION
	}//END ASYNC CLASS	
}