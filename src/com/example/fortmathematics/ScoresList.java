package com.example.fortmathematics;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class ScoresList extends ListActivity {

	private ScoreDbAdapter dbHelper;
	private ScoreDbHelper help;
	private Cursor cursor;
	private SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.score_list);

		this.getListView().setDividerHeight(2);
		dbHelper = new ScoreDbAdapter(this);
		dbHelper.open();

		fillData();
	}

	// Deals with putting the required information in the list
	@SuppressWarnings("deprecation")
	private void fillData() {
		help = new ScoreDbHelper(ScoresList.this);
		System.out.println(ScoreSelectionMenu.getSet());
		System.out.println(ScoreSelectionMenu.getType());
		db = help.getWritableDatabase();
			cursor = db.rawQuery("SELECT * FROM score WHERE type = '"
					+ ScoreSelectionMenu.getType() + "' AND g_set = '" + ScoreSelectionMenu.getSet() + "'", null);
	
		startManagingCursor(cursor);

		// Items to go in the cursor adapter
		String[] from = new String[] { ScoreDbAdapter.KEY_NAME,
				ScoreDbAdapter.KEY_SCORE,  ScoreDbAdapter.KEY_TOTAL };
		// Positions to store the items
		int[] to = new int[] { R.id.label, R.id.label1, R.id.label2};

		// Now create an array adapter and set it to display using our row
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter hotel = new SimpleCursorAdapter(this,
				R.layout.score_row, cursor, from, to);
		setListAdapter(hotel);
	}
}
