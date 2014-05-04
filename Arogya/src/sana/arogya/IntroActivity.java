package sana.arogya;

import java.util.Timer;
import java.util.TimerTask;

import sana.helitavya.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IntroActivity extends Activity {
	 @Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.intro_activity);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
			   public void run() {
			   nextIntent();
			   }
			}, 2000);
	 }
	 public void nextIntent()
	 {
		 Intent in=new Intent(this,UserDetailsActivity.class);
		 	startActivity(in);
	 }
	 }
