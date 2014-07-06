package com.example.fortmathematics;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowSets extends ListActivity {
	private GameDbAdapter dbHelper;
	private GameDbHelper help;

	private String game_tag = "Game";
	private Cursor cursor;
	private SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.addition_list);
		this.getListView().setDividerHeight(2);
		dbHelper = new GameDbAdapter(this);
		dbHelper.open();

		fillData();

	}

	// Prepares to go to Hotel Details in order for an update to take place
	@SuppressWarnings("deprecation")
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, Game.class);

		Cursor a = dbHelper.fetchGame(id);
		startManagingCursor(a);

		i.putExtra(GameDbAdapter.KEY_ROWID, id);

		startActivity(i);

		finish();
	}

	// Deals with putting the required information in the list
	@SuppressWarnings("deprecation")
	private void fillData() {
		help = new GameDbHelper(ShowSets.this);

		db = help.getWritableDatabase();
		if (GameSelection.getSelection().equals("Addition")) {
			cursor = db.rawQuery("SELECT * FROM game WHERE type = 'Addition'",
					null);
		} else if (GameSelection.getSelection().equals("Subtraction")) {
			cursor = db.rawQuery(
					"SELECT * FROM game WHERE type = 'Subtraction'", null);

		} else if (GameSelection.getSelection().equals("Multiplication")) {
			cursor = db.rawQuery(
					"SELECT * From game WHERE type = 'Multiplication'", null);
		} else if (GameSelection.getSelection().equals("Division")) {
			cursor = db.rawQuery(
					"SELECT * From game WHERE type = 'Division'", null);
		} else if (GameSelection.getSelection().equals("Mixup")) {
			cursor = db.rawQuery(
					"SELECT * From game WHERE type = 'Mixup'", null);
		}
		startManagingCursor(cursor);

		// Items to go in the cursor adapter
		String[] from = new String[] { GameDbAdapter.KEY_SET,
				GameDbAdapter.KEY_DESCRIPTION };
		// Positions to store the items
		int[] to = new int[] { R.id.label, R.id.label1 };

		// Now create an array adapter and set it to display using our row
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter hotel = new SimpleCursorAdapter(this,
				R.layout.addition_row, cursor, from, to);
		setListAdapter(hotel);
		Log.d(game_tag, "Displayed Sets");
	}

	// Called when activity finishes in order to close the database adapter
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.close();
			Log.d(game_tag, "Database Adapter Closed in " + game_tag);
		}
	}

	/**
	 * This below will hopefully be auto generated to an extent
	 */
	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(ShowSets.this, GameSelection.class);
		startActivity(i);
		finish();
	}

}
