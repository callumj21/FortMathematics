package com.example.fortmathematics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GameDbHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "applicationdata";
	private static final int DATABASE_VERSION = 4;
	public static int gameTableCreation = 0;

	// Database creation sql statement for the add table
	private static final String GAME_CREATE = "create table game (_id integer primary key autoincrement, "
			+ "type text not null, g_set text not null, " +
			"description text not null, questions text not null, answers text not null);";;

	public GameDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		// Sets the variable to 1 so during a creation or updation of the
		// database pre entered instances are only entered into the table once
		gameTableCreation = 1;
		database.execSQL(GAME_CREATE);

	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(GameDbHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS game");

		onCreate(database);
	}
}
