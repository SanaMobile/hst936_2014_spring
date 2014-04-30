package com.example.ovuguide;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		Intent intent = getIntent();
		
		ObservationsDAO observationsDAO = new ObservationsDAO(getBaseContext());
		DailyReading dailyReading, dailyReading2;
		
		dailyReading = new DailyReading();
		dailyReading.setDayOfMonth(intent.getIntExtra("dayOfMonth",0));
		dailyReading.setMonth(intent.getIntExtra("month",0));
		dailyReading.setYear(intent.getIntExtra("year",0));
		Random r1 = new Random();
		
		dailyReading.setMucus(0);
		dailyReading.setTemperature(0.0);
		dailyReading.setPhase(0);
		
		observationsDAO.open();
		observationsDAO.addDailyReadings(dailyReading);
		observationsDAO.close();
		
		Intent i = new Intent("com.example.ovuguide.Test2");
		i.putExtra("dayOfMonth", dailyReading.getDayOfMonth());
		i.putExtra("month", dailyReading.getMonth());
		i.putExtra("year", dailyReading.getYear());
		startActivity(i);
		
		/*observationsDAO.open();
		
		dailyReading2= observationsDAO.getDailyReading(intent.getIntExtra("dayOfMonth",0), intent.getIntExtra("month",0), intent.getIntExtra("year",0));
		observationsDAO.close();
		if(truth)
		{
			Toast toast =Toast.makeText(this.getBaseContext(), "Not null", Toast.LENGTH_LONG);
			toast.show();
		}
		else
		{
			Toast toast =Toast.makeText(this.getBaseContext(), "Its null", Toast.LENGTH_LONG);
			toast.show();
		}
		/*String txt="";
		for (String string : res) {
			txt+=string;
		}
		Toast toast =Toast.makeText(this.getBaseContext(), txt, Toast.LENGTH_LONG);
		toast.show();*/
		
		/*String txt = dailyReading.getDayOfMonth()+"\n"+dailyReading.getMonth()+"\n"+dailyReading.getYear()+"\n"+dailyReading.getMucus()+"\n"+dailyReading.getTemperature()+"\n"+dailyReading.getPhase();
		Toast toast =Toast.makeText(this.getBaseContext(), txt, Toast.LENGTH_LONG);
		toast.show();*/
		/*if(dailyReading2==null)
		{
			
			Toast toast =Toast.makeText(this.getBaseContext(), "Null cursor", Toast.LENGTH_LONG);
			toast.show();
			
		}
		else
		{
		String txt = dailyReading2.getDayOfMonth()+"\n"+dailyReading2.getMonth()+"\n"+dailyReading2.getYear()+"\n"+dailyReading2.getMucus()+"\n"+dailyReading2.getTemperature()+"\n"+dailyReading2.getPhase();
		Toast toast =Toast.makeText(this.getBaseContext(), txt, Toast.LENGTH_LONG);
		toast.show();
		//}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}
