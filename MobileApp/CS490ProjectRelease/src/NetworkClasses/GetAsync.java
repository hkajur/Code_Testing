package NetworkClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class GetAsync extends AsyncTask<String, Void, String> {
	private Login session = new Login();
	
	@Override
	protected String doInBackground(String... urls) 
	{
		try {
			return getUrl(urls[0],urls[1], urls[2]);
		}
		catch (IOException e){
			return "Cannot connect. Server is not responding.";
		}
	}
	@Override
	protected void onPostExecute(String result) 
	{

	}
	
	private String getUrl(String myurl,String user_id, String tag) throws IOException {
		String[] responseArr = null;	         
		String response =""; 
		
		Map<String, String> params = new HashMap<String, String>();				   
		params.put("user", user_id);
		params.put("tag", tag);
		params.put("token", session.getToken());
		
		try {
			Streamer.sendGetRequest(myurl);
			responseArr = Streamer.readMultipleLinesRespone();
		} catch (IOException ex) {
			Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
			ex.printStackTrace();
		}
		for(String i : responseArr){
			response+=i;
		}
		Streamer.disconnect();	        
		return response;
	}//END downloadUrl FUNCTION
}//END ASYNC CLASS