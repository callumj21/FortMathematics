package com.example.fortmathematics;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ScoresList extends ListActivity {

	private ScoreDbAdapter dbHelper;
	private ScoreDbHelper help;
	private Cursor cursor;
	private SQLiteDatabase db;

	private TextView scoreText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_list);

		this.getListView().setDividerHeight(2);
		dbHelper = new ScoreDbAdapter(this);
		dbHelper.open();

		scoreText = (TextView) findViewById(R.id.score_text);

		fillData();
	}

	// Deals with putting the required information in the list
	@SuppressWarnings("deprecation")
	private void fillData() {
		help = new ScoreDbHelper(ScoresList.this);
		System.out.println(ScoreSelectionMenu.getSet());
		System.out.println(ScoreSelectionMenu.getType());
		scoreText
				.setText("You are currently viewing the scores for "
						+ ScoreSelectionMenu.getType()
						+ " "
						+ ScoreSelectionMenu.getSet()
						+ " . To view more details about a  score select one from the list ");
		db = help.getWritableDatabase();
		cursor = db.rawQuery("SELECT * FROM score WHERE type = '"
				+ ScoreSelectionMenu.getType() + "' AND g_set = '"
				+ ScoreSelectionMenu.getSet() + "' ORDER BY COALESCE(g_score,total_time) DESC", null);

		startManagingCursor(cursor);

		// Items to go in the cursor adapter
		String[] from = new String[] { ScoreDbAdapter.KEY_NAME,
				ScoreDbAdapter.KEY_SCORE, ScoreDbAdapter.KEY_TOTAL };
		// Positions to store the items
		int[] to = new int[] { R.id.label, R.id.label1, R.id.label2 };

		// Now create an array adapter and set it to display using our row
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter hotel = new SimpleCursorAdapter(this,
				R.layout.score_row, cursor, from, to);
		setListAdapter(hotel);
	}
	
	   @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) { 
	        super.onListItemClick(l, v, position, id); 
	        Cursor score = dbHelper.fetchScore(id); 
	        startManagingCursor(score); 
	        Intent i = new Intent(this, PlayerResults.class); 
	        // Put any details in with the intent if they exist already applies 
	        // to an updation 
	        i.putExtra(ScoreDbAdapter.KEY_ROWID, id); 
	        // Activity returns an result if called with startActivityForResult 
	        startActivity(i); 
	    } 
}
