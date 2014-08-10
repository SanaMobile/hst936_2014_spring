package com.example.ovuguide;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Form extends Activity {
	
	Intent intent;
	RadioGroup radioGroupMucus,radioGroupSex;
	RadioButton radioBtnMucus,radioBtnSex;
	Button BtnSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);
		
		intent = getIntent();
		radioGroupMucus = (RadioGroup) findViewById(R.id.radioGroupMucusTexture);
		radioGroupSex = (RadioGroup) findViewById(R.id.radioGroupSex);
		BtnSubmit = (Button) findViewById(R.id.btnSubmit);
		
		BtnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//validate
				int selectedMucusId,selectedSexStatusId;
				selectedMucusId = radioGroupMucus.getCheckedRadioButtonId();
				selectedSexStatusId = radioGroupSex.getCheckedRadioButtonId();
				
				if(selectedMucusId == -1)
				{
					Toast toast =Toast.makeText(getBaseContext(), "Please select a value for the texture for the mucus ", Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				else if(selectedSexStatusId == -1)
				{
					Toast toast =Toast.makeText(getBaseContext(), "Please state whether you have had sex or not", Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				
				radioBtnMucus = (RadioButton) findViewById(selectedMucusId);
				radioBtnSex = (RadioButton) findViewById(selectedSexStatusId);
				String mucus = radioBtnMucus.getText().toString();
				int mucusValue=MucusTexture.NONE;
				
				if(mucus.compareTo("Dry")==0)
				{
					mucusValue = MucusTexture.NONE;
				}
				else if(mucus.compareTo("Sticky")==0)
					mucusValue = MucusTexture.STICKY;
				else if(mucus.compareTo("Creamy")==0)				
					mucusValue = MucusTexture.CREAMY;
				else if(mucus.compareTo("Egg-White")==0)				
					mucusValue = MucusTexture.EGGWHITE;
				else if(mucus.compareTo("Watery")==0)	
					mucusValue = MucusTexture.WATERY;
				else if	(mucus.compareTo("Menses")==0)		
					mucusValue = MucusTexture.MENSES;
				
				
				ObservationsDAO observationsDAO = new ObservationsDAO(getBaseContext());
				DailyReading dailyReading, dailyReading2;
				
				dailyReading = new DailyReading();
				dailyReading.setDayOfMonth(intent.getIntExtra("dayOfMonth",0));
				dailyReading.setMonth(intent.getIntExtra("month",0));
				dailyReading.setYear(intent.getIntExtra("year",0));
				//Random r1 = new Random();
				
				dailyReading.setMucus(mucusValue);
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
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
	
	
}
