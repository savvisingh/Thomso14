package in.co.sdslabs.thomso14;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Event_tab_pager_adapter  extends FragmentPagerAdapter {

	final int PAGE_COUNT = 3;

	 public Event_tab_pager_adapter (FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		//Bundle data = new Bundle();
		switch (arg0) {

		/** tab1 is selected */
		case 0:
			Fragment formals = new Formals();
			return formals;
		case 1:
			Pronites pronites =new Pronites();
			return pronites;
			/** tab2 is selected */
		case 2:
			Informals informals =new Informals();
			return informals;
		
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
		
		if(position==0){return "Formals";}
		if(position==1){return "ProNites";}
		if(position==2){return "InFormals";}
		
		return null;
	}
}
