package InstructorClasses;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.malan.cs490project.R;

import ExamQuestionClasses.QuestionObject;
import NetworkClasses.Login;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/*TODO
 * - Include due DATE/TIME functionality
 * - Include time per exam functionality
 * */

public class InstructorFragmentTab2 extends Fragment implements OnClickListener{
		
	ListView listview;
	EditText examNameView;
	ArrayAdapter<String> adapter;	
	String questions_for_JSON;
	String examName;
	Questions_ListViewAdapter listviewadapter;
	ArrayList<QuestionObject> questions;
	Login session;
	ArrayList<String> questionsForSubmit;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
        View view = inflater.inflate(R.layout.fragment_instructor_tab2, container, false);
        return view;

		/*
		Toast.makeText(getActivity().getBaseContext(), "Press and hold to select", Toast.LENGTH_SHORT).show();
		//		CREATE VIEW AND FIND RELEVENT VIEW OBJECTS
		View view = inflater.inflate(R.layout.fragment_instructor_tab2, container, false);
		
		examNameView = (EditText) view.findViewById(R.id.examName);		
		listview = (ListView) view.findViewById(R.id.listView1);

		//		CREATE session FOR POSTING CHOSEN QUESTIONS TO SERVER		
		session = new Login();
		
		//		RETRIEVE ARGUMENTS FROM InstructorPanel
    	Bundle args = getArguments();
    	questions_for_JSON = args.getString("QUESTIONS");

		//		CONVERT STRING TO JSON FOR PARSING
		try {
			questions = new ArrayList<QuestionObject>();
			JSONObject tempJSON = new JSONObject(questions_for_JSON);
			questions_for_JSON = tempJSON.get("questions").toString();
			JSONArray result = new JSONArray(questions_for_JSON);
			for(int i = 0; i < result.length(); i++)
			{
				QuestionObject temp_question = new QuestionObject();
				temp_question.setId(result.getJSONObject(i).getString("questionID"));
				temp_question.setQuestion(result.getJSONObject(i).getString("question"));
				temp_question.setType(result.getJSONObject(i).getString("questionType"));
				
				questions.add(temp_question);
			}
		}
		catch (JSONException e) {
			Toast.makeText(getActivity(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		//		SET ADAPTER
		listviewadapter = new Questions_ListViewAdapter(getActivity(), R.layout.question_list_item,questions);		
		listview.setAdapter(listviewadapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);


        //		SET ABILITY TO CHOOSE DIFFERENT QUESTIONS
        listview.setMultiChoiceModeListener(new MultiChoiceModeListener() {
        	 
            @SuppressLint("NewApi")
			public void onItemCheckedStateChanged(ActionMode mode,int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listview.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                listviewadapter.toggleSelection(position);
            }
 
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                case R.id.delete:
                    // Calls getSelectedIds method from ListViewAdapter Class
                    SparseBooleanArray selectedForDel = listviewadapter.getSelectedIds();
                    // Captures all selected ids with a loop
                    for (int i = (selectedForDel.size() - 1); i >= 0; i--) {
                        if (selectedForDel.valueAt(i)) {
                            QuestionObject selecteditem = listviewadapter.getItem(selectedForDel.keyAt(i));
                            // Remove selected items following the ids
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
                            
                            Toast.makeText(getActivity().getBaseContext(), "Exam: " + examName + " submitted", Toast.LENGTH_SHORT).show();
                        }
                    }
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}