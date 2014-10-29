package in.co.sdslabs.thomso14;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.util.Log;


public class StartService extends Service{
	private AlarmManager mAlarmManager;
	private Intent mNotificationReceiverIntent;
    private PendingIntent mNotificationReceiverPendingIntent;
    private String EVENTNAME = "eventname";
	
	ArrayList<String> eventname=null;
	
	ArrayList<String> favouriteeventnameonday=new ArrayList<String>();
	
	
	
	DatabaseHelper myDbHelper;
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		   mNotificationReceiverIntent = new Intent (getApplicationContext(),AlarmReciever.class); 
			
			
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		
		
		 myDbHelper = new DatabaseHelper(getApplicationContext());
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

		 
	Log.i("entered", "OnStartCommand");
			Calendar c = Calendar.getInstance();
			int date =c.get(Calendar.DAY_OF_MONTH);
			 Log.i("date",String.valueOf(date) );
			int day=4; 
			 switch (date) {
			case 31:
				day=1;
				break;
			case 1:
				day=2;
				break;
			case 2:
				day=3;
				break;
			default:
				break;
			}
			if(day<4){
				eventname = myDbHelper.getFavouritesName();
				int x=eventname.size();
				Log.i("favourites",String.valueOf(x) );
				Log.i("day",String.valueOf(day) );
				if(day==0)
				{  Log.i("entered",String.valueOf(0) );
					for (int i=0;i<x;i++){
						int event_day=myDbHelper.getEventDay(eventname.get(i));
						if(event_day==0){
							favouriteeventnameonday.add(eventname.get(i));
						}
					}
				}
				else if(day==1)
				{
					Log.i("entered",String.valueOf(1) );
					for (int i=0;i<x;i++){
						Log.i("for loop",String.valueOf(i) );
						int event_day=myDbHelper.getEventDay(eventname.get(i));
						Log.i("event_day",String.valueOf(event_day) );
						if(event_day==1||event_day==12||event_day==123){
							Log.i("entered","if statement" );
							favouriteeventnameonday.add(eventname.get(i));
							Log.i("added",String.valueOf(i)+"event" );
						}
					}
				}
				else if(day==2)
				{   Log.i("entered",String.valueOf(2) );
					for (int i=0;i<x;i++){
						int event_day=myDbHelper.getEventDay(eventname.get(i));
						if(event_day==2||event_day==12||event_day==123||event_day==23){
							favouriteeventnameonday.add(eventname.get(i));
						}
					}
				}
				
				else 
				{Log.i("entered",String.valueOf(3) );
					for (int i=0;i<x;i++){
						int event_day=myDbHelper.getEventDay(eventname.get(i));
						if(event_day==3||event_day==23||event_day==123){
							favouriteeventnameonday.add(eventname.get(i));
						}
					}
				}
				
				
				int no_of_alarms =favouriteeventnameonday.size();
				Log.i("no_of_alarms",String.valueOf(no_of_alarms) );
				if(no_of_alarms>0){
					
					for(int i=0;i<no_of_alarms;i++){
					int starttime=myDbHelper.getStartTime(favouriteeventnameonday.get(i));
					int hrs=starttime/100;
					int min=starttime%100;
					Date _date=null;
					switch (day) {
					case 0:
						 _date=new Date(114, 9,30 );
						break;
					case 1:
						 _date=new Date(114, 9,31 );
						break;
					case 2:
						 _date=new Date(114, 10,1 );
						break;
				    case 3:
							 _date=new Date(114, 10,2 );
								break;
					default:
						break;
					}
					
					long eventmillisec=_date.getTime()+hrs*60*60*1000+min*60*1000;
					mNotificationReceiverIntent.putExtra("event", favouriteeventnameonday.get(i));
					 long currentmillisec =System.currentTimeMillis();
					 Log.i("eventmillisec", String.valueOf(eventmillisec));
					 Log.i("currentmillisec", String.valueOf(currentmillisec));
					 if(currentmillisec<eventmillisec){
					mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
		            this.getApplicationContext(),i, mNotificationReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
										mAlarmManager.set(AlarmManager.RTC_WAKEUP,eventmillisec,
												 mNotificationReceiverPendingIntent);
										Log.i("Alarm","Set");
					}
					}
					}
			} 

		
		
		
		
				
			return START_NOT_STICKY;
		
		 }
		
		
		
		 
		
		
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
