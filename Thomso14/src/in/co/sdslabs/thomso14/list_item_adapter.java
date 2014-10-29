package in.co.sdslabs.thomso14;



import in.co.sdslabs.thomso14.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class list_item_adapter extends BaseAdapter{

	
	

	Context context;
	TextView eventname,eventtag,eventtime,eventvenue;
	List<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
	public list_item_adapter(List<HashMap<String, String>> evlist,Context appcontext) {
		this.eventList=evlist;
		this.context=appcontext;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return eventList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return eventList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
           convertView = mInflater.inflate(R.layout.eventcategory_list_item, null);
        }
		HashMap<String, String> hm = new HashMap<String, String>();
		hm=eventList.get(position);
		eventname=(TextView)convertView.findViewById(R.id.tv_eName);
		eventtime=(TextView)convertView.findViewById(R.id.thumb_event_time);
		eventtag=(TextView)convertView.findViewById(R.id.thumb_event_tag);
		eventvenue=(TextView)convertView.findViewById(R.id.thumb_event_venue);
		
		eventname.setText(hm.get("eventname"));
		eventtime.setText(hm.get("eventtime"));
		eventvenue.setText(hm.get("eventvenue"));
		if(hm.get("eventdate")==null){
			eventtag.setText(hm.get("eventtag"));}
		else eventtag.setText(hm.get("eventdate"));
		 Typeface font= Typeface.createFromAsset(context.getAssets(), "CaviarDreams.ttf");
		 eventname.setTypeface(font);
		 eventname.setTypeface(eventname.getTypeface(), Typeface.BOLD);
		 eventvenue.setTypeface(font);
		 eventtime.setTypeface(font);
         
		 if(hm.get("eventtag").matches("PRONITES"))
			{eventtag.setTextColor(Color.parseColor("#c568eb"));}
			else if(hm.get("eventtag").matches("FORMALS"))
			{eventtag.setTextColor(Color.parseColor("#5bc7f9"));}
			else if(hm.get("eventtag").matches("INFORMALS"))
			{eventtag.setTextColor(Color.parseColor("#ff9036"));}
			else if(hm.get("eventtag").matches("WORKSHOPS"))
			{eventtag.setTextColor(Color.parseColor("#5CE62E"));}
			
			
		return convertView;
	}
	


}
