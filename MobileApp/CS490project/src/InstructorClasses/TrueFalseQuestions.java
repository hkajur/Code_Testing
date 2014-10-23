package InstructorClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs490project.AuthenticatorConnection;
import com.example.cs490project.R;

public class TrueFalseQuestions extends Fragment{

	EditText question;
	EditText answer1;	//Correct
	EditText reason1;	//Correct
	
	EditText answer2;
	EditText reason2;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_add_tf_questions, container, false);
		
		Button submit = (Button) view.findViewById(R.id.button1);
		   submit.setOnClickListener(new OnClickListener()
		   {
			   @Override
			   public void onClick(View v)
			   {
				   String response = null;	         
			         
				   //PARAMETERS TO SEND
				   Map<String, String> params = new HashMap<String, String>();				   
				   params.put("tag", "TrueFalseChoiceQuestionInsert");
				   params.put("question_type", "TrueFalse");
				   params.put("question", question.toString());
				   params.put("correct", answer1.toString());
				   params.put("correct_reason", reason1.toString());
				   params.put("wrongAnswer1", answer2.toString());
				   params.put("wrongReason1", reason2.toString());
				   
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
				   question = (EditText) getView().findViewById(R.id.TFQuestion);
				   answer1 = (EditText) getView().findViewById(R.id.TFeditText1); //Correct
				   reason1 = (EditText) getView().findViewById(R.id.TFeditText2);	//Correct
						
				   answer2 = (EditText) getView().findViewById(R.id.MCeditText3);
				   reason2 = (EditText) getView().findViewById(R.id.MCeditText4);
				   
				   question.setText("");
				   answer1.setText("");
				   reason1.setText("");
					
				   answer2.setText("");
				   reason2.setText("");
			   } 
		   });
		
		return view;
	}

}
