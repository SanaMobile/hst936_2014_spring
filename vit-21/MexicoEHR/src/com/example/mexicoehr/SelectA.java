package com.example.mexicoehr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectA extends Activity implements OnClickListener {
	Button b1, b2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.selecta);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Echild:
			Intent i=new Intent(this,Edit.class);
			startActivity(i);
			break;
			
			
		case R.id.Eadult:
			
			Intent j=new Intent(this,EditA.class);
			startActivity(j);
			break;
		
		
		}

	}

}
