package sana.arogya;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBAdapter {
	
	private static final String DATABASE_NAME = "XYValue";
	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE = "CREATE TABLE axisvalues (_id integer primary key autoincrement, "+
			"content TEXT not null, " +
			"sex INT not null," +
			"age REAL not null," +
			"p3 REAL not null," +
			"p25 REAL not null," +
			"p50 REAL not null," +
			"p75 REAL not null, "+
			"p97 REAL not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	public SQLiteDatabase open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return db;
	}

	public void close() {
		DBHelper.close();
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS axisvalues");
			onCreate(db);
		}
	}

}

