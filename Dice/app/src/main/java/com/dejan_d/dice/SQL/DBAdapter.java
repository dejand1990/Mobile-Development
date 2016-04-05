package com.dejan_d.dice.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
Here all the database connections are being handeld
 */
public class DBAdapter {

	private static final String TAG = "DBAdapter"; //used for logging database version changes
			
	// Field Names:
	public static final String KEY_ROWID = "id";
    public static final String KEY_TASK = "task";
	public static final String SCORE_ROWID = "_id";
	public static final String SCORE_AMOUNT = "score";
	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_TASK};
	public static final String[] ALL_SCORES = new String[] {SCORE_ROWID, SCORE_AMOUNT};

	// DataBase info:
	public static final String DATABASE_NAME = "dbToDo";
	public static final String DATABASE_TABLE = "mainToDo";
	public static final String DATABASE_SCORE_TABLE = "scoreTableNew";
	public static final int DATABASE_VERSION = 25; // The version number must be incremented each time a change to DB structure occurs.

	//SQL statements to create database
	private static final String DATABASE_CREATE_SQL = 
			"CREATE TABLE " + DATABASE_TABLE
			+ " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_TASK + " TEXT NOT NULL"
			+ ");";

    private static final String DATABASE_CREATE_SCORE =
            "CREATE TABLE " + DATABASE_SCORE_TABLE
            + " ( " + SCORE_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SCORE_AMOUNT + " TEXT NOT NULL"
            + ");";

	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
        this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Add a new set of values to be inserted into the database. For the assignments
	public void fillDatabase() {
        db = myDBHelper.getWritableDatabase();
		ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TASK, "Drink 1 shot!");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "Take a sip of your drink");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "Eat a lemon.");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "Give a shot away to someone else");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "Hold your breath for 30 seconds");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "Walk a straight line");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "Touch your nose with your finger");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "Finish your drink. EVERYTHING left.");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "Tell someone else what to do.");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "Name ur current crush.");
        db.insert(DATABASE_TABLE, null, initialValues);
        initialValues.put(KEY_TASK, "You got lucky! Do nothing!");
        db.insert(DATABASE_TABLE, null, initialValues);
	}

	//Adds the assignment rolled to the database
	public void addScoreToDatabase(String todo) {
		db = myDBHelper.getWritableDatabase();
		ContentValues scoreValues = new ContentValues();
		scoreValues.put(SCORE_AMOUNT, todo);
		db.insert(DATABASE_SCORE_TABLE, null, scoreValues);
	}
	
	// Return all data in the rolled table.
	public Cursor getAllRows() {
		db = myDBHelper.getReadableDatabase();
		Cursor c = 	db.query(true, DATABASE_SCORE_TABLE, ALL_SCORES, null, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId) from the assignment table
	public Cursor getRow(int rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	//Empties the rolled score table and sets the SCORE_ROWID to 1
	public void deleteAll(){
		db = myDBHelper.getWritableDatabase();
		db.execSQL("delete from "+ DATABASE_SCORE_TABLE);

		//Update method to set value to 1
		db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='"+ DATABASE_SCORE_TABLE+"';");
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);
            _db.execSQL(DATABASE_CREATE_SCORE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}

