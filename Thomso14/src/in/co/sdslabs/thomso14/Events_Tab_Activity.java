package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Events_Tab_Activity extends ActionBarActivity {

	private ViewPager mPager;
	ActionBar mActionBar;
	public static int day;
    PagerTabStrip pts;
    TextView yourTextView;
    Typeface font;
	 public Events_Tab_Activity () {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_layout);

		/** Getting a reference to action bar of this activity */
		mActionBar = getSupportActionBar();
		mActionBar.setTitle(" ");
		int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		   yourTextView = (TextView)findViewById(titleId); 
		 
		  font= Typeface.createFromAsset(getAssets(), "27990.ttf");
		  yourTextView.setTypeface(font);
		  yourTextView.setTextSize(28);
		/** Getting a reference to action bar of this activity */
		
		 mActionBar.setBackgroundDrawable(new ColorDrawable(0xff000000));
		
        pts = (PagerTabStrip)findViewById(R.id.pager_title_strip);
		
		pts.setClickable(true);
		pts.setTextColor(Color.WHITE);
		pts.setTextSize(2, 16);
		
		mPager = (ViewPager) findViewById(R.id.pager);

	
		final FragmentManager fm = getSupportFragmentManager();

		Event_tab_pager_adapter fragmentPagerAdapter = new Event_tab_pager_adapter(
				fm);

	
		mPager.setAdapter(fragmentPagerAdapter);
		
		mPager.setCurrentItem(1);
		
		
		ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				//Log.d(null, Integer.toString(arg2));
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				//Log.d(null, "onpagescroll state changed");
				
			}
		};

		/** Setting the pageChange listener to the viewPager */
		mPager.setOnPageChangeListener(pageChangeListener);

		/** Creating an instance of FragmentPagerAdapter */
	

		mActionBar.setDisplayShowTitleEnabled(true);
	

		
	
	
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	    	
			super.onBackPressed();
	        return true;
        case R.id.home_button:
	    Intent home = new Intent(this,MainNavDrawerActivity.class);
	    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home);
		break;
	        
	    case R.id.filter:
			
			Intent i = new Intent(this, MainTabActivity.class);
			startActivity(i);
			break;
		
		case R.id.contact:

			// TODO : Fire intent for contacts activity
			Intent gotocontacts = new Intent(this, ContactListActivity.class);
			startActivity(gotocontacts);
			break;
		case R.id.about_us:
			
			Intent about_us = new Intent(this , AboutUs.class);
			startActivity(about_us);
			break;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events_menu, menu);
		return true;
	}

	
	

}
