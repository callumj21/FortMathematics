package com.example.fortmathematics;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ScoreSelectionMenu extends Activity {

	private Spinner spinner1;
	private Spinner spinner2;
	private GameDbAdapter dbHelper;
	private Cursor cursor;
	private GameDbHelper help;
	private SQLiteDatabase db;

	private static String type;
	private static String set;

	private Button viewScores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_selections);

		dbHelper = new GameDbAdapter(this);
		dbHelper.open();

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		viewScores = (Button) findViewById(R.id.table_button);
		ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"Addition", "Subtraction", "Multiplication",
						"Division", "Mixup" });
		adapterType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapterType);
		spinner2.setAdapter(adapterType);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				type = spinner1.getSelectedItem().toString();
				updateSpinner();

				viewScores.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						set = spinner2.getSelectedItem().toString();
						Intent i = new Intent(ScoreSelectionMenu.this,
								ScoresList.class);
						startActivity(i);
					}

				});

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void updateSpinner() {
		String sets = " ";

		help = new GameDbHelper(ScoreSelectionMenu.this);

		db = help.getWritableDatabase();

		if (type.equals("Addition")) {
			cursor = db.rawQuery("SELECT * FROM game WHERE type = 'Addition'",
					null);

		} else if (type.equals("Subtraction")) {
			cursor = db.rawQuery(
					"SELECT * FROM game WHERE type = 'Subtraction'", null);
		} else if (type.equals("Multiplication")) {
			cursor = db.rawQuery(
					"SELECT * FROM game WHERE type = 'Multiplication'", null);

		} else if (type.equals("Division")) {
			cursor = db.rawQuery(
					"SELECT * FROM game WHERE type = 'Division'", null);

		} else if (type.equals("Mixup")) {
			cursor = db.rawQuery(
					"SELECT * FROM game WHERE type = 'Mixup'", null);

		}
		startManagingCursor(cursor);

		cursor.moveToFirst();
		sets = sets
				+ cursor.getString(cursor
						.getColumnIndexOrThrow(GameDbAdapter.KEY_SET)) + ",";
		sets = sets.substring(1);
		while (!cursor.isAfterLast()) {
			if (cursor.moveToNext())
				sets = sets
						+ cursor.getString(cursor
								.getColumnIndexOrThrow(GameDbAdapter.KEY_SET))
						+ ",";
		}
		System.out.println(sets);
		String setArray[] = sets.split(",");
		for (int i = 0; i < setArray.length; i++) {
			System.out.println(setArray[i]);
		}
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
				ScoreSelectionMenu.this, android.R.layout.simple_spinner_item,
				setArray);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}

	public static String getType() {
		return type;
	}

	public static String getSet() {
		return set;
	}

}
