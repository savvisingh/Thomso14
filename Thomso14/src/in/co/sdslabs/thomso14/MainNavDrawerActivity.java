package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainNavDrawerActivity extends ActionBarActivity {

	int mPosition = -1;
	String mTitle = "";
	DatabaseHelper myDbHelper;
	// Array of strings storing Event Category names
	static String[] mEventCategories;
	Intent service_intent;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private LinearLayout mDrawer;
	private List<HashMap<String, String>> mList;
	private SimpleAdapter mAdapter;
	final private String EVENTCATEGORY = "eventcategory";
	final private String IMAGE = "image";
	private HomeFragment hFragment;
	private UpcomingEvents uFragment;
	FragmentManager fragmentManager;
	FragmentTransaction ft;
	
	Typeface font;
	 TextView yourTextView;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addHomeFragment();
		 ActionBar bar = getActionBar();
		  bar.setBackgroundDrawable(new ColorDrawable(0xff000000));
		 bar.setTitle(" ");
		  int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		   yourTextView = (TextView)findViewById(titleId); 
		  yourTextView.setTextColor(Color.parseColor("#ffffff"));
		  font= Typeface.createFromAsset(getAssets(), "27990.ttf");
		  yourTextView.setTypeface(font);
		  yourTextView.setTextSize(28);
		  
		 
  String[] event_images={
		  String.valueOf(R.drawable.favdrawer),
				  String.valueOf(R.drawable.ongoingdrawer),
						  String.valueOf( R.drawable.day0drawer),
								  String.valueOf(R.drawable.day1drawer),
										  String.valueOf(R.drawable.day2drawer 
												 ),
												  String.valueOf(R.drawable.day3drawer),
														  String.valueOf(R.drawable.eventsdrawer)};
		  
		// Getting an array of country names
		mEventCategories = getResources().getStringArray(
				R.array.eventCategories);

		// Title of the activity
		mTitle = (String) getTitle();

		// Getting a reference to the drawer listview
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// Getting a reference to the sidebar drawer ( Title + ListView )
		mDrawer = (LinearLayout) findViewById(R.id.drawer);

		// Each row in the list stores country name, count and flag
		mList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < mEventCategories.length; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put(EVENTCATEGORY, mEventCategories[i]);
			hm.put(IMAGE, event_images[i]);
			mList.add(hm);
		}

		// Keys used in Hashmap
		String[] from = { IMAGE };

		// Ids of views in listview_layout
		int[] to = { R.id.image };

		// Instantiating an adapter to store each items
		// R.layout.drawer_layout defines the layout of each item
		mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from,
				to);

		// Getting reference to DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Creating a ToggleButton for NavigationDrawer with drawer event
		// listener
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			
			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {
				highlightSelectedEventCategory();
				// getSupportActionBar().setTitle("Cognizance 2014");
				getSupportActionBar().setTitle(" ");
				yourTextView.setTextSize(25);
				supportInvalidateOptionsMenu();
			}

			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {
				 
				
				supportInvalidateOptionsMenu();
			}
		};

		// Setting event listener for the drawer
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// ItemClick event handler for the drawer items
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				// Show fragment for eventCategories
				showFragment(position);

				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawer);
			}
		});

		// Enabling Up navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		// Changing the background of the color drawable
		// getSupportActionBar().setBackgroundDrawable(
		// new ColorDrawable(Color.rgb(234, 234, 234)));
		// Setting the adapter to the listView
		mDrawerList.setAdapter(mAdapter);
	    
		Handler handler = new Handler(); 
	    handler.postDelayed(new Runnable() { 
	         public void run() { 
	        	 mDrawerLayout.openDrawer(mDrawer);     
	         } 
	    }, 2000); 
		
		
	      service_intent=new Intent(getApplicationContext(),StartService.class);		
		 
	      startService(service_intent);	 
	 
	     
	      
	   
	}

	private void addHomeFragment() {

		// initialize the HomeFragment
		hFragment = new HomeFragment();

		// Getting reference to the FragmentManager
		fragmentManager = getSupportFragmentManager();

		// Creating a fragment transaction
		ft = fragmentManager.beginTransaction();

		// Adding a fragment to the fragment transaction
		ft.replace(R.id.content_frame, hFragment);
		// Committing the transaction
		ft.commit();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_navdrawer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// super.onOptionsItemSelected(item);

		if (mDrawerToggle.onOptionsItemSelected(item)) {

		}
		switch (item.getItemId()) {

		
		
		case R.id.map:
			// TODO : Fire intent for full map and not the zoomed in view
			Intent goToMap = new Intent(this, MapTest.class);
			startActivity(goToMap);
			break;
		case R.id.contact:

			// TODO : Fire intent for contacts activity
			Intent gotocontacts = new Intent(this, ContactListActivity.class);
			startActivity(gotocontacts);
			break;
		case R.id.about_us:

			Intent about_us = new Intent(this, AboutUs.class);
			startActivity(about_us);
			break;
		case R.id.sponsors:
			Intent spons = new Intent(this,Sponsors.class);
			startActivity(spons);
			break;

		// case R.id.home :
		// fragmentManager.popBackStack();
		}
		return true;
	}

	public void showFragment(int position) {

		// Currently selected eventCategory
		mTitle = mEventCategories[position];

		myDbHelper = new DatabaseHelper(this);
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myDbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		if (position == 0) {

			ArrayList<String> eventname = myDbHelper.getFavouritesName();
			Log.i("event.size()",String.valueOf(eventname.size()));
			if (eventname.size() == 0) {
				Toast.makeText(this, "There are no current Favourites",
						Toast.LENGTH_SHORT).show();
			} else {
				
				CustomListFragment eFragment = new CustomListFragment();
				// Getting reference to the FragmentManager
				
				fragmentManager = getSupportFragmentManager();
				
				FragmentTransaction ft = fragmentManager.beginTransaction();
			
				// Adding a fragment to the fragment transaction
				ft.replace(R.id.content_frame, eFragment);
				ft.addToBackStack(null);
				
				ft.commit();
				
			}
			   }
			 else if (position == 1) {

			
					Calendar c = Calendar.getInstance();
					int date =c.get(Calendar.DAY_OF_MONTH);
					 Log.i("date",String.valueOf(date) );
					int day=4; 
					 switch (date) {
					 case 30:
							day=0;
							break; 
					case 31:
						day=1;
						break;
					case 1:
						day=2;
						break;
					case 2:
						day=3;
						break;
					default:
						break;
					}
				if (day ==4) {Toast.makeText(this, "There are no Ongoing Evnets",
						Toast.LENGTH_SHORT).show();	}
				if(day<4){
					 ArrayList<String> eventname; 
					 eventname=myDbHelper.getOnGoingEventNames(day);
				 if(eventname.size()==0){
					 Toast.makeText(this, "There are no Ongoing Evnets",
								Toast.LENGTH_SHORT).show();
				 }
				 else{
				 OngoingEvents eFragment = new OngoingEvents();
					// Getting reference to the FragmentManager
					
					fragmentManager = getSupportFragmentManager();
					
					FragmentTransaction ft = fragmentManager.beginTransaction();
				
					// Adding a fragment to the fragment transaction
					ft.replace(R.id.content_frame, eFragment);
					ft.addToBackStack(null);
					
					ft.commit();
				 }
				}
		}
			 else if (position == 2) {

				
					Intent intent = new Intent(this, MainTabActivity.class);
					 intent.putExtra("tab_selected",'0');
					startActivity(intent);
					
				}
			 else if (position == 3) {

					
					Intent intent = new Intent(this, MainTabActivity.class);
					 intent.putExtra("tab_selected",'1');
					startActivity(intent);
					
				}
			 
			 else if (position == 4) {

					
					Intent intent = new Intent(this, MainTabActivity.class);
					 intent.putExtra("tab_selected",'2');
					startActivity(intent);
					
				} else if (position == 5) {

					Intent intent = new Intent(this, MainTabActivity.class);
					 intent.putExtra("tab_selected",'3');
					startActivity(intent);
					
					
					
				} else if (position == 6) {

					
					Intent intent = new Intent(this, Events_Tab_Activity.class);
					 
					startActivity(intent);
					
					
				}
			 
			 

	}

	// Highlight the selected eventCategory
	public void highlightSelectedEventCategory() {

		mDrawerList.setItemChecked(mPosition, true);
		if (mPosition != -1)
			getSupportActionBar().setTitle(mEventCategories[mPosition]);
	}

	
}
