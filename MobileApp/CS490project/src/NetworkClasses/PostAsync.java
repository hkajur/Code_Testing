package NetworkClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class PostAsync extends AsyncTask<String, Void, String> {
	private loginFunctions session = new loginFunctions();
	
	@Override
	protected String doInBackground(String... urls) 
	{
		try {
			return postUrl(urls[0],urls[1], urls[2]);
		}
		catch (IOException e){
			return "Cannot connect. Server is not responding.";
		}
	}
	@Override
	protected void onPostExecute(String result) 
	{

	}
	
	private String postUrl(String myurl,String user_id, String tag) throws IOException {
		String[] responseArr = null;	         
		String response =""; 
		
		Map<String, String> params = new HashMap<String, String>();				   
		params.put("user", user_id);
		params.put("tag", tag);
		params.put("token", session.getToken());
		
		try {
			AuthenticatorConnection.sendPostRequest(myurl, params);
			responseArr = AuthenticatorConnection.readMultipleLinesRespone();
		} catch (IOException ex) {
			Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
			ex.printStackTrace();
		}
		for(String i : responseArr){
			response+=i;
		}
		AuthenticatorConnection.disconnect();	        
		return response;
	}//END downloadUrl FUNCTION
}//END ASYNC CLASS