package InstructorClasses;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malan.cs490project.R;

@SuppressLint("NewApi")
public class EmptyForm extends Fragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_empty, container, false);
		return view;
	}

}
