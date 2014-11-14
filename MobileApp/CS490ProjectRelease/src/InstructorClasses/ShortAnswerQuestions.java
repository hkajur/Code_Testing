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

public class ShortAnswerQuestions extends Fragment{
	
	EditText question;
	EditText input1;
	EditText output1;
	EditText input2;
	EditText output2;
	EditText input3;
	EditText output3;

	String user_id;
	
	public static Login session = new Login();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_add_short_questions, container, false);

		question = (EditText) view.findViewById(R.id.ShortEditText1);
		input1   = (EditText) view.findViewById(R.id.SHeditText1);
		output1  = (EditText) view.findViewById(R.id.SHeditText2);
		input2	 = (EditText) view.findViewById(R.id.SHeditText3); 
		output2  = (EditText) view.findViewById(R.id.SHeditText4);
		input3	 = (EditText) view.findViewById(R.id.SHeditText5); 
		output3	 = (EditText) view.findViewById(R.id.SHeditText6);
		
		
    	Bundle args = getArguments();
    	user_id = args.getString("USER_ID");
    	
    	//DEBUGGING NETWORK CONNECTION
/*------==============================================================================================================
    			ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

    			if (networkInfo != null && networkInfo.isConnected()) {
    				Toast.makeText(getActivity().getBaseContext(), "HOUSTON LANDED", Toast.LENGTH_LONG).show();
    			} else {
    				Toast.makeText(getActivity().getBaseContext(), "HOUSTON FUCKED UP", Toast.LENGTH_LONG).show();
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
				input1.setText("");
				output1.setText("");
				input2.setText("");
				output2.setText("");
				input3.setText("");
				output3.setText("");
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
		    params.put("tag", "ShortAnswerQuestionInsert");
		    params.put("question_type", "ShortAnswer");
		    params.put("question", question.toString());
		    params.put("input1", input1.toString());
		    params.put("output1",output1.toString());
		    params.put("input2",input2.toString()); 
		    params.put("output2",output2.toString());
		    params.put("input3",input3.toString()); 
		    params.put("output3",output3.toString());

	        //TRY TO POST PARAMETERS TO SERVER AND GET RESPONSE
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
