package com.example.mexicoehr;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Five extends Activity {
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fin);
		
		
		Thread ta=new Thread()
		{
		public void run()
		{
			try{
				sleep(5000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			finally
			{
				Intent op=new Intent(Five.this,MainActivity.class);
				startActivity(op);
			}
		}
	};
	ta.start();
}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.release();
		finish();
	}
	
}



