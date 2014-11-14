package InstructorClasses;

import com.malan.cs490project.R;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

public class InstructorFragmentTab3 extends Fragment {
	
//	private Spinner choice;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
        View view = inflater.inflate(R.layout.fragment_instructor_tab3, container, false);
        return view;

        /*
		View view = inflater.inflate(R.layout.fragment_instructor_tab3, container, false);

    	Bundle args = getArguments();
    	
        final String user_id = args.getString("USER_ID");
    	final String user_name = args.getString("USER_NAME");
    	
		choice = (Spinner) view.findViewById(R.id.SpinnerFeedbackType);
		
		choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			
			android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
	    	android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {		    				
		    	String text = choice.getSelectedItem().toString();

		    	MultipleChoiceQuestions MCfragment = new MultipleChoiceQuestions();
		    	TrueFalseQuestions TFfragment = new TrueFalseQuestions();
		    	ShortAnswerQuestions SHfragment = new ShortAnswerQuestions();
		    	EmptyForm empty = new EmptyForm(); 		    	
		    	
				Bundle args = new Bundle();
				args.putString("USER_ID", user_id);				
				
	            
	            MCfragment.setArguments(args);
	            TFfragment.setArguments(args);
	            SHfragment.setArguments(args);
		    	
	            fragmentManager = getFragmentManager();
		    	fragmentTransaction = fragmentManager.beginTransaction();
	            
		    	if (text.equals("Multiple Choice")){		    		
		    		fragmentTransaction.replace(R.id.fragmentContainer, MCfragment);		    		
		    	}
		    	else if (text.equals("True/False")){
		    		fragmentTransaction.replace(R.id.fragmentContainer, TFfragment);
		    	}
		    	else if(text.equals("Programming Question")){
		    		fragmentTransaction.replace(R.id.fragmentContainer, SHfragment);
		    	}
		    	else{
		    		fragmentTransaction.replace(R.id.fragmentContainer,null);
		    	}
		    	fragmentTransaction.commit();
		    	
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    	fragmentTransaction.replace(R.id.fragmentContainer,null);
		    	fragmentTransaction.commit();
		    }
		});
		return view;
		*/
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
	
}

