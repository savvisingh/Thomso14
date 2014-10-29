package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactAdapter extends ArrayAdapter<ContactClass> {
	private final Context context;
	// List values
	private final List<ContactClass> contactList;

	public ContactAdapter(Context context, List<ContactClass> contactList) {
		super(context, R.layout.contact_item, contactList);
		this.context = context;
		this.contactList = contactList;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.contact_item, parent, false);

		TextView name = (TextView) rowView.findViewById(R.id.name);
		TextView number = (TextView) rowView.findViewById(R.id.number);
		
		TextView post = (TextView) rowView.findViewById(R.id.position);

		name.setText(contactList.get(position).getName());
		number.setText(contactList.get(position).getNumber());

		if (contactList.get(position).getNumber().contentEquals("*"))
			number.setVisibility(View.GONE);
	
		
		post.setText(contactList.get(position).getPost());
		if (contactList.get(position).getName()
				.contentEquals("Lavika Aggarwal"))
			post.setText("Co-Convener");

		return rowView;
	}

}
