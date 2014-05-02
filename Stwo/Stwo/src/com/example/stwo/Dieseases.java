package com.example.stwo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dieseases extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.disease);
		
		
		Button first=(Button) findViewById(R.id.button1);
		first.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent f=new Intent(Dieseases.this,Marasamus.class)	;
			startActivity(f);
			finish();
			}
		});
		
		Button second=(Button) findViewById(R.id.button2);
		second.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent g=new Intent(Dieseases.this,kwashiorkor.class);
				startActivity(g);
				finish();
				
			}
		});
		
		}

}
