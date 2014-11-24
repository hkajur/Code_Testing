package InstructorClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.malan.cs490project.R;

import NetworkClasses.Login;
import NetworkClasses.Streamer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class InstructorFragmentTab3 extends Fragment {
	
	int temp_placeholder=0;
	
	private Spinner choice;
	private Button clear;
	private Button submit;
	private EditText question_in;
	private EditText points_in;	
	private GridLayout responses;
	RelativeLayout.LayoutParams RelativeLayoutParams;
	RelativeLayout.LayoutParams RelativeLayoutParams2;
	
	public int points;
	public String question;
	public Map<String,String> answers;
	
	public Login session;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
        View view = inflater.inflate(R.layout.fragment_instructor_tab3, container, false);
        choice = (Spinner) view.findViewById(R.id.SpinnerFeedbackType);
        submit = (Button) view.findViewById(R.id.button1);
        clear = (Button) view.findViewById(R.id.button2);
        question_in = (EditText) view.findViewById(R.id.QuestionText);
        points_in = (EditText) view.findViewById(R.id.points);
        responses = (GridLayout) view.findViewById(R.id.fragmentContainer);

        
        
        
		choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
	    	android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {		    				
		    	String text = choice.getSelectedItem().toString();
		    	
	            fragmentManager = getFragmentManager();
		    	fragmentTransaction = fragmentManager.beginTransaction();
	            
		    	if (text.equals("Multiple Choice")){
		    		
		    		responses.removeAllViews();

		    		GridLayout.Spec titleTxtSpecColumn = GridLayout.spec(0, GridLayout.BASELINE);
		    		GridLayout.Spec titleTxtSpecRow = GridLayout.spec(0);
		    		
		    		EditText[] newText = new EditText[8];
		    		
		    		newText[0] = (EditText) new EditText(getActivity().getBaseContext());
		    		newText[0].setHint("Answer");
		    		newText[0].setId(1);
		    		newText[0].setHintTextColor(Color.GREEN);
		    		newText[0].setTextColor(Color.BLACK);
		    		newText[0].setGravity(1);
		    		responses.addView(newText[0], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    		
		    		titleTxtSpecColumn = GridLayout.spec(1, GridLayout.BASELINE);
		    		titleTxtSpecRow = GridLayout.spec(0);
		    		newText[1] = new EditText(getActivity().getBaseContext());
		    		newText[1].setHint("Correct Reason");
		    		newText[1].setHintTextColor(Color.GREEN);
		    		newText[1].setTextColor(Color.BLACK);
		    		newText[1].setGravity(1);
		    		responses.addView(newText[1], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    		
	    			int col = 0;
	    			int row = 1;
	    			int place = 2;
		    		for(int i=0 ; i<3 ; i++){
		    			col = 0;
			    		titleTxtSpecColumn = GridLayout.spec(col, GridLayout.BASELINE);
			    		titleTxtSpecRow = GridLayout.spec(row);

		    			newText[place] = (EditText) new EditText(getActivity().getBaseContext());
		    			newText[place].setHint("Wrong " + i);
		    			newText[place].setId(2);
		    			newText[place].setHintTextColor(Color.RED);
		    			newText[place].setTextColor(Color.BLACK);
		    			newText[place].setGravity(1);
		    			temp_placeholder=newText[place].getId();
		    			responses.addView(newText[place]);
		    			
		    			place++;
		    			col++;
		    			
		    			newText[place] = new EditText(getActivity().getBaseContext());
		    			newText[place].setHint("Wrong " + i + " reason");
		    			newText[place].setId(2);
		    			newText[place].setHintTextColor(Color.RED);
		    			newText[place].setTextColor(Color.BLACK);
		    			newText[place].setGravity(1);
		    			temp_placeholder=newText[place].getId();
		    			responses.addView(newText[place]);
		    			place++;
		    			row++;
		    		}

		    		
	    				
		    	}
		    	else if (text.equals("True/False")){
		    		responses.removeAllViews();
		    		
		    		GridLayout.Spec titleTxtSpecColumn = GridLayout.spec(0, GridLayout.BASELINE);
		    		GridLayout.Spec titleTxtSpecRow = GridLayout.spec(0);
		    		
		    		EditText[] newText = new EditText[8];
		    		
		    		newText[0] = (EditText) new EditText(getActivity().getBaseContext());
		    		newText[0].setHint("Answer");
		    		newText[0].setId(1);
		    		newText[0].setHintTextColor(Color.GREEN);
		    		newText[0].setTextColor(Color.BLACK);
		    		newText[0].setGravity(1);
		    		responses.addView(newText[0], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    		
		    		titleTxtSpecColumn = GridLayout.spec(1, GridLayout.BASELINE);
		    		titleTxtSpecRow = GridLayout.spec(0);
		    		newText[1] = new EditText(getActivity().getBaseContext());
		    		newText[1].setHint("Correct Reason");
		    		newText[1].setHintTextColor(Color.GREEN);
		    		newText[1].setTextColor(Color.BLACK);
		    		newText[1].setGravity(1);
		    		responses.addView(newText[1], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));
		    		
		    		titleTxtSpecColumn = GridLayout.spec(0, GridLayout.BASELINE);
		    		titleTxtSpecRow = GridLayout.spec(1);
		    		
		    		
		    		newText[2] = (EditText) new EditText(getActivity().getBaseContext());
		    		newText[2].setHint("Wrong");
		    		newText[2].setId(1);
		    		newText[2].setHintTextColor(Color.RED);
		    		newText[2].setTextColor(Color.BLACK);
		    		newText[2].setGravity(1);
		    		responses.addView(newText[2], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    		
		    		titleTxtSpecColumn = GridLayout.spec(1, GridLayout.BASELINE);
		    		titleTxtSpecRow = GridLayout.spec(1);
		    		newText[3] = new EditText(getActivity().getBaseContext());
		    		newText[3].setHint("Wrong Reason");
		    		newText[3].setHintTextColor(Color.RED);
		    		newText[3].setTextColor(Color.BLACK);
		    		newText[3].setGravity(1);
		    		responses.addView(newText[3], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    	}
		    	else if(text.equals("Short Answer")){
		    		responses.removeAllViews();

		    	}
		    	else{
		    		responses.removeAllViews();
		    		for(int i=0 ; i<3 ; i++){
		    			EditText newText = new EditText(getActivity().getBaseContext());
		    			newText = new EditText(getActivity().getBaseContext());
		    			newText.setHint("Input");
		    			newText.setTextColor(Color.BLACK);
		    			newText.setGravity(1);
		    			responses.addView(newText);	
		    		
		    			newText = new EditText(getActivity().getBaseContext());
		    			newText.setHint("Output");
		    			newText.setTextColor(Color.BLACK);
		    			newText.setGravity(1);
		    			responses.addView(newText);
		    		}
		    	}
		    	fragmentTransaction.commit();
		    	
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    	fragmentTransaction.replace(R.id.fragmentContainer,null);
		    	fragmentTransaction.commit();
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

	public class HttpAsyncTask extends AsyncTask<String, Void, String> {
	    @Override
    	protected String doInBackground(String... urls) 
    	{
	    	session = new Login();
    		String response = null;	         
	         
			//PARAMETERS TO SEND
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("token", urls[0]);
			params.put("tag", "TrueFalseChoiceQuestionInsert");
			params.put("question_type", "TrueFalse");

			params.put("question", question.toString());

			try {
				Streamer.sendPostRequest(session.getURL(), params);
			    response = Streamer.readSingleLineRespone();
			} catch (IOException ex) {
				Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
			    ex.printStackTrace();
			}
			Streamer.disconnect();	        
			return response;
    	}
		@Override
		protected void onPostExecute(String result) 
		{
			JSONObject response;
			Toast.makeText(getActivity().getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();
			try {
				response = new JSONObject(result);
				
				if(response.get("questionCreated").toString().equals("Success")){
					Toast.makeText(getActivity().getBaseContext(), "Question added", Toast.LENGTH_SHORT).show();
					Log.i("InstructorFragmentTab3", "Question submitted");
				}
				else{
					Log.i("InstructorFragmentTab3", "Error submitting");
					Log.w("InstructorFragmentTab3", response.get("Error").toString());
					Toast.makeText(getActivity().getBaseContext(), "Error submitting", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				Toast.makeText(getActivity().getBaseContext(), "Server has not responded. Try again.", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
	}//END ASYNC CLASS 
}

