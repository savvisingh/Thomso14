package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class UpcomingEvents extends ListFragment {

	// private ListView mEventList;
	private List<HashMap<String, String>> eventList;
	private SimpleAdapter mAdapter;

	private String EVENTNAME = "eventname";
	private String EVENTONELINER = "eventoneliner";
	private String EVENTIMAGE = "eventimage";

	int position;
	String[] categories;
	int day;
	long time;

	ArrayList<String> eventname;
	ArrayList<Long> startTime;
	ArrayList<String> eventoneliner;

	FragmentManager fragmentManager;

	Bundle data;

	DatabaseHelper myDbHelper;

	ArrayList<String> eventToBePassed;

	public UpcomingEvents() {
		// TODO Auto-generated constructor stub
		// to be set to current date and time
		Calendar c = Calendar.getInstance();
		int d = c.get(Calendar.DAY_OF_WEEK);
		
		switch (d) {
		case 6:
			day = 1;
			break;
		case 7:
			day = 2;
			break;
		case 8:
			day = 3;
			break;
		default:
			day = 0;
		}
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		time = hour * 100 + min;
		if (hour <= 8)
			time = 900;
		else if (hour >= 19) {
			time = 900;
			if (day != 3)
				day++;
		}

		Log.v("DAY", "day :" + day);
		Log.v("DAY", "time" + time);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Creating view corresponding to the fragment
		View v = inflater.inflate(R.layout.eventcategoryfragment_layout,
				container, false);
		
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Upcoming Events");


		
		eventList = new ArrayList<HashMap<String, String>>();
		eventToBePassed = new ArrayList<String>();

		// Initialising DBhelper class

		myDbHelper = new DatabaseHelper(getActivity().getBaseContext());
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

		// checking recent
		startTime = myDbHelper.getUpcomingTime(day);
		eventname = myDbHelper.getUpcomingEventNames(day);

		for (int i = 0; i < startTime.size(); i++) {

			if (startTime.get(i) - time <= 200
					&& startTime.get(i) - time >= -15) {

				Log.i("diff : ", i + " : " + (startTime.get(i) - time));

				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put(EVENTNAME, eventname.get(i));
				eventToBePassed.add(eventname.get(i));
				Log.v("eventname", "Displayed eventname" + eventname.get(i));
				hm.put(EVENTONELINER,
						myDbHelper.getEventOneLiner(eventname.get(i)));
				int x = myDbHelper.getImageX(eventname.get(i));
				int y = myDbHelper.getImageY(eventname.get(i));
				
				eventList.add(hm);
			}
		}
		if (day == 1) {
			if (1 == 1) {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put(EVENTNAME, "Shadow Act");
				eventToBePassed.add("Shadow Act");
				hm.put(EVENTONELINER, "");
				int x = myDbHelper.getImageX("Shadow Act");
				int y = myDbHelper.getImageY("Shadow Act");
				
				eventList.add(hm);
			}

			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put(EVENTNAME, "Speed Painting");
			eventToBePassed.add("Speed Painting");
			hm.put(EVENTONELINER, "");
			int x = myDbHelper.getImageX("Speed Painting");
			int y = myDbHelper.getImageY("Speed Painting");
			
			eventList.add(hm);
		} else if (day == 2) {
			if (1 == 1) {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put(EVENTNAME, "Sunburn");
				eventToBePassed.add("Sunburn");
				hm.put(EVENTONELINER, "");
				int x = myDbHelper.getImageX("Sunburn");
				int y = myDbHelper.getImageY("Sunburn");
				
				eventList.add(hm);
			}
		} else if (day == 3) {
			if (1 == 1) {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put(EVENTNAME, "3D Projection Mappings");
				eventToBePassed.add("3D Projection Mappings");
				hm.put(EVENTONELINER, "");
				int x = myDbHelper.getImageX("3D Projection Mappings");
				int y = myDbHelper.getImageY("3D Projection Mappings");
				
				eventList.add(hm);
			}

			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put(EVENTNAME, "Hula B Boys Dance");
			eventToBePassed.add("Hula B Boys Dance");
			hm.put(EVENTONELINER, "");
			int x = myDbHelper.getImageX("Hula B Boys Dance");
			int y = myDbHelper.getImageY("Hula B Boys Dance");
			
			eventList.add(hm);
		}

		if (eventToBePassed.size() == 0) {
			Toast.makeText(getActivity().getBaseContext(),
					"There are no Upcoming Events", Toast.LENGTH_SHORT).show();
			getActivity().getSupportFragmentManager().popBackStack();

		}

		// if(eventList.size() == 0)
		// Toast.makeText(getActivity(), "No Upcoming Events",
		// Toast.LENGTH_SHORT).show();
		String[] from = { EVENTNAME, EVENTONELINER, EVENTIMAGE };

		int[] to = { R.id.tv_eName, R.id.tv_eDescr, R.id.eventImage };

		// Instantiating an adapter to store each items
		// R.layout.drawer_layout defines the layout of each item

		mAdapter = new SimpleAdapter(getActivity().getBaseContext(), eventList,
				R.layout.eventcategory_list_item, from, to);

		return v;

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		String eventName = eventToBePassed.get(position);
		Bundle data = new Bundle();
		data.putString("event", eventName);

		Log.i("eventname sent", eventName);
		Log.i("position", "" + position);
		Intent i = new Intent(getActivity().getBaseContext(),
				EventActivity.class);
		i.putExtras(data);
		startActivity(i);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		DatabaseHelper myDbHelper = new DatabaseHelper(getActivity()
				.getBaseContext());
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
		// final TextView listHeader = new TextView(getActivity());
		// listHeader.setTextSize(20);
		// listHeader.setPadding(15, 10, 5, 10);
		//
		// Typeface mTypeFace = Typeface.createFromAsset(
		// getActivity().getAssets(), "Roboto-Medium.ttf");
		// listHeader.setTypeface(mTypeFace);
		// listHeader.setBackgroundColor(Color.rgb(1, 140, 149));
		// //
		// listHeader.setBackgroundResource(R.drawable.eventcat_competetions);
		// listHeader.setClickable(false);
		// listHeader.setText(myDbHelper
		// .getCategoryDescription(categories[position]));
		// listHeader.setGravity(Gravity.FILL_HORIZONTAL);
		//
		// getListView().addHeaderView(listHeader, null, false);
		// getListView().addFooterView(Color.rgb(1, 140, 149));
		setListAdapter(mAdapter);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		setListAdapter(null);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		((ActionBarActivity) activity).getSupportActionBar().setTitle(
				"Upcoming Events");
	}
}
