package com.example.fortmathematics;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Addition extends ListActivity {
	private GameDbAdapter dbHelper;

	private Cursor cursor;
	public static int flag = 0;
	private String game_tag = "Game";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addition_list);
		this.getListView().setDividerHeight(2);

		// Create database Adapter and then open it
		dbHelper = new GameDbAdapter(this);
		dbHelper.open();
		// If the hotel table is just created or has been updated and it is the
		// first time in this activity add the pre made hotels
		if (flag == 0 && GameDbHelper.gameTableCreation == 1) {
			flag = 1;
			dbHelper.createAdd("Set 1","Easy", "10 + 27, 15 + 13, 28 + 23","37,28,51");

		}

		
		
		fillData();

		registerForContextMenu(getListView());
	}





	// Prepares to go to Hotel Details in order for an update to take place
	@SuppressWarnings("deprecation")
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor hotel = dbHelper.fetchAddition(id);
		startManagingCursor(hotel);
		
			Intent i = new Intent(this, Game.class);
			// Put any details in with the intent if they exist already applies
			// to an updation
			i.putExtra(GameDbAdapter.KEY_A_ROWID, id);
			// Activity returns an result if called with startActivityForResult

			startActivity(i);

				finish();
	}

	// Called with the result of the other activity
	// requestCode was the origin request code send to the activity
	// resultCode is the return code, 0 is everything is ok
	// intend can be use to get some data from the caller
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();

	}

	// Deals with putting the required information in the list
	@SuppressWarnings("deprecation")
	private void fillData() {
		// Get all the hotels from the database
		cursor = dbHelper.fetchAllAdds();
		startManagingCursor(cursor);

		// Items to go in the cursor adapter
		String[] from = new String[] { GameDbAdapter.KEY_A_SET,
				GameDbAdapter.KEY_A_DESCRIPTION };
		// Positions to store the items
		int[] to = new int[] { R.id.label, R.id.label1 };

		// Now create an array adapter and set it to display using our row
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter hotel = new SimpleCursorAdapter(this,
				R.layout.addition_row, cursor, from, to);
		setListAdapter(hotel);
		Log.d(game_tag, "Displayed Adds");
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

}
