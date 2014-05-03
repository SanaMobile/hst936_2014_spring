package com.example.mexicoehr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class After extends Activity implements OnClickListener {
	Button ba, be;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selecta);
		ba = (Button) findViewById(R.id.Eadult);
		be = (Button) findViewById(R.id.Echild);
		ba.setOnClickListener(this);
		be.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Echild:
			Intent i = new Intent(After.this, Edit.class);
			startActivity(i);
			break;

		case R.id.Eadult:
			Intent j = new Intent(After.this, EditA.class);
			startActivity(j);
			break;

		}

	}

}
