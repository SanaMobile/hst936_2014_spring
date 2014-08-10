package com.example.mexicoehr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Next extends Activity implements OnClickListener {
Button b1,b2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create);
		b1=(Button)findViewById(R.id.create);
		b2=(Button)findViewById(R.id.edit);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.create:
			Intent i=new Intent(Next.this,Select.class);
			startActivity(i);
			break;
			
		case R.id.edit:
			Intent j=new Intent(Next.this,After.class);
			startActivity(j);
			break;
			
		}
	}


}
