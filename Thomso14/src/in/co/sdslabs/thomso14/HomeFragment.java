package in.co.sdslabs.thomso14;

import in.co.sdslabs.thomso14.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class HomeFragment extends Fragment {

	SharedPreferences pref = null;
	Editor prefedit = null;
	ImageView logo,spons;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		logo= (ImageView) getActivity().findViewById(R.id.imageView1);
		spons= (ImageView) getActivity().findViewById(R.id.imageView2);
		pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		prefedit = pref.edit();
		if(pref.getBoolean("animcheck", true)==true){
			Animation anim_logo = AnimationUtils.loadAnimation(getActivity(), R.anim.logo_animation);
			Animation anim_spons = AnimationUtils.loadAnimation(getActivity(), R.anim.spons_animation);
			spons.setAnimation(anim_spons);
			anim_spons.start();
			logo.setAnimation(anim_logo);
			anim_logo.start();
			prefedit.putBoolean("animcheck", false);
			prefedit.commit();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		prefedit = pref.edit();
		prefedit.putBoolean("animcheck", true);
		prefedit.commit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.home, container, false);
		
		
		
	}

}
