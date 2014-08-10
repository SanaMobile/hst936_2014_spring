package com.example.stwo;



import com.example.stwo.db.Databasehandler;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signups extends Activity {
	
	
	EditText edt1,edt2,edt3,edt4,edt5 = null;
	String d1,d2,d3,d4,d5 = null;
	
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		

		
		  final Databasehandler sh = new Databasehandler(this);
	
		
		edt1 = (EditText) findViewById(R.id.editText4);
		edt2 = (EditText) findViewById(R.id.editText1);
		edt3= (EditText) findViewById(R.id.editText2);
		edt4 = (EditText) findViewById(R.id.editText3);
		edt5 = (EditText) findViewById(R.id.editText5);
		
		
	
		
		
		Button f=(Button) findViewById(R.id.button1);
		f.setOnClickListener(new View.OnClickListener() {
			
			 
			
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
		d1=edt1.getText().toString();
		d2=edt2.getText().toString();
		d3=edt3.getText().toString();
		d4=edt4.getText().toString();
		d5=edt5.getText().toString();
				
		
				
		          long sid1 = sh.addpeople(d1, d2, d5, d3, d4);
				 Toast.makeText(getApplicationContext(), "You have successfully registered", Toast.LENGTH_LONG).show();
				 Intent back=new Intent(Signups.this,Login.class);
				 startActivity(back);
				}
			
		});
	}

}
