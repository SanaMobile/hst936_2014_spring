package com.example.ovuguide;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class Test2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test2);
		Intent intent = getIntent();
		
		ObservationsDAO observationsDAO = new ObservationsDAO(getBaseContext());
		observationsDAO.open();		
		ArrayList<DailyReading> dailyReading2= observationsDAO.getDailyReading(intent.getIntExtra("dayOfMonth",0), intent.getIntExtra("month",0), intent.getIntExtra("year",0));
		observationsDAO.close();
		
		for (DailyReading reading : dailyReading2)
		{
		String txt = reading.getDayOfMonth()+"\n"+reading.getMonth()+"\n"+reading.getYear()+"\n"+reading.getMucus()+"\n"+reading.getTemperature()+"\n"+reading.getPhase();
		Toast toast =Toast.makeText(this.getBaseContext(), txt, Toast.LENGTH_LONG);
		toast.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test2, menu);
		return true;
	}

}
