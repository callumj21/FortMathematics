package com.example.fortmathematics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GameDbHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "applicationdata";
	private static final int DATABASE_VERSION = 35;
	public static int addTableCreation = 0;
	public static int subTableCreation = 0;

	// Database creation sql statement for the add table
	private static final String ADD_CREATE = "create table addition (_id integer primary key autoincrement, "
			+ "a_set text not null, a_description text not null, a_questions text not null, a_answers text not null);";
	private static final String SUB_CREATE = "create table subtraction (_id integer primary key autoincrement, "
			+ "s_set text not null, s_description text not null, s_questions text not null, s_answers text not null);";
	public GameDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		// Sets the variable to 1 so during a creation or updation of the
		// database pre entered instances are only entered into the table once
		addTableCreation = 1;
		subTableCreation = 1;
		database.execSQL(ADD_CREATE);
		database.execSQL(SUB_CREATE);

	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(GameDbHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS addition");
		database.execSQL("DROP TABLE IF EXISTS subtraction");

		onCreate(database);
	}
}
