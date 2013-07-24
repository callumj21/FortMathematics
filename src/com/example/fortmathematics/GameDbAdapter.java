package com.example.fortmathematics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameDbAdapter {
	// Database fields
	public static final String KEY_A_ROWID = "_id";
	public static final String KEY_A_SET = "a_set";
	public static final String KEY_A_DESCRIPTION = "a_description";
	public static final String KEY_A_QUESTIONS = "a_questions";
	public static final String KEY_A_ANSWERS = "a_answers";
	private static final String ADD_TABLE = "addition";

	public static final String KEY_S_ROWID = "_id";
	public static final String KEY_S_SET = "s_set";
	public static final String KEY_S_DESCRIPTION = "s_description";
	public static final String KEY_S_QUESTIONS = "s_questions";
	public static final String KEY_S_ANSWERS = "s_answers";
	private static final String SUB_TABLE = "subtraction";

	private Context context;
	private SQLiteDatabase database;
	private GameDbHelper dbHelper;
	private String DB_TAG = "DbAdapter";

	public GameDbAdapter(Context context) {
		this.context = context;
	}

	// Opens the database
	public GameDbAdapter open() throws SQLException {
		dbHelper = new GameDbHelper(context);
		database = dbHelper.getWritableDatabase();
		Log.d(DB_TAG, "Successfully Opened the Database");
		return this;
	}

	// Closes the Database
	public void close() {
		Log.d(DB_TAG, "Successfully closed the Database Adapter");
		dbHelper.close();
	}

	// Creates the Add Instance
	public long createAdd(String set, String description, String questions,
			String answers) {
		ContentValues initialValues = createContentValues(set, description,
				questions, answers);
		Log.d(DB_TAG, "Hotel created Successfully values are " + description
				+ " " + questions);
		return database.insert(ADD_TABLE, null, initialValues);
	}

	/**
	 * * Return a Cursor over the list of all addition games in the database * * @return
	 * Cursor over all hotels
	 */

	public Cursor fetchAllAdds() {
		return database.query(ADD_TABLE, new String[] { KEY_A_ROWID, KEY_A_SET,
				KEY_A_DESCRIPTION, KEY_A_QUESTIONS, KEY_A_ANSWERS }, null,
				null, null, null, null);
	}

	// Return a Cursor positioned at the defined addition set

	public Cursor fetchAddition(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, ADD_TABLE, new String[] {
				KEY_A_ROWID, KEY_A_SET, KEY_A_DESCRIPTION, KEY_A_QUESTIONS,
				KEY_A_ANSWERS }, KEY_A_ROWID + "=" + rowId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String set, String description,
			String questions, String answers) {
		ContentValues values = new ContentValues();
		values.put(KEY_A_SET, set);
		values.put(KEY_A_DESCRIPTION, description);
		values.put(KEY_A_QUESTIONS, questions);
		values.put(KEY_A_ANSWERS, answers);
		Log.d(DB_TAG, "Values successfully entered into add database table");
		return values;

	}

	public long createSub(String set, String description, String questions,
			String answers) {
		ContentValues initialValues = sCreateContentValues(set, description,
				questions, answers);
		Log.d(DB_TAG, "subtraction created Successfully values are " + description
				+ " " + questions);
		return database.insert(SUB_TABLE, null, initialValues);
	}
	
	public Cursor fetchAllSubs() {
		return database.query(SUB_TABLE, new String[] { KEY_S_ROWID, KEY_S_SET,
				KEY_S_DESCRIPTION, KEY_S_QUESTIONS, KEY_S_ANSWERS }, null,
				null, null, null, null);
	}

	public Cursor fetchSubtraction(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, SUB_TABLE, new String[] {
				KEY_S_ROWID, KEY_S_SET, KEY_S_DESCRIPTION, KEY_S_QUESTIONS,
				KEY_S_ANSWERS }, KEY_S_ROWID + "=" + rowId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues sCreateContentValues(String set, String description,
			String questions, String answers) {
		ContentValues values = new ContentValues();
		values.put(KEY_S_SET, set);
		values.put(KEY_S_DESCRIPTION, description);
		values.put(KEY_S_QUESTIONS, questions);
		values.put(KEY_S_ANSWERS, answers);
		Log.d(DB_TAG, "Values successfully entered into sub database table");
		return values;
	}
}
