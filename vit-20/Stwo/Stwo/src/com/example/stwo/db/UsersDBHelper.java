package com.example.stwo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UsersDBHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "Rahul kiran";
	
	private static final String DATABASE_NAME = "Users.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE1 = "people";
	public static final String COLUMN_ID = "peopleId";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_IMAGE = "image";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_AGE = "age";
	
	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE1 + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_NAME + " TEXT, " +
			COLUMN_EMAIL + " VARCHAR, " +
			COLUMN_ADDRESS + " VARCHAR, " +
			COLUMN_IMAGE + " TEXT, " +
			COLUMN_PASSWORD + " VARCHAR, "+ 
			COLUMN_AGE+ " INT " +
			")";
			
	public static final String TABLE2 = "doctor";
	
	
	public UsersDBHelper(Context context) {
		super(context,DATABASE_NAME,null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
     db.execSQL(TABLE_CREATE);
     Log.i(LOGTAG, "Table has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
    db.execSQL("DROP TABLE IF EXISTS" + TABLE1);
	}

}
