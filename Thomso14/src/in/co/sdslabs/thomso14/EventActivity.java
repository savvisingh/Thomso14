package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;

public class EventActivity extends ActionBarActivity implements OnClickListener {

	public EventActivity() {
	}

	DatabaseHelper myDbHelper;
	Bundle b;
	boolean fav;
	GPSTracker gps;
	TextView on, off;
	String dept_name;
    ArrayList<String> contacts_name=null,contacts_number=null;
	boolean isDept = false;
    ListView contact_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
		 ActionBar bar = getActionBar();
		  bar.setBackgroundDrawable(new ColorDrawable(0xff000000));
		TextView eName = (TextView) findViewById(R.id.event_name);
		TextView eOneLiner = (TextView) findViewById(R.id.event_oneliner);
		TextView eDescription = (TextView) findViewById(R.id.event_description);
		TextView eDate = (TextView) findViewById(R.id.event_date);
		TextView eTime = (TextView) findViewById(R.id.event_time);
		TextView eVenue = (TextView) findViewById(R.id.event_venue);
		TextView eContact = (TextView) findViewById(R.id.event_contact);
		
		ImageView eventIcon = (ImageView) findViewById(R.id.event_ImView);

		CheckBox star = (CheckBox) findViewById(R.id.star);

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

		b = getIntent().getExtras();

		try {
			isDept = b.getBoolean("dept");
			Log.i("bool",isDept+"");
			Log.i("event fetch : ",b.getString("event"));
		} catch (Exception e) {
			Log.i("event fetch : ",e.toString());
		}

		 

			if (myDbHelper.isFavourite(b.getString("event"))) {
				fav = true;
				star.setChecked(true);
			} else {
				fav = false;
				star.setChecked(false);
			}

			star.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {

					if (fav) {
						myDbHelper.unmarkAsFavourite(b.getString("event"));
						
					} else {
						myDbHelper.markAsFavourite(b.getString("event"));
						
						
						Intent  service_intent=new Intent(getApplicationContext(),StartService.class);		
							 
					      startService(service_intent);	 		
					}
				}
			});

			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setTitle(b.getString("event"));
		    Typeface font= Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");
			eName.setText(b.getString("event"));
			eName.setTypeface(font);
			eOneLiner
					.setText(myDbHelper.getEventOneLiner(b.getString("event")));
			eDescription.setText(myDbHelper.getEventDescription(b
					.getString("event")));
			
			 eDescription.setTypeface(font);
			int x = myDbHelper.getImageX(b.getString("event"));
			int y = myDbHelper.getImageY(b.getString("event"));
			Log.v("Image", "x :" + x);
			Log.v("Image", "y :" + y);

			
			eDate.setText("DATE : "
					+ myDbHelper.getEventDate(b.getString("event")));
			eDate.setTypeface(font);
			eTime.setText("TIME : " + setTime(b.getString("event")));
			eTime.setTypeface(font);
			eVenue.setTextColor(Color.rgb(1, 140, 149));
			eVenue.setText("VENUE : "
					+ myDbHelper.getVenueDisplay(b.getString("event")));
			eVenue.setTypeface(font);
			Log.i("event",b.getString("event"));
			contacts_name=myDbHelper.getEventContactsName(b.getString("event"));
		    contacts_number=myDbHelper.getEventContactsNumber(b.getString("event"));
		   
		    Log.i("contacts_name.size()", String.valueOf(contacts_name.size()));
		   
		    EventsContactAdapter madapter=new EventsContactAdapter(contacts_name, contacts_number, getApplicationContext());
		    Log.i("error", "checkpoint 3");
		    Log.i("error", "checkpoint 2");
		   // contact_list=(ListView)findViewById(R.id.contact_list);
		   // contact_list.setAdapter(madapter);
		    
		    Log.i("error", "checkpoint 4");
		     
		     
		     eVenue.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("venue1 : ", b.getString("event"));
					if (b.getString("event").contentEquals("Sciennovate"))
						showZoomedMap("Main Building");
					else
						showZoomedMap(myDbHelper.getVenueMap(b
								.getString("event")));
				}
			});
		     
		  eContact.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				final Dialog dialog = new Dialog(EventActivity.this);

				dialog.setTitle("Contact");
			    View view = getLayoutInflater().inflate(R.layout.custom_list_dialog, null);

			    ListView lv = (ListView) view.findViewById(R.id.custom_list);

			    // Change MyActivity.this and myListOfItems to your own values
			    CustomListAdapterDialog clad = new CustomListAdapterDialog(EventActivity.this, contacts_name,contacts_number);

			    lv.setAdapter(clad);

			    lv.setOnItemClickListener( new OnItemClickListener() {
			       
			    	@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
			    		Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:"+ contacts_number.get(arg2)));
						startActivity(callIntent);	
					}
			    });

			    dialog.setContentView(view);

			    dialog.show();

			}
		});   
		     
		     
		     
		}

	

	private void showZoomedMap(String place) {

		PointF coord = myDbHelper.searchPlaceForCoordinates(place);
		Bundle mapParams = new Bundle();
		mapParams.putInt("mode", 1); // mode = 0 for normal and mode = 1 for
										// zoomed
		mapParams.putFloat("X", (float) coord.x);
		mapParams.putFloat("Y", (float) coord.y);
		Log.i("coord : ", coord.x + " : " + coord.y);

		Intent i = new Intent(this, in.co.sdslabs.mdg.map.CampusMap.class);
		i.putExtras(mapParams);
		startActivity(i);
		// myDbHelper.close();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.online) {

			PointF coord = myDbHelper.searchPlaceForLatLong(myDbHelper
					.getVenueMap(b.getString("event")));
			getPathFromPresentLocation(coord.x, coord.y);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			break;
		case R.id.navigate:
			// Log.i("nav", myDbHelper.getVenueMap(b.getString("event")));
			Intent goToMap = new Intent(this, MapTest.class);
			startActivity(goToMap);
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}

	private void showDialogD() {
		// TODO Auto-generated method stub
		PointF coord = myDbHelper.searchPlaceForLatLong(myDbHelper
				.getVenueMapD(dept_name));
		getPathFromPresentLocation(coord.x, coord.y);
	}

	public String setTime(String event) {

		int start = myDbHelper.getStartTime(event);
		int end = myDbHelper.getEndTime(event);

		String startX;
		String endX;

		if (start < 1200) {
			if (start % 100 == 0) {
				startX = start / 100 + ":" + "00 am";
			} else {
				startX = start / 100 + ":" + start % 100 + " am";
			}
		} else if (start >= 1200 && start < 1300) {
			if (start % 100 == 0) {
				startX = start / 100 + ":" + "00 pm";
			} else {
				startX = start / 100 + ":" + start % 100 + " pm";
			}
		} else {
			if (start % 100 == 0) {
				startX = (start / 100) - 12 + ":" + "00 pm";
			} else {
				startX = (start / 100) - 12 + ":" + start % 100 + " pm";
			}
		}

		if (end < 1300) {
			if (end % 100 == 0) {
				endX = end / 100 + ":" + "00 am";
			} else {
				endX = end / 100 + ":" + end % 100 + " am";
			}
			if (end > 1200 && end < 1300) {
				if (end % 100 == 0) {
					endX = end / 100 + ":" + "00 pm";
				} else {
					endX = start / 100 + ":" + end % 100 + " pm";
				}
			}
		} else {
			if (end % 100 == 0) {
				endX = (end / 100) - 12 + ":" + "00 pm";
			} else {
				endX = (end / 100) - 12 + ":" + end % 100 + " pm";
			}
		}

		return startX + " - " + endX;
	}

	public String setTimeD(String dept, String event) {

		int start = myDbHelper.getStartTimeD(dept, event);
		int end = myDbHelper.getEndTimeD(dept, event);

		String startX;
		String endX;

		if (start < 1200) {
			if (start % 100 == 0) {
				startX = start / 100 + ":" + "00 am";
			} else {
				startX = start / 100 + ":" + start % 100 + " am";
			}
		} else if (start >= 1200 && start < 1300) {
			if (start % 100 == 0) {
				startX = start / 100 + ":" + "00 pm";
			} else {
				startX = start / 100 + ":" + start % 100 + " pm";
			}
		} else {
			if (start % 100 == 0) {
				startX = (start / 100) - 12 + ":" + "00 pm";
			} else {
				startX = (start / 100) - 12 + ":" + start % 100 + " pm";
			}
		}

		if (end < 1300) {
			if (end % 100 == 0) {
				endX = end / 100 + ":" + "00 am";
			} else {
				endX = end / 100 + ":" + end % 100 + " am";
			}
			if (end > 1200 && end < 1300) {
				if (end % 100 == 0) {
					endX = end / 100 + ":" + "00 pm";
				} else {
					endX = start / 100 + ":" + end % 100 + " pm";
				}
			}
		} else {
			if (end % 100 == 0) {
				endX = (end / 100) - 12 + ":" + "00 pm";
			} else {
				endX = (end / 100) - 12 + ":" + end % 100 + " pm";
			}
		}

		return startX + " - " + endX;
	}

	private void showDialog() {
		// TODO Auto-generated method stub
		if (b.getString("event").contentEquals("Sciennovate")) {
			PointF coord = myDbHelper.searchPlaceForLatLong("Main Building");
			getPathFromPresentLocation(coord.x, coord.y);
		} else {
			PointF coord = myDbHelper.searchPlaceForLatLong(myDbHelper
					.getVenueMap(b.getString("event")));
			getPathFromPresentLocation(coord.x, coord.y);
		}

	}

	private void getPathFromPresentLocation(double destLat, double destLong) {
		// TODO Auto-generated method stub
		// create class object
		gps = new GPSTracker(this);

		// check if GPS enabled
		if (gps.canGetLocation()) {

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			onlineMap(latitude, longitude, destLat, destLong);

		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
	}

	private void onlineMap(double startLat, double startLong, double destLat,
			double destLong) {
		// TODO Auto-generated method stub
		String uri = "http://maps.google.com/maps?saddr=" + startLat + ","
				+ startLong + "&daddr=" + destLat + "," + destLong;
		Intent intent1 = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(uri));
		intent1.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");
		intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent1);
	}



	}

