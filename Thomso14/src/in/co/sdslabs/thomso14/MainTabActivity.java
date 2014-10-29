package in.co.sdslabs.thomso14;



import in.co.sdslabs.thomso14.R;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainTabActivity extends ActionBarActivity {

	private ViewPager mPager;
	ActionBar mActionBar;
	public static int day;
    PagerTabStrip pts;
    TextView yourTextView;
    Typeface font;
	public MainTabActivity() {
		// TODO Auto-generated constructor stub
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_layout);
		  
		 
		 
		int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		   yourTextView = (TextView)findViewById(titleId); 
		 
		  font= Typeface.createFromAsset(getAssets(), "27990.ttf");
		  yourTextView.setTypeface(font);
		  yourTextView.setTextSize(28);
		/** Getting a reference to action bar of this activity */
		mActionBar = getSupportActionBar();
		 mActionBar.setBackgroundDrawable(new ColorDrawable(0xff000000));
		mActionBar.setTitle(" ");
        pts = (PagerTabStrip)findViewById(R.id.pager_title_strip);
		
		pts.setClickable(true);
		pts.setTextColor(Color.WHITE);
		pts.setTextSize(2, 16);
		
		mPager = (ViewPager) findViewById(R.id.pager);

	
		final FragmentManager fm = getSupportFragmentManager();

		MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(
				fm);

	
		mPager.setAdapter(fragmentPagerAdapter);
		char currItem = getIntent().getCharExtra("tab_selected", '2');
		if(currItem=='0'){mPager.setCurrentItem(0);}
		if(currItem=='1'){mPager.setCurrentItem(1);}
		if(currItem=='2'){mPager.setCurrentItem(2);}
		if(currItem=='3'){mPager.setCurrentItem(3);}
		if(currItem=='4'){mPager.setCurrentItem(4);}
		
		
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
	   
	      
	    case R.id.home_button:
		    Intent home = new Intent(this,MainNavDrawerActivity.class);
		    
		    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(home);
			break;
		
	    case R.id.map:
			// TODO : Fire intent for full map and not the zoomed in view
			Intent goToMap = new Intent(this, MapTest.class);
			startActivity(goToMap);
			break;
			
	    case R.id.filter:
			finish();
			Intent i = new Intent(this, Events_Tab_Activity.class);
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
		getMenuInflater().inflate(R.menu.menu_tab, menu);
		return true;
	}

	
	

}