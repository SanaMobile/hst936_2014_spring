package com.example.bmiapp;


import android.os.Bundle;
import android.app.Activity;

import android.view.View;


import android.content.Intent;
public class gender extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
	}
	

	public void callingIntent(View v)
	{
		if(v.getId()==R.id.button1)
		{
	     Intent gen=new Intent(v.getContext(),MainActivity.class);
	     startActivity(gen);
	     
		}
	}
}
		
	