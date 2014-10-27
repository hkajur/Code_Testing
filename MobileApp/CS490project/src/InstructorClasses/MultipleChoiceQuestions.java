package InstructorClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import NetworkClasses.AuthenticatorConnection;
import NetworkClasses.loginFunctions;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cs490project.R;


public class MultipleChoiceQuestions extends Fragment{

	//GLOBAL VARIABLES
	EditText question;
	EditText answer1;	//Correct
	EditText reason1;	//Correct
	
	EditText answer2;
	EditText reason2;
	EditText answer3;
	EditText reason3;
	EditText answer4;
	EditText reason4;
	
	String user_id;
	
	public static loginFunctions session = new loginFunctions();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		//INFLATE CURRENT VIEW WITH THIS FRAGMENT
		View view = inflater.inflate(R.layout.fragment_add_mc_questions, container, false);
		
		//COLLECT ALL FORM ELEMENTS WE'RE LOOKING FOR 
		question = (EditText) view.findViewById(R.id.MCQuestion);
		answer1 = (EditText) view.findViewById(R.id.MCeditText1); //Correct
		reason1 = (EditText) view.findViewById(R.id.MCeditText2);	//Correct
		
		answer2 = (EditText) view.findViewById(R.id.MCeditText3);
		reason2 = (EditText) view.findViewById(R.id.MCeditText4);
		answer3 = (EditText) view.findViewById(R.id.MCeditText5);
		reason3 = (EditText) view.findViewById(R.id.MCeditText6);
		answer4 = (EditText) view.findViewById(R.id.MCeditText7);
		reason4 = (EditText) view.findViewById(R.id.MCeditText8);
		
		
		//GET USER CREDENTIALS FROM main_activity
    	Bundle args = getArguments();
    	user_id = args.getString("USER_ID");

    	//DEBUGGING NETWORK CONNECTION
/*------==============================================================================================================
		ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			question.setText("HOUSTON LANDED");
		} else {
			question.setText("HOUSTON FUCKED UP");
		}
//------==============================================================================================================*/    	

		//SUBMIT BUTTOM SMACKED
		Button submit = (Button) view.findViewById(R.id.button1);
		submit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{			
		        new HttpAsyncTask().execute(session.getURL(), session.getToken());			
			}
		}); //END SUBMIT BUTTON
//--------------------------------------------------------------------------------------------------------------------		
		//CLEAR BUTTON SMACKED		   
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
				answer3.setText("");
				reason3.setText("");
				answer4.setText("");
				reason4.setText("");
			}	 
		}); //END CLEAR BUTTON
		return view;
	}//END onCreateView FUNCTION

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
			params.put("tag", "MultipleChoiceQuestionInsert");
			params.put("question_type", "MultipleChoice");

			params.put("question", question.toString());

			params.put("correct", answer1.toString());
			params.put("correct_reason", reason1.toString());

			params.put("wrongAnswer1", answer2.toString());
			params.put("wrongReason1", reason2.toString());
			params.put("wrongAnswer2", answer3.toString());
			params.put("wrongReason2", reason3.toString());
			params.put("wrongAnswer3", answer4.toString());
			params.put("wrongReason3", reason4.toString());	         
	        //TRY TO POST PARAMETERS TO SERVER AND GET RESPONSE
	        try {
	        	AuthenticatorConnection.sendPostRequest(myurl, params);
	            response = AuthenticatorConnection.readSingleLineRespone();
	        } catch (IOException ex) {
	        	Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
	            ex.printStackTrace();
	        }
	        //CLOSE CONNECTION AND RETURN THE JSON REPONSE
	        AuthenticatorConnection.disconnect();	        
			return response;
		}//END downloadUrl FUNCTION
	}//END ASYNC CLASS 
}//END CLASS