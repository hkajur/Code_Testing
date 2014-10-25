package InstructorClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.cs490project.R;
import com.example.cs490project.R.id;
import com.example.cs490project.R.layout;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
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

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		Toast.makeText(getActivity().getBaseContext(), "SWIPE QUESTION LEFT TO SELECT", Toast.LENGTH_SHORT).show();
		
		
		View view = inflater.inflate(R.layout.fragment_instructor_tab2, container, false);
		
		
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
//	    final StableArrayAdapter adapter = new StableArrayAdapter(getActivity().getBaseContext(),R.layout.simple_list_item, list);
	    adapter = new ArrayAdapter(getActivity().getBaseContext(),R.layout.simple_list_item, list);

	    listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    listview.setAdapter(adapter);

	    
		submit.setOnClickListener(this);
		/* THIS WORKS
		Bundle args = getArguments();
    	final String user_id = args.getString("USER_ID");
    	final String instructor_JSON = args.getString("INSTRUCTOR_JSON");
		
    	

        LinearLayout l = (LinearLayout) view;
        LayoutParams params = new LayoutParams(
        		LayoutParams.MATCH_PARENT,
        		LayoutParams.WRAP_CONTENT                      
        );
        params.setMargins(0, 5, 0, 5);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/gillsansmt.ttf"); 
        
        
        TextView tv;
        
        for(int i=0 ; i<2 ; i++){
        	tv = new TextView(container.getContext());
            tv.setBackgroundResource(R.drawable.exam_background);
            tv.setPadding(15, 10, 10, 15);
            tv.setTextSize(15);
            tv.setText(String.valueOf(i));
            tv.setTypeface(type);
            tv.setLayoutParams(params);
            tv.setOnTouchListener(new ShowExamGrade());
            l.addView(tv);        	
        }
        
        Button submit = new Button(container.getContext());
        params.setMargins(0, 25, 0, 0);
        submit.setBackgroundResource(R.drawable.buttonshape);
        submit.setText("Submit");
        submit.setLayoutParams(params);
//        submit.setOnClickListener((OnClickListener) l);
        l.addView(submit);
        
        /*
        tv = new TextView(container.getContext());
        tv.setText("Testing2...");
        l.addView(tv);
    	*/
        
		return view;
	}
	public void onClick(View v) {
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
//############################################################################################################	
	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,List<String> objects) {
	    	super(context, textViewResourceId, objects);
	    	for (int i = 0; i < objects.size(); ++i) 
	    	{
	    		mIdMap.put(objects.get(i), i);
	    	}
	    }

	    @Override
	    public long getItemId(int position) 
	    {
	    	String item = getItem(position);
	    	return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() 
	    {
	    	return true;
	    }
	}
//############################################################################################################	
}