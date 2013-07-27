package com.example.fortmathematics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScoreDbHelper  extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "applicationdata1";
	private static final int DATABASE_VERSION = 4;
	public static int scoreTableCreation = 0;

	// Database creation sql statement for the add table
	private static final String SCORE_CREATE = "create table score (_id integer primary key autoincrement, "
			+ "name text not null, g_score text not null, type text not null, g_set text not null, " +
			"questions text not null, answers text not null, times text not null, total_time text not null, " +
			"results text not null, user text not null);";

	public ScoreDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		// Sets the variable to 1 so during a creation or updation of the
		// database pre entered instances are only entered into the table once
		scoreTableCreation = 1;
		database.execSQL(SCORE_CREATE);

	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(ScoreDbHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS score");

		onCreate(database);
	}
}