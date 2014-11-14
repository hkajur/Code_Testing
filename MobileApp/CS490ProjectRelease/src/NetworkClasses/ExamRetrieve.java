package NetworkClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class ExamRetrieve {
	
	private String examID;
	private String userID;
	private Streamer connection;
	private Login session;	         
	private String response = ""; 

	
	public ExamRetrieve(){
		examID = "0";
		userID = "";
		session = new Login();		
	}

	public ExamRetrieve(String examid, String userid){
		examID = examid;
		userID = userid;
		session = new Login();				
	}
	
	public String getResponse() throws IOException{
		this.postUrl();
		return response;
	}
	
	
	public void postUrl() throws IOException {
		String[] responseArr = null;
		
		Map<String, String> params = new HashMap<String, String>();				   
		params.put("userID", userID);
		params.put("examID", examID);
		params.put("tag", "conductExams");
		params.put("token", session.getToken());
		
		try {
			Streamer.sendPostRequest("http://afsaccess1.njit.edu/~vk255/Code_Testing/MiddleEnd/studentExams.php", params);
			responseArr = Streamer.readMultipleLinesRespone();
		} catch (IOException ex) {
			Log.w("INTERNET CONNECTIVITY", "Could not connect to server");
			ex.printStackTrace();
		}
		for(String i : responseArr){
			response+=i;
		}
		Streamer.disconnect();	        
	}

	
}

