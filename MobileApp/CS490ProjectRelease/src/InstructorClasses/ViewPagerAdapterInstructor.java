package InstructorClasses;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class ViewPagerAdapterInstructor extends FragmentPagerAdapter {
 
	final int PAGE_COUNT = 3;
    // Tab Titles
    private String tabtitles[] = new String[] { "Exams", "Construct", "Add Questions" };
    Context context;
 
    public ViewPagerAdapterInstructor(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    
 
    @Override
    public Fragment getItem(int position) {
        switch (position) {
 
            // Open FragmentTab1.java
        case 0:
        	InstructorFragmentTab1 fragmenttab1 = new InstructorFragmentTab1();
            return fragmenttab1;
 
            // Open FragmentTab2.java
        case 1:
        	InstructorFragmentTab2 fragmenttab2 = new InstructorFragmentTab2();
            return fragmenttab2;
 
            // Open FragmentTab3.java
        case 2:
        	InstructorFragmentTab3 fragmenttab3 = new InstructorFragmentTab3();
            return fragmenttab3;
        }
        return null;
    }
 
    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}