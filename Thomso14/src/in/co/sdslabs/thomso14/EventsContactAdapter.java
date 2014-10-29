package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventsContactAdapter extends BaseAdapter {
	ArrayList<String> Contact_Name,Contact_Number;
	Context context;
	public EventsContactAdapter ( ArrayList<String> contactname,ArrayList<String> contactnumber,Context appcontext){
		this.Contact_Name=contactname;
		this.Contact_Number=contactnumber;
		this.context=appcontext;
		 Log.i("entered", "constructor");
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Contact_Name.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return Contact_Name.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.contact_item, parent, false);

		TextView name = (TextView) rowView.findViewById(R.id.name);
		TextView number = (TextView) rowView.findViewById(R.id.number);
		TextView email = (TextView) rowView.findViewById(R.id.email);
		TextView post = (TextView) rowView.findViewById(R.id.position);
		
		name.setText(Contact_Name.get(position));
		number.setText(Contact_Number.get(position));
		email.setVisibility(View.GONE);
		post.setVisibility(View.GONE);
		return rowView;
	}

}
