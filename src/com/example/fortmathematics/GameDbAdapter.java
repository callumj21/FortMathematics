package com.example.fortmathematics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameDbAdapter {
	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TYPE = "type";
	public static final String KEY_SET = "g_set";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_QUESTIONS = "questions";
	public static final String KEY_ANSWERS = "answers";
	private static final String GAME_TABLE = "game";


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
	public long createGame(String type,String set, String description, String questions,
			String answers) {
		ContentValues initialValues = createContentValues(type,set, description,
				questions, answers);
		Log.d(DB_TAG, "Game created Successfully values are " + description
				+ " " + questions);
		return database.insert(GAME_TABLE, null, initialValues);
	}

	/**
	 * * Return a Cursor over the list of all  games in the database * * @return
	 * Cursor over all hotels
	 */

	public Cursor fetchAllGames() {
		return database.query(GAME_TABLE, new String[] { KEY_ROWID, KEY_TYPE, KEY_SET,
				KEY_DESCRIPTION, KEY_QUESTIONS, KEY_ANSWERS }, null,
				null, null, null, null);
	}

	// Return a Cursor positioned at the defined addition set

	public Cursor fetchGame(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, GAME_TABLE, new String[] {
				KEY_ROWID, 	KEY_TYPE, KEY_SET, KEY_DESCRIPTION, KEY_QUESTIONS,
				KEY_ANSWERS }, KEY_ROWID + "=" + rowId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String type, String set, String description,
			String questions, String answers) {
		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, type);
		values.put(KEY_SET, set);
		values.put(KEY_DESCRIPTION, description);
		values.put(KEY_QUESTIONS, questions);
		values.put(KEY_ANSWERS, answers);
		Log.d(DB_TAG, "Values successfully entered into add database table");
		return values;

	}


}
