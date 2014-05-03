package com.example.mexicoehr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DoctorEdit extends Activity implements OnClickListener{
Button c,a;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		a=(Button)findViewById(R.id.adult);
		c=(Button)findViewById(R.id.child);
		a.setOnClickListener(this);
		c.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.child:
			Intent i =new Intent(this,PatientC.class);
			startActivity(i);
			break;
		case R.id.adult:
			Intent j =new Intent(this,PatientA.class);
			startActivity(j);
			break;
		}
	}
}
