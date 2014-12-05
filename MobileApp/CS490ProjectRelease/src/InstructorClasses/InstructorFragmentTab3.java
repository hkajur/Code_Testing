package InstructorClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.malan.cs490project.R;

import NetworkClasses.Login;
import NetworkClasses.Streamer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.text.InputType;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class InstructorFragmentTab3 extends Fragment {
	
	String questionTypeMarker = ""; 
	
	private Spinner choice;
	private Button submit;
	private EditText question_in;
	private EditText points_in;	
	private GridLayout responses_layout;
	RelativeLayout.LayoutParams RelativeLayoutParams;
	RelativeLayout.LayoutParams RelativeLayoutParams2;
	Display display;
	Point size;
	int width;	
	
	private EditText[] mcEditText = new EditText[8]; 
	private EditText[] prEditText = new EditText[6];
	private EditText[] tfEditText = new EditText[4];
	private EditText saEditText;
	
	
	public String questionType;
	public String question;	
	public String points;
	public List<String> responses;
	
	public Login session;
	
	public JSONObject response;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
        View view = inflater.inflate(R.layout.fragment_instructor_tab3, container, false);
        choice = (Spinner) view.findViewById(R.id.SpinnerFeedbackType);
        submit = (Button) view.findViewById(R.id.button1);
        question_in = (EditText) view.findViewById(R.id.QuestionText);
        points_in = (EditText) view.findViewById(R.id.points);
        responses_layout = (GridLayout) view.findViewById(R.id.fragmentContainer);
        
		display = getActivity().getWindowManager().getDefaultDisplay();
		size = new Point();
		display.getSize(size);
		final int width = size.x;	

        
        /*
         * question_type, question,  points

   If question_type == "MultipleChoice"
      Additional Post Variables: correct, correct_reason, wrongAnswer1, wrongReason1, wrongAnswer2, wrongReason2, wrongAnswer3, wrongReason3
   Else if question_type == "TrueFalse"
      Additional Post Variables: correct,correct_reason, wrongAnswer1, wrongReason1
   Else if question_type == "ShortAnswer"
      NO additional post variables required
   Else if question_type == "Programming"
      Additional Post Variables: input1, output1, input2, output2, input3, output3

         * */

        submit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	AsyncTask<String, Void, String> response_thread = null;
            	switch (questionTypeMarker){
            		case "MC":
            			responses = new ArrayList<String>();
            			questionType = "Multiple Choice";
            			question = question_in.getText().toString();
            			points = points_in.getText().toString();
            			for(EditText i : mcEditText){
            				responses.add(i.getText().toString());
            			}
            			Log.i("MC QUESTION", responses.toString());
            			response_thread = new questionSubmit().execute(
                    			questionType,
                    			question,
                    			points,
                    			responses.toString(),
            					"MultipleChoiceQuestionInsert");		
            			break;
            		case "TF":
            			responses = new ArrayList<String>();
            			questionType = "TrueFalse";
            			question = question_in.getText().toString();
            			points = points_in.getText().toString();
            			for(EditText i : tfEditText){
            				responses.add(i.getText().toString());
            			}
            			Log.i("TF QUESTION", responses.toString());
            			response_thread = new questionSubmit().execute(
                    			questionType,
                    			question,
                    			points,
                    			responses.toString(),
            					"TrueFalseChoiceQuestionInsert");		            			
            			break;
            		case "SA":
            			responses = new ArrayList<String>();
            			questionType = "ShortAnswer";
            			question = question_in.getText().toString();
            			points = points_in.getText().toString();
            			response_thread = new questionSubmit().execute(
                    			questionType,
                    			question,
                    			points,
                    			saEditText.getText().toString(),
            					"ShortAnswerQuestionInsert");		            			
            			break;
            		case "PR":
            			responses = new ArrayList<String>();
            			questionType = "Programming";
            			question = question_in.getText().toString();
            			points = points_in.getText().toString();
            			for(EditText i : prEditText){
            				responses.add(i.getText().toString());
            			}
            			Log.i("PROGRAM QUESTION", responses.toString());
            			response_thread = new questionSubmit().execute(
                    			questionType,
                    			question,
                    			points,
                    			responses.toString(),
            					"ProgramQuestionInsert");		            			
            			break;
            		default:
            			break;            			
            	}
            	
    			try {
    				String result = response_thread.get();
    				Log.i("InstructorFragmentTab3", result);
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
    			} catch (ExecutionException | InterruptedException | JSONException e) {
    				Toast.makeText(getActivity().getBaseContext(), "Server has not responded. Try again.", Toast.LENGTH_SHORT).show();
    				e.printStackTrace();
    			} 

            	

            }
        });
        
		choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
	    	android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {		    				
		    	String text = choice.getSelectedItem().toString();
		    	
	            fragmentManager = getFragmentManager();
		    	fragmentTransaction = fragmentManager.beginTransaction();
	            
		    	if (text.equals("Multiple Choice")){
		    		questionTypeMarker = "MC";
		    		responses_layout.removeAllViews();

		    		GridLayout.Spec titleTxtSpecColumn = GridLayout.spec(0, GridLayout.BASELINE);
		    		GridLayout.Spec titleTxtSpecRow = GridLayout.spec(0);
		    		
		    		mcEditText[0] = (EditText) new EditText(getActivity().getBaseContext());
		    		mcEditText[0].setHint("Answer");
		    		mcEditText[0].setWidth(width);
		    		mcEditText[0].setId(1);
		    		mcEditText[0].setHintTextColor(Color.GREEN);
		    		mcEditText[0].setTextColor(Color.BLACK);
		    		mcEditText[0].setGravity(1);
		    		mcEditText[0].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    		responses_layout.addView(mcEditText[0], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    		
		    		titleTxtSpecColumn = GridLayout.spec(1, GridLayout.BASELINE);
		    		titleTxtSpecRow = GridLayout.spec(0);
		    		mcEditText[1] = new EditText(getActivity().getBaseContext());
		    		mcEditText[1].setHint("Correct Reason");
		    		mcEditText[1].setWidth(width);
		    		mcEditText[1].setHintTextColor(Color.GREEN);
		    		mcEditText[1].setTextColor(Color.BLACK);
		    		mcEditText[1].setGravity(1);
		    		mcEditText[1].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    		responses_layout.addView(mcEditText[1], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    		
	    			int col = 0;
	    			int row = 1;
	    			int place = 2;
		    		for(int i=0 ; i<3 ; i++){
		    			col = 0;
			    		titleTxtSpecColumn = GridLayout.spec(col, GridLayout.BASELINE);
			    		titleTxtSpecRow = GridLayout.spec(row);

			    		mcEditText[place] = (EditText) new EditText(getActivity().getBaseContext());
			    		mcEditText[place].setHint("Wrong " + i);
			    		mcEditText[place].setWidth(width);
			    		mcEditText[place].setId(2);
			    		mcEditText[place].setHintTextColor(Color.RED);
			    		mcEditText[place].setTextColor(Color.BLACK);
			    		mcEditText[place].setGravity(1);
			    		mcEditText[place].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    			responses_layout.addView(mcEditText[place]);
		    			
		    			place++;
		    			col++;
		    			
		    			mcEditText[place] = new EditText(getActivity().getBaseContext());
		    			mcEditText[place].setHint("Wrong " + i + " reason");
		    			mcEditText[place].setWidth(width);
		    			mcEditText[place].setId(2);
		    			mcEditText[place].setHintTextColor(Color.RED);
		    			mcEditText[place].setTextColor(Color.BLACK);
		    			mcEditText[place].setGravity(1);
		    			mcEditText[place].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    			responses_layout.addView(mcEditText[place]);
		    			place++;
		    			row++;
		    		}
		    		
		    		Toast.makeText(getActivity(), "Scroll left/right for anwers/feedback", Toast.LENGTH_LONG).show();
		    		
		    	}
		    	else if (text.equals("True/False")){
		    		questionTypeMarker = "TF";
		    		responses_layout.removeAllViews();
		    		
		    		GridLayout.Spec titleTxtSpecColumn = GridLayout.spec(0, GridLayout.BASELINE);
		    		GridLayout.Spec titleTxtSpecRow = GridLayout.spec(0);
		    		
		    		
		    		tfEditText[0] = (EditText) new EditText(getActivity().getBaseContext());
		    		tfEditText[0].setHint("Answer");
		    		tfEditText[0].setWidth(width);
		    		tfEditText[0].setId(1);
		    		tfEditText[0].setHintTextColor(Color.GREEN);
		    		tfEditText[0].setTextColor(Color.BLACK);
		    		tfEditText[0].setGravity(1);
		    		tfEditText[0].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    		responses_layout.addView(tfEditText[0], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    		
		    		titleTxtSpecColumn = GridLayout.spec(1, GridLayout.BASELINE);
		    		titleTxtSpecRow = GridLayout.spec(0);
		    		tfEditText[1] = new EditText(getActivity().getBaseContext());
		    		tfEditText[1].setHint("Correct Reason");
		    		tfEditText[1].setWidth(width);
		    		tfEditText[1].setHintTextColor(Color.GREEN);
		    		tfEditText[1].setTextColor(Color.BLACK);
		    		tfEditText[1].setGravity(1);
		    		tfEditText[1].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    		responses_layout.addView(tfEditText[1], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));
		    		
		    		titleTxtSpecColumn = GridLayout.spec(0, GridLayout.BASELINE);
		    		titleTxtSpecRow = GridLayout.spec(1);
		    		
		    		
		    		tfEditText[2] = (EditText) new EditText(getActivity().getBaseContext());
		    		tfEditText[2].setHint("Wrong");
		    		tfEditText[2].setWidth(width);
		    		tfEditText[2].setId(1);
		    		tfEditText[2].setHintTextColor(Color.RED);
		    		tfEditText[2].setTextColor(Color.BLACK);
		    		tfEditText[2].setGravity(1);
		    		tfEditText[2].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    		responses_layout.addView(tfEditText[2], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    		
		    		titleTxtSpecColumn = GridLayout.spec(1, GridLayout.BASELINE);
		    		titleTxtSpecRow = GridLayout.spec(1);
		    		tfEditText[3] = new EditText(getActivity().getBaseContext());
		    		tfEditText[3].setHint("Wrong Reason");
		    		tfEditText[3].setWidth(width);
		    		tfEditText[3].setHintTextColor(Color.RED);
		    		tfEditText[3].setTextColor(Color.BLACK);
		    		tfEditText[3].setGravity(1);
		    		tfEditText[3].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    		responses_layout.addView(tfEditText[3], new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    		Toast.makeText(getActivity(), "Scroll left/right for anwers/feedback", Toast.LENGTH_LONG).show();
		    		
		    	}
		    	else if(text.equals("Short Answer")){
		    		questionTypeMarker = "SA";
		    		responses_layout.removeAllViews();

		    		GridLayout.Spec titleTxtSpecColumn = GridLayout.spec(0, GridLayout.BASELINE);
		    		GridLayout.Spec titleTxtSpecRow = GridLayout.spec(0);		    		
		    		
		    		saEditText = (EditText) new EditText(getActivity().getBaseContext());
		    		saEditText.setHint("Answer");
		    		saEditText.setId(1);
		    		saEditText.setHintTextColor(Color.GREEN);
		    		saEditText.setTextColor(Color.BLACK);
		    		saEditText.setGravity(1);
		    		saEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    		responses_layout.addView(saEditText, new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));

		    	}
		    	else{
		    		questionTypeMarker = "PR";
		    		responses_layout.removeAllViews();
		    		
		    		GridLayout.Spec titleTxtSpecColumn;
		    		GridLayout.Spec titleTxtSpecRow;
		    		
		    		int counter = 0;
		    		for(int i=0 ; i<3 ; i++){
			    		titleTxtSpecColumn = GridLayout.spec(0, GridLayout.BASELINE);
			    		titleTxtSpecRow = GridLayout.spec(i);
		    			prEditText[counter] = new EditText(getActivity().getBaseContext());
		    			prEditText[counter] = new EditText(getActivity().getBaseContext());
		    			prEditText[counter].setHint("Input");
		    			prEditText[counter].setWidth(width);
		    			prEditText[counter].setTextColor(Color.BLACK);
		    			prEditText[counter].setGravity(1);
		    			prEditText[counter].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    			responses_layout.addView(prEditText[counter],new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));	
		    		
		    			counter++;
		    			
			    		titleTxtSpecColumn = GridLayout.spec(1, GridLayout.BASELINE);
			    		titleTxtSpecRow = GridLayout.spec(i);
		    			prEditText[counter] = new EditText(getActivity().getBaseContext());
		    			prEditText[counter].setHint("Output");
		    			prEditText[counter].setWidth(width);
		    			prEditText[counter].setTextColor(Color.BLACK);
		    			prEditText[counter].setGravity(1);
		    			prEditText[counter].setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		    			responses_layout.addView(prEditText[counter],new GridLayout.LayoutParams(titleTxtSpecRow, titleTxtSpecColumn));
		    			counter++;
		    		}

		    		Toast.makeText(getActivity(), "Scroll left/right for anwers/feedback", Toast.LENGTH_LONG).show();
		    		
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
	public void onResume(){
		super.onResume();		
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
	
//===============================================================================================================================	
	
	public class questionSubmit extends AsyncTask<String, Void, String> {
	    @Override
    	protected String doInBackground(String... urls) 
    	{
	    	session = new Login();
    		String response = null;	         
	         
			//PARAMETERS TO SEND
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("token", session.getToken());
			
			params.put("question_type", urls[0]);
			params.put("question", urls[1]);
			params.put("points", urls[2]);
			params.put("responses", urls[3]);
			params.put("tag", urls[4]);

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
	}//END ASYNC CLASS 
}

