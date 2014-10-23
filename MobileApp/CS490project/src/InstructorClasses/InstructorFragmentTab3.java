package InstructorClasses;

import com.example.cs490project.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class InstructorFragmentTab3 extends Fragment {
	
	private Spinner choice;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_instructor_tab3, container, false);
		choice = (Spinner) view.findViewById(R.id.SpinnerFeedbackType);
		
		choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {		    				
		    	String text = choice.getSelectedItem().toString();
		    	FragmentManager fragmentManager = getFragmentManager();
		    	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		    	
		    	MultipleChoiceQuestions MCfragment = new MultipleChoiceQuestions();
		    	TrueFalseQuestions TFfragment = new TrueFalseQuestions();
		    	ShortAnswerQuestions SHfragment = new ShortAnswerQuestions();
		    	EmptyForm empty = new EmptyForm(); 
		    	
		    	fragmentTransaction.replace(R.id.fragmentContainer, empty);
		    	
		    	if (text.equals("Multiple Choice")){		    		
		    		fragmentTransaction.replace(R.id.fragmentContainer, MCfragment);		    		
		    	}
		    	else if (text.equals("True/False")){
		    		fragmentTransaction.replace(R.id.fragmentContainer, TFfragment);
		    	}
		    	else if(text.equals("Short Answer")){
		    		fragmentTransaction.replace(R.id.fragmentContainer, SHfragment);
		    	}
		    	else{
		    		fragmentTransaction.replace(R.id.fragmentContainer,empty);
		    	}
		    	fragmentTransaction.commit();
		    	/*
		    	MultipleChoiceQuestions newFragment = new MultipleChoiceQuestions();
		    	Bundle args = new Bundle();
		    	args.putInt(MultipleChoiceQuestions.ARG_POSITION, position);
		    	newFragment.setArguments(args);

		    	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		    	// Replace whatever is in the fragment_container view with this fragment,
		    	// and add the transaction to the back stack so the user can navigate back
		    	transaction.replace(R.id.fragment_container, newFragment);
		    	transaction.addToBackStack(null);

		    	// Commit the transaction
		    	transaction.commit();
		    	
		    	*/
		    	
		    }
		    public void onNothingSelected(AdapterView<?> parent) {

		    }
		});
		return view;
	}	
	
}
