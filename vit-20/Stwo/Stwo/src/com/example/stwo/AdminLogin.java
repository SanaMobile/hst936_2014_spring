package com.example.stwo;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);
		
		 final EditText one=(EditText) findViewById(R.id.editText1);
 
		
		Button reg=(Button) findViewById(R.id.button1);
		
		reg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if("password".equals(one.getText().toString())){
				Intent n=new Intent(AdminLogin.this,Admin2.class);
				startActivity(n);
				
				}
				else{
					Toast.makeText(getApplicationContext(),"You need to be a Admin to login",Toast.LENGTH_LONG).show();
				}
					
				
			}
		});
	}
}
