package com.example.stwo;

import java.util.List;

import com.example.stwo.db.People;
import com.example.stwo.db.PeoplePullParser;
import com.example.stwo.db.UserDataSource;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Signup extends ListActivity {
	
	
	private static final String LOGTAG = "rahul";
	UserDataSource datasource;
	
	SQLiteDatabase db1 = null;
	private static String DBNAME = "data.db";
	Button btn,btn1,btn2,btn3 = null;
	TextView tvw;
	EditText edt1,edt2,edt3,edt4,edt5 = null;
	Editable d1,d2,d3,d4,d5 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
        datasource =new UserDataSource(this);
		
		 datasource.open();

		
		edt1 = (EditText) findViewById(R.id.editText4);
		edt2 = (EditText) findViewById(R.id.editText1);
		edt3= (EditText) findViewById(R.id.editText2);
		edt4 = (EditText) findViewById(R.id.editText3);
		edt5 = (EditText) findViewById(R.id.editText5);
		
		
		List<People> p=datasource.findAll();
		if(p.size()== 0){
		createData();
		p=datasource.findAll();
		}
		
		ArrayAdapter<People> adapter=new ArrayAdapter<People>(this, android.R.layout.simple_expandable_list_item_1,p);
		setListAdapter(adapter);

		btn=(Button)findViewById(R.id.button1);
		//Database will be created through below method
		final SQLiteDatabase db= openOrCreateDatabase("DBNAME",Context.MODE_PRIVATE,null);
		//Create the table if it is not existing 

		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//fetching data we use editable and not string
				d1=edt1.getText();
				d2=edt2.getText();
				d3=edt3.getText();
				d4=edt4.getText();
				d5=edt5.getText();
				try{

					db.execSQL("CREATE TABLE IF NOT EXISTS Patients(ID INTEGER PRIMARY KEY AUTOINCREMENT,PASSWORD VARCHAR,NAME TEXT,EMAIL VARCHAR,AGE INT,ADDRESS VARCHAR,DIESEASE TEXT)");
					db.execSQL("INSERT INTO Patients(NAME,EMAIL,AGE,ADDRESS)VALUES (d1,d5,d2,d3,d4)");

					Cursor c = db.rawQuery("SELECT * FROM Patients", null);


					if(c!=null){
						if(c.moveToFirst()){
							do{
								String name =c.getString(c.getColumnIndex("NAME"));
								String password =c.getString(c.getColumnIndex("PASSWORD"));
								String email =c.getString(c.getColumnIndex("EMAIL"));
								String age =c.getString(c.getColumnIndex("AGE"));
								String address =c.getString(c.getColumnIndex("ADDRESS"));
								System.out.println(name);
								System.out.println(password);
								System.out.println(email);
								System.out.println(age);
								System.out.println(address);
							}while(c.moveToNext());
						}
					}

				}catch(Exception e){
					System.out.println(e);
				}

			}
		});
	}



private void createData() {
	PeoplePullParser parser=new PeoplePullParser();
	List<People> a=parser.parseXML(this);
	
	for (People people : a) {
		datasource.create(people);
	}
}
private void setListAdapter(ArrayAdapter<People> adapter) {
	// TODO Auto-generated method stub
	
}

@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	datasource.open();
}

@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	datasource.close();
}}