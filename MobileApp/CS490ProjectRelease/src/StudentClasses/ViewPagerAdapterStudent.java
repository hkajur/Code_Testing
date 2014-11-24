package StudentClasses;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class ViewPagerAdapterStudent extends FragmentPagerAdapter {
 
	final int PAGE_COUNT = 2;
    // Tab Titles
    private String tabtitles[] = new String[] { "Outstanding", "Past Exams" };
    Context context;
 
    public ViewPagerAdapterStudent(FragmentManager fm) {
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
        	StudentFragmentTab1 fragmenttab1 = new StudentFragmentTab1();
            return fragmenttab1;
 
            // Open FragmentTab2.java
        case 1:
        	StudentFragmentTab2 fragmenttab2 = new StudentFragmentTab2();
            return fragmenttab2;
 
            /* Open FragmentTab3.java USELESS
        case 2:
        	StudentFragmentTab3 fragmenttab3 = new StudentFragmentTab3();
            return fragmenttab3;
            */
        }
        return null;
    }
 
    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}