package com.example.stwo;



import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

public class MainPage extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page);



	
		
		Button one =(Button) findViewById(R.id.button1);
		one.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {


				// TODO Auto-generated method stub
				Intent a=new Intent(MainPage.this,Homepage.class);
				startActivity(a);
			}
		});
	
	
	
	Button two =(Button) findViewById(R.id.button2);
	two.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {


			// TODO Auto-generated method stub
			Intent a=new Intent(MainPage.this,Dieseases.class);
			startActivity(a);
		}
	});
	
	Button three =(Button) findViewById(R.id.button3);
	three.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {


			// TODO Auto-generated method stub
			Intent a=new Intent(MainPage.this,Third.class);
			startActivity(a);
		}
	});
}
	
}
/*
	private void createData(){
		/*People p=new People();
		p.setName("Rahul kiran");
		p.setEmail("rahulkirant@gmail.com");
		p.setAge(20);
		p.setAddress("Tampines St 71,Singapore");
		p=datasource.create(p);*/
		
		
		
		
		//Log.i(LOGTAG,"person created with id"+ p.getId());
	

