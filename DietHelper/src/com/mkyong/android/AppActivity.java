package com.mkyong.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class AppActivity extends Activity {

	Button button,b1,b2,b3,b4,b5,b6;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		addListenerOnButton();
	}

	public void addListenerOnButton() {

		final Context context = this;

		button = (Button) findViewById(R.id.button1);
        b1= (Button) findViewById(R.id.button2);
        b2= (Button) findViewById(R.id.button3);
        b3= (Button) findViewById(R.id.button4);
        b4= (Button) findViewById(R.id.button5);
        b5= (Button) findViewById(R.id.button6);
        b6= (Button) findViewById(R.id.button7);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, App2Activity.class);
                startActivity(intent);   

			}

	
			
		});
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, App3Activity.class);
                startActivity(intent);   

			}

	
			
		});
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, App4Activity.class);
                startActivity(intent);   

			}

	
			
		});
		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, App5Activity.class);
                startActivity(intent);   

			}

	
			
		});
		b4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, App6Activity.class);
                startActivity(intent);   

			}

	
			
		});
		b5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, App7Activity.class);
                startActivity(intent);   

			}

	
			
		});
		b6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, App8Activity.class);
                startActivity(intent);   

			}

	
			
		});
		
	}

}