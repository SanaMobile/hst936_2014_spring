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
		
		dailyReading.setMucus(r1.nextInt(6));
		//dailyReading.setTemperature(0.0);
		PhaseCalculator phaseCalculator = new PhaseCalculator();
		dailyReading.setPhase(phaseCalculator.calculatePhase(getBaseContext(), dailyReading));
		
		
		boolean success = observationsDAO.addDailyReadings(dailyReading);
		
		
		if(!success)
		{
			Toast toast =Toast.makeText(getBaseContext(), "Could not insert the reading ", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		
		dailyReading2 = observationsDAO.getDailyReading(intent.getIntExtra("dayOfMonth",0), intent.getIntExtra("month",0), intent.getIntExtra("year",0));
		String msg = dailyReading.getMucus()+"\n"+dailyReading.getPhase()+"\n";
		Toast toast =Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}
