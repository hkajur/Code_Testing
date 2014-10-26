package InstructorClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.cs490project.R;

import com.example.cs490project.R.id;
import com.example.cs490project.R.layout;

import NetworkClasses.AuthenticatorConnection;
import NetworkClasses.PostAsync;
import NetworkClasses.loginFunctions;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class InstructorFragmentTab2 extends Fragment implements OnClickListener{
	
	LinearLayout layout;
	ListView listview;
	Button submit;
	ArrayAdapter<String> adapter;
	
	String user_id;
	
	public static loginFunctions session = new loginFunctions();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		Toast.makeText(getActivity().getBaseContext(), "SWIPE QUESTION LEFT TO SELECT", Toast.LENGTH_SHORT).show();
		
		View view = inflater.inflate(R.layout.fragment_instructor_tab2, container, false);

		
    	Bundle args = getArguments();
    	user_id = args.getString("USER_ID");

		//myurl,user_id, tag
		AsyncTask<String, Void, String> response = new PostAsync().execute(session.getURL(),user_id,"getExamQuestions");
		try {
			String iight = response.get();
		}
		catch (InterruptedException e) 
		{
			Toast.makeText(getActivity().getBaseContext(), "Interruption Occured. Try again.", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		catch (ExecutionException e) 
		{
			Toast.makeText(getActivity().getBaseContext(), "Execution interrupted.Try again.", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
    	
		layout = (LinearLayout) view;		
		submit = (Button) view.findViewById(R.id.button1);		
		listview = (ListView) view.findViewById(R.id.listView1);
	    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
		        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
		        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
		        "Android", "iPhone", "WindowsMobile" };

	    final ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < values.length; ++i) {
	      list.add(values[i]);
	    }
	    adapter = new ArrayAdapter(getActivity().getBaseContext(),R.layout.simple_list_item, list);

	    listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    listview.setAdapter(adapter);

	    
		submit.setOnClickListener(this);
		
		
		
		return view;
	}
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
		*/
	}	
}