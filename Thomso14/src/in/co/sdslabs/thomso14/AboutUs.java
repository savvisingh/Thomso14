package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUs extends ActionBarActivity implements OnClickListener{

	public AboutUs() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 ActionBar bar = getActionBar();
		 bar.setTitle("  Credits");
		  bar.setBackgroundDrawable(new ColorDrawable(0xff000000));
		ImageView fb = (ImageView)findViewById(R.id.fb);
		ImageView git = (ImageView)findViewById(R.id.git);
		ImageView play = (ImageView)findViewById(R.id.play);
		ImageView sarabjeet=(ImageView)findViewById(R.id.sarabjeet);
		ImageView akash=(ImageView)findViewById(R.id.aakash);	
		ImageView akshay=(ImageView)findViewById(R.id.akshay);
		TextView mdg=(TextView)findViewById(R.id.mdg_writeup);
		TextView developers=(TextView)findViewById(R.id.developers);
		TextView designers=(TextView)findViewById(R.id.designer);
		
		
		Typeface font= Typeface.createFromAsset(getAssets(), "CaviarDreams.ttf");
		
		
		designers.setTypeface(font);
		developers.setTypeface(font);
		mdg.setTypeface(font);
		
		
		
		
		
		fb.setOnClickListener(this);
		git.setOnClickListener(this);
		play.setOnClickListener(this);
		sarabjeet.setOnClickListener(this);
		akash.setOnClickListener(this);
		akshay.setOnClickListener(this);
		
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.fb :
			Intent fbIntent =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("https://www.facebook.com/mdgiitr"));
	          startActivity(fbIntent);
			break;
		case R.id.git :
			Intent gitIntent =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("https://github.com/sdsmdg"));
	          startActivity(gitIntent);			
			break;
		case R.id.play :
			Intent playIntent =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("https://play.google.com/store/apps/developer?id=SDSLabs"));
	          startActivity(playIntent);	
			break;
		case R.id.sarabjeet :
			Intent fbIntentsavvi =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("https://www.facebook.com/savvi.singh"));
	          startActivity(fbIntentsavvi);
			break;
		case R.id.aakash:
			Intent fbIntentakash =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("https://www.facebook.com/aakash.kerawat"));
	          startActivity(fbIntentakash);
			break;
		case R.id.akshay :
			Intent fbIntentakshay =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("https://www.facebook.com/karangaleakshay"));
	          startActivity(fbIntentakshay);
			break;
			
		}
	}

}
