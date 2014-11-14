package InstructorClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import NetworkClasses.Streamer;
import NetworkClasses.Login;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.malan.cs490project.R;

public class TrueFalseQuestions extends Fragment{

	EditText question;
	EditText answer1;	//Correct
	EditText reason1;	//Correct
	
	EditText answer2;
	EditText reason2;

	String user_id;
	
	public static Login session = new Login();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_add_tf_questions, container, false);
		
		question = (EditText) view.findViewById(R.id.TFQuestion);
		answer1 = (EditText) view.findViewById(R.id.TFeditText1); //Correct
		reason1 = (EditText) view.findViewById(R.id.TFeditText2);	//Correct
		
		answer2 = (EditText) view.findViewById(R.id.TFeditText3);
		reason2 = (EditText) view.findViewById(R.id.TFeditText4);
		
		//GET USER CREDENTIALS FROM main_activity
    	Bundle args = getArguments();
    	user_id = args.getString("USER_ID");
		
		Button submit = (Button) view.findViewById(R.id.button1);
		submit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new HttpAsyncTask().execute(session.getURL(), session.getToken());
			} 
		});
		
		Button clear = (Button) view.findViewById(R.id.button2);
		clear.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				question.setText("");
				answer1.setText("");
				reason1.setText("");
				
				answer2.setText("");
				reason2.setText("");
			} 
		});
		
		return view;
	}

	@Override
	public void onStop() {
	    super.onStop();
	} 

	public class HttpAsyncTask extends AsyncTask<String, Void, String> {
	    @Override
    	protected String doInBackground(String... urls) 
    	{
    		try {
    			return downloadUrl(urls[0],urls[1]);
    		}
    		catch (IOException e){
    			return "Cannot connect. Server is not responding.";
    		}
    	}	    // onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) 
		{
			JSONObject response;
			Toast.makeText(getActivity().getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();
			try {
				response = new JSONObject(result);
				
				if(response.get("questionCreated").toString().equals("Success")){
					Toast.makeText(getActivity().getBaseContext(), "Question added", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getActivity().getBaseContext(), response.get("Error").toString(), Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				Toast.makeText(getActivity().getBaseContext(), "Server has not responded. Try again.", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
//############################################################################################################################################
	    private String downloadUrl(String myurl,String token) throws IOException {
		    String response = null;	         
	         
		    //PARAMETERS TO SEND
			Map<String, String> params = new HashMap<String, String>();				   
			params.put("user", user_id);
			params.put("token", token);
			params.put("tag", "TrueFalseChoiceQuestionInsert");
			params.put("question_type", "TrueFalse");

			params.put("question", question.toString());

			params.put("correct", answer1.toString());
			params.put("correct_reason", reason1.toString());

			params.put("wrongAnswer1", answer2.toString());
			params.put("wrongReason1", reason2.toString());	        //TRY TO POST PARAMETERS TO SERVER AND GET RESPONSE
	        try {
	        	Streamer.sendPostRequest(myurl, params);
	            response = Streamer.readSingleLineRespone();
	        } catch (IOException ex) {
	        	Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
	            ex.printStackTrace();
	        }
	        //CLOSE CONNECTION AND RETURN THE JSON REPONSE
	        Streamer.disconnect();	        
			return response;
		}//END downloadUrl FUNCTION
	}//END ASYNC CLASS 
}
