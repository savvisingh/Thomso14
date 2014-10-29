package in.co.sdslabs.thomso14;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 4;

	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		//Bundle data = new Bundle();
		switch (arg0) {

		/** tab1 is selected */
		case 0:
			Day0 day0 = new Day0();
			return day0;
		case 1:
			Day1 day1 = new Day1();
			return day1;
			/** tab2 is selected */
		case 2:
			Day2 day2 = new Day2();
			return day2;
		case 3:
			Day3 day3 = new Day3();
			return day3;
		}
		return null;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGE_COUNT;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		
		if(position==0){return "Day 0 (Oct 30)";}
		if(position==1){return "Day 1 (Oct 31)";}
		if(position==2){return "Day 2 (Nov 1)";}
		if(position==3){return "Day 3 (Nov 2)";}
		return null;
	}
}
