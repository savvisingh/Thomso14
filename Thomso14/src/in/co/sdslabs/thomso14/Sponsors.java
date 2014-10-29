package in.co.sdslabs.thomso14;


import in.co.sdslabs.thomso14.R;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Sponsors extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sponsors);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 ActionBar bar = getActionBar();
		  bar.setBackgroundDrawable(new ColorDrawable(0xff000000));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
