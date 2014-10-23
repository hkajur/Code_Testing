package InstructorClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs490project.AuthenticatorConnection;
import com.example.cs490project.R;

public class MultipleChoiceQuestions extends Fragment{

	EditText question;
	EditText answer1;	//Correct
	EditText reason1;	//Correct
	
	EditText answer2;
	EditText reason2;
	EditText answer3;
	EditText reason3;
	EditText answer4;
	EditText reason4;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_add_mc_questions, container, false);
		
		question = (EditText) view.findViewById(R.id.MCQuestion);
		answer1 = (EditText) view.findViewById(R.id.MCeditText1); //Correct
		reason1 = (EditText) view.findViewById(R.id.MCeditText2);	//Correct
		
		answer2 = (EditText) view.findViewById(R.id.MCeditText3);
		reason2 = (EditText) view.findViewById(R.id.MCeditText4);
		answer3 = (EditText) view.findViewById(R.id.MCeditText5);
		reason3 = (EditText) view.findViewById(R.id.MCeditText6);
		answer4 = (EditText) view.findViewById(R.id.MCeditText7);
		reason4 = (EditText) view.findViewById(R.id.MCeditText8);
		

		Button submit = (Button) view.findViewById(R.id.button1);
		   submit.setOnClickListener(new OnClickListener()
		   {
			   @Override
			   public void onClick(View v)
			   {
				   String response = null;	         
				         
				   //PARAMETERS TO SEND
				   Map<String, String> params = new HashMap<String, String>();				   
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
					   AuthenticatorConnection.sendPostRequest("http://web.njit.edu/~dm282/cs490/index.php", params);
//					   response = AuthenticatorConnection.readSingleLineRespone();
					   Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
				   } catch (IOException ex) {
					   ex.printStackTrace();
				   }
				        //CLOSE CONNECTION AND RETURN THE JSON REPONSE
				   AuthenticatorConnection.disconnect();
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
					answer3.setText("");
					reason3.setText("");
					answer4.setText("");
					reason4.setText("");
				}	 
			}); 
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onActivityCreated(savedInstanceState);


	}
	
}