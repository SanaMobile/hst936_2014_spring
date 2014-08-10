package com.example.stwo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class Databasehandler extends SQLiteOpenHelper{
	public class userTable {    
		// EACH STUDENT HAS UNIQUE ID    
		public static final String ID = "_id";     
		public static final String NAME = "name";   
	public static final String EMAIl = "email";   
	public static final String ADDRESS = "address";     
	public static final String PASSWORD = "password";    
	public static final String AGE = "age";    
	public static final String TABLE_NAME = "users"; 
	
	
	}
	

	  private static final String DATABASE_NAME = "data.db";
	    // TOGGLE THIS NUMBER FOR UPDATING TABLES AND DATABASE  
	  private static final int DATABASE_VERSION = 3;
	  
	    public Databasehandler(Context context) 
	    {     
	    	super(context, DATABASE_NAME, null, DATABASE_VERSION);  
	    }

	    
	    private static String DB_PATH = "/data/data/com.exmple.stwo/databases/";
	    
	    
	    
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			  db.execSQL("CREATE TABLE " + userTable.TABLE_NAME    
					  + " (" + userTable.ID + " INTEGER PRIMARY KEY  AUTOINCREMENT,"      
					  + userTable.NAME + " TEXT,"      
					  + userTable.EMAIl + " VARCHAR,"
					  + userTable.PASSWORD + " VARCHAR,"
					  + userTable.ADDRESS + " VARCHAR,"  
					  + userTable.AGE + " INTEGER);");
			  
			  String query = "CREATE TABLE userlogin (Id INTEGER PRIMARY  KEY AUTOINCREMENT,username TEXT,email VARCHAR,age INTEGER,address VARCHAR,password INTEGER)";
              db.execSQL(query); 
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			
			  Log.w("LOG_TAG", "Upgrading database from version "        + oldVersion + " to " + newVersion + ",         which will destroy all old data");
		        // KILL PREVIOUS TABLES IF UPGRADED   
			  db.execSQL("DROP TABLE IF EXISTS " + userTable.TABLE_NAME);     

		        // CREATE NEW INSTANCE OF SCHEMA       
			  onCreate(db);
			
		}

		
		long one=addpeople("rahul","rahulkirant@gmail.com","rahul","singapore","20");
	    
		public long addpeople(String name,String email,String password,String address,String age) 
		{        // CREATE A CONTENTVALUE OBJECT       
			ContentValues cv = new ContentValues();     
			cv.put(userTable.NAME, name);     
			cv.put(userTable.EMAIl, email);   
			cv.put(userTable.PASSWORD, password);
			cv.put(userTable.ADDRESS, address);
			cv.put(userTable.AGE ,age);
			
	        // RETRIEVE WRITEABLE DATABASE AND INSERT   
		
		  SQLiteDatabase sd = getWritableDatabase();     
		  long result = sd.insert(userTable.TABLE_NAME, 
	        userTable.NAME, cv);       
		  return result;   
		  
		}
		
	    public Cursor getPeople()
	    {        SQLiteDatabase sd = getWritableDatabase();
        // WE ONLY NEED TO RETURN STUDENT IDS     
	    String[] cols = new String[] { userTable.ID,
	    		userTable.EMAIl,
	    		userTable.PASSWORD
	    		};
     
        // QUERY CLASS MAP FOR STUDENTS IN COURSE 
        Cursor c = sd.query(userTable.TABLE_NAME, cols,null,null, null,null, null);
        return c;    
        
	    }
	    
	    
	    public void open()throws SQLException {

	        // Open the database
	        String myPath = DB_PATH + DATABASE_NAME;
	        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null,
	                SQLiteDatabase.OPEN_READONLY);

	    }
	    public int Login(String username,String password)
	    {
	        String[] selectionArgs = new String[]{userTable.EMAIl, userTable.PASSWORD};
	        try
	        {
	            int i = 0;
	            Cursor c = null;
	            SQLiteDatabase db = null;
				c = db.rawQuery("select * from users where "+username+"=? and "+password+"=?", selectionArgs);
	            c.moveToFirst();
	            i = c.getCount(); 
	            c.close(); 
	            return i;
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	        return 0;
	    }
}