package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class Day0  extends ListFragment {

	private String EVENTNAME = "eventname";
	private String EVENTONELINER = "eventoneliner";
	private String EVENTIMAGE = "eventimage";
	private String EVENTIME = "eventtime";
	private String EVENTTAG = "eventtag";
	private String EVENTVENUE = "eventvenue";
	ArrayList<String> eventname;
	ArrayList<String> eventoneliner;
	
	DatabaseHelper myDbHelper;
	
	FragmentManager fragmentManager;
	public Day0() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		List<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
		 myDbHelper = new DatabaseHelper(getActivity()
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
		
		View v = inflater.inflate(R.layout.eventcategoryfragment_layout,
				container, false);
		
		int x ,y;
		eventname = myDbHelper.getEventNamex(0);
		
		
		
		for (int i = 0; i < eventname.size(); i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			Log.v("dfsd", eventname.get(i));
			hm.put("eventname", eventname.get(i));
			
			hm.put("eventtime", setTime(eventname.get(i)));
			hm.put("eventvenue",myDbHelper.getVenueDisplay(eventname.get(i)));
			hm.put("eventtag",myDbHelper.getEventTag((eventname.get(i))));
			
			
			eventList.add(hm);
		}
		
		TextView tv_eName=(TextView) v.findViewById(R.id.tv_eName);
		// Typeface font= Typeface.createFromAsset(getActivity().getAssets(), "CaviarDreams.ttf");
		  //tv_eName.setTypeface(font);
		list_item_adapter mAdapter = new list_item_adapter(eventList,getActivity());
				

	
		// Setting the adapter to the listView
		setListAdapter(mAdapter);
		Log.v("Day", "1");
		return v;
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

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		setListAdapter(null);
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
		super.onListItemClick(l, v, 1, id);
		
		Bundle data = new Bundle();
		data.putString("event", eventname.get(pos));
		Intent i = new Intent(getActivity().getBaseContext() , EventActivity.class);
		i.putExtras(data);
		startActivity(i);
	}

}
