package com.example.stwo.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDataSource {

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String LOGTAG = "Rahul kiran";
	
	
	private static final String[] allcolumns={
		UsersDBHelper.COLUMN_ID,
		UsersDBHelper.COLUMN_NAME,
		UsersDBHelper.COLUMN_EMAIL,
		UsersDBHelper.COLUMN_AGE,
		UsersDBHelper.COLUMN_PASSWORD,
		UsersDBHelper.COLUMN_ADDRESS
	};
	
	
	public UserDataSource(Context context){
	
		dbhelper=new UsersDBHelper(context);
		
	}
	
	public void open(){
		Log.i(LOGTAG,"Database opened");
		//returns connection with the database
		database=dbhelper.getWritableDatabase();
	}
	
	public void close(){
		Log.i(LOGTAG,"Database closed");
		dbhelper.close();
	}
	
	public People create(People p){
		
		//gives mapping interface
		ContentValues values=new ContentValues();
		values.put(UsersDBHelper.COLUMN_NAME, p.getName());
		values.put(UsersDBHelper.COLUMN_EMAIL, p.getEmail());
		values.put(UsersDBHelper.COLUMN_AGE, p.getAge());
		values.put(UsersDBHelper.COLUMN_PASSWORD, p.getPassword());
		values.put(UsersDBHelper.COLUMN_ADDRESS, p.getAddress());
		
		long insertid=database.insert(UsersDBHelper.TABLE1, null, values);
		
		p.setId(insertid);
		return p;
		
	}
	
	public List<People> findAll(){
		List<People> p=new ArrayList<People>();
		
		Cursor cursor=database.query(UsersDBHelper.TABLE1, allcolumns, null, null, null, null, null);
		
		Log.i(LOGTAG, "Returned" + cursor.getCount() + "rows");
		
		//cursor starts before the first row
		if(cursor.getCount()>0){
			while(cursor.moveToNext()){
				People c=new People();
				c.setId(cursor.getLong(cursor.getColumnIndex(UsersDBHelper.COLUMN_ID)));
				c.setName(cursor.getString(cursor.getColumnIndex(UsersDBHelper.COLUMN_NAME)));
			    p.add(c);
			}
		}
		return p;
		
		
	}
}
