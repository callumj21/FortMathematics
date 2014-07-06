package com.example.fortmathematics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ScoreDbAdapter {
	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_SCORE = "g_score";
	public static final String KEY_TYPE = "type";
	public static final String KEY_SET = "g_set";
	public static final String KEY_QUESTIONS = "questions";
	public static final String KEY_ANSWERS = "answers";
	public static final String KEY_USER = "user";
	public static final String KEY_TOTAL = "total_time";
	public static final String KEY_TIMES = "times";
	public static final String KEY_RESULTS = "results";

	private static final String SCORE_TABLE = "score";

	private Context context;
	private SQLiteDatabase database;
	private ScoreDbHelper dbHelper;
	private String DB_TAG = "DbAdapter";

	public ScoreDbAdapter(Context context) {
		this.context = context;
	}

	// Opens the database
	public ScoreDbAdapter open() throws SQLException {
		dbHelper = new ScoreDbHelper(context);
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
	public long createScore(String name, String score, String type, String set,
			String questions, String answers, String user, String total,
			String times, String results) {
		ContentValues initialValues = createContentValues(name, score, type,
				set, questions, answers, user, total, times, results);
		return database.insert(SCORE_TABLE, null, initialValues);
	}

	/**
	 * * Return a Cursor over the list of all scores in the database * * @return
	 * Cursor over all hotels
	 */

	public Cursor fetchAllScores() {
		return database.query(SCORE_TABLE, new String[] { KEY_ROWID, KEY_NAME,
				KEY_SCORE, KEY_TYPE, KEY_SET, KEY_QUESTIONS, KEY_ANSWERS,
				KEY_TIMES, KEY_TOTAL, KEY_RESULTS, KEY_USER }, null, null,
				null, null, null);
	}

	// Return a Cursor positioned at the defined addition set

	public Cursor fetchScore(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, SCORE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_SCORE, KEY_TYPE, KEY_SET,
				KEY_QUESTIONS, KEY_ANSWERS, KEY_TIMES, KEY_TOTAL, KEY_RESULTS,
				KEY_USER }, KEY_ROWID + "=" + rowId, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String name, String score,
			String type, String set, String questions, String answers,
			String user, String total, String times, String results) {
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_SCORE, score);
		values.put(KEY_TYPE, type);
		values.put(KEY_SET, set);
		values.put(KEY_QUESTIONS, questions);
		values.put(KEY_ANSWERS, answers);
		values.put(KEY_USER, user);
		values.put(KEY_TOTAL, total);
		values.put(KEY_TIMES, times);
		values.put(KEY_RESULTS, results);
		Log.d(DB_TAG, "Values successfully entered into add database table");
		return values;

	}

}
