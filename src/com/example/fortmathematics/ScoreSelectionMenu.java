package com.example.fortmathematics;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ScoreSelectionMenu extends Activity {

	private Spinner spinner1;
	private Spinner spinner2;
	private GameDbAdapter dbHelper;
	private Cursor cursor;
	private GameDbHelper help;
	private SQLiteDatabase db;
	
	private String hello;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_selections);

		dbHelper = new GameDbAdapter(this);
		dbHelper.open();

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
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
				 hello = spinner1.getSelectedItem().toString();
				if (hello.equals("Addition")) {
					updateSpinner();
				} else if(hello.equals("Subtraction")){ 
						updateSpinner();

				} else {
					ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
							ScoreSelectionMenu.this,
							android.R.layout.simple_spinner_item,
							new String[] { "nothing here" });
					adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner2.setAdapter(adapter2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	public void updateSpinner(){
		String sets = " ";

		help = new GameDbHelper(ScoreSelectionMenu.this);

		db = help.getWritableDatabase();
		
		if (hello.equals("Addition")) {
			cursor = db.rawQuery("SELECT * FROM game WHERE type = 'Addition'", null);

		} else if(hello.equals("Subtraction")){ 
			cursor = db.rawQuery("SELECT * FROM game WHERE type = 'Subtraction'", null);

		}
		startManagingCursor(cursor);

		cursor.moveToFirst();
		sets = sets
				+ cursor.getString(cursor
						.getColumnIndexOrThrow(GameDbAdapter.KEY_SET))
				+ ",";
		while (!cursor.isAfterLast()) {
			if (cursor.moveToNext())
				sets = sets
						+ cursor.getString(cursor
								.getColumnIndexOrThrow(GameDbAdapter.KEY_SET))
						+ ",";
		}
		System.out.println(sets);
		String setArray[] = sets.split(",");
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
				ScoreSelectionMenu.this,
				android.R.layout.simple_spinner_item,setArray);
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

}
