package StudentClasses;

import com.malan.cs490project.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StudentFragmentTab3 extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		
        View view = inflater.inflate(R.layout.fragment_student_tab3, container, false);
        return view;

        /*
		View view = inflater.inflate(R.layout.fragment_student_tab3, container, false);
		
		Bundle args = getArguments();
		
		TextView name = (TextView) view.findViewById(R.id.textView1);
		
		name.setText(args.get("USER_NAME").toString());
		
		return view;
		*/
	}
}