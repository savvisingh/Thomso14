package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapterDialog  extends BaseAdapter {

private ArrayList<String> contactname,contactnumber;

private LayoutInflater layoutInflater;

public CustomListAdapterDialog(Context context, ArrayList<String> contact_name,ArrayList<String> contact_number) {
    this.contactname = contact_name;
    this.contactnumber=contact_number;
    layoutInflater = LayoutInflater.from(context);
}

@Override
public int getCount() {
    return contactname.size();
}

@Override
public Object getItem(int position) {
    return contactname.get(position);
}

@Override
public long getItemId(int position) {
    return position;
}


   

static class ViewHolder {
    TextView unitView;
    TextView quantityView;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	 ViewHolder holder;
	    if (convertView == null) {
	        convertView = layoutInflater.inflate(R.layout.custom_list_dialog_item, null);
	        holder = new ViewHolder();
	        holder.unitView = (TextView) convertView.findViewById(R.id.name);
	        holder.quantityView = (TextView) convertView.findViewById(R.id.number);
	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }

	    holder.unitView.setText(contactname.get(position));
	    holder.quantityView.setText(contactnumber.get(position));

	    return convertView;
	}

}