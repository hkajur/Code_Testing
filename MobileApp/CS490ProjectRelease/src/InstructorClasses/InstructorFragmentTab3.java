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
import android.widget.Spinner;
import android.widget.Toast;

public class InstructorFragmentTab3 extends Fragment {
	
	int temp_placeholder=0;
	
	private Spinner choice;
	private Button clear;
	private Button submit;
	private EditText question_in;
	private EditText points_in;	
	private LinearLayout responses;
	
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
        responses = (LinearLayout) view.findViewById(R.id.fragmentContainer);

        
        
        
		choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
	    	android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {		    				
		    	String text = choice.getSelectedItem().toString();
		    	
	            fragmentManager = getFragmentManager();
		    	fragmentTransaction = fragmentManager.beginTransaction();
	            
		    	if (text.equals("Multiple Choice")){
		    		responses.removeAllViews();
		    		temp_placeholder=0;
		    		
		    		EditText newText = new EditText(getActivity().getBaseContext());
		    		temp_placeholder=newText.getId();
		    		newText.setHint("Answer");
		    		newText.setHintTextColor(Color.GREEN);
		    		newText.setTextColor(Color.BLACK);
		    		newText.setGravity(1);
	    			responses.addView(newText);
		    		
	    			for(int i=0 ; i<3 ; i++){
		    			newText = new EditText(getActivity().getBaseContext());
		    			newText.setHint("Wrong " + i);
		    			newText.setHintTextColor(Color.RED);
		    			newText.setTextColor(Color.BLACK);
		    			newText.setGravity(1);
		    			temp_placeholder=newText.getId();
		    			responses.addView(newText);
		    		}	
		    	}
		    	else if (text.equals("True/False")){
		    		responses.removeAllViews();
		    		temp_placeholder=0;
		    		
		    		EditText newText = new EditText(getActivity().getBaseContext());
		    		temp_placeholder=newText.getId();
		    		newText.setHint("Answer");
		    		newText.setHintTextColor(Color.GREEN);
		    		newText.setTextColor(Color.BLACK);
		    		newText.setGravity(1);
	    			responses.addView(newText);
		    		
	    			newText = new EditText(getActivity().getBaseContext());
	    			newText.setHint("Wrong");
	    			newText.setHintTextColor(Color.RED);
	    			newText.setTextColor(Color.BLACK);
	    			newText.setGravity(1);
	    			temp_placeholder=newText.getId();
	    			responses.addView(newText);	
		    	}
		    	else if(text.equals("Fill in the blank")){
		    		responses.removeAllViews();
		    		temp_placeholder=0;
		    	}
		    	else{
		    		responses.removeAllViews();
		    		temp_placeholder=0;
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

