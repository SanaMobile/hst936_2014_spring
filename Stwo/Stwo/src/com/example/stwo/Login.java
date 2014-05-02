package com.example.stwo;




import com.example.stwo.db.Databasehandler;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {


	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		TextView registerscreen=(TextView) findViewById(R.id.link_to_register);



		Button Login=(Button) findViewById(R.id.btnLogin);
		Login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				EditText username=(EditText) findViewById(R.id.editText1);
				EditText password=(EditText) findViewById(R.id.editText2);
				
				
				 final String Username = username.getText().toString();
	                final String Password=  password.getText().toString();

				final Databasehandler db = new Databasehandler(getBaseContext());

				

                
				
				
                if(db.Login(Username, Password)==1)
                {
                	Toast.makeText(getApplicationContext(),"Please Try again", Toast.LENGTH_LONG).show();
					Intent in=new Intent(Login.this,Signups.class);
    				startActivity(in);
	
                }
				else{
					
    				Intent in=new Intent(Login.this,MainPage.class);
    				startActivity(in);
    				Toast.makeText(getApplicationContext(),"Logged in successfully", Toast.LENGTH_LONG).show();
				}
				

			
		


			}
		});

		final Intent one=new Intent(Login.this,Signups.class);		

		registerscreen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				startActivity(one);
			}
		});
	}


}
