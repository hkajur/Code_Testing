package InstructorClasses;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cs490project.R;

import com.example.cs490project.QuestionObject;
import NetworkClasses.loginFunctions;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class InstructorFragmentTab2 extends Fragment implements OnClickListener{
	
	LinearLayout layout;
	ListView listview;
	Button submit;
	ArrayAdapter<String> adapter;
	
	String questions_for_JSON;
	private static ArrayList<QuestionObject> questions = new ArrayList<QuestionObject>();
	
	public static loginFunctions session = new loginFunctions();

	final ArrayList<String> list = new ArrayList<String>();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
//		Toast.makeText(getActivity().getBaseContext(), "SWIPE QUESTION LEFT TO SELECT", Toast.LENGTH_SHORT).show();
		
		View view = inflater.inflate(R.layout.fragment_instructor_tab2, container, false);

		
    	Bundle args = getArguments();
    	questions_for_JSON = args.getString("QUESTIONS");

		//myurl,user_id, tag
		try {
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
    	
		layout = (LinearLayout) view;		
		submit = (Button) view.findViewById(R.id.button1);		
		listview = (ListView) view.findViewById(R.id.listView1);

	    
	    for (int i = 0; i < questions.size(); ++i) {
	      list.add(questions.get(i).getQuestion());
	    }
	    adapter = new ArrayAdapter(getActivity().getBaseContext(),R.layout.simple_list_item, list);

	    listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    listview.setAdapter(adapter);

    	listview.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
    	    {       	    	
    	    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
    	    	startActivity(browserIntent);
    	    }
    	});

	    
	    
		submit.setOnClickListener(this);
		
		
		
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	public void onClick(View v) {
		Toast.makeText(getActivity().getBaseContext(), "BOOP", Toast.LENGTH_SHORT).show();
		SparseBooleanArray checked = listview.getCheckedItemPositions();
		ArrayList<String> selectedItems = new ArrayList<String>();
		for (int i = 0; i < checked.size(); i++) {
			int position = checked.keyAt(i);
			if (checked.valueAt(i))
				selectedItems.add(adapter.getItem(position));
		}

		String[] outputStrArr = new String[selectedItems.size()];

		for (int i = 0; i < selectedItems.size(); i++) {
			outputStrArr[i] = selectedItems.get(i);
		}

		/*
		Intent intent = new Intent(getApplicationContext(),ResultActivity.class);

		// Create a bundle object
		Bundle b = new Bundle();
		b.putStringArray("selectedItems", outputStrArr);

		// Add the bundle to the intent.
		intent.putExtras(b);

		// start the ResultActivity
		startActivity(intent);
	}			*/
}

