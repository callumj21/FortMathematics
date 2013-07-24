package com.example.fortmathematics;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class GameSelection extends ListActivity {
	private static String phone = null;
	private static int phoneListExists = 0;
	Context context = GameSelection.this;
	private static String gameSelection;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection_screen);

		// Create the Simple Adaptar
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.selection_row, new String[] { "gameMode",
						"description", }, new int[] { R.id.text1, R.id.text2});
		// Call the populateList Method
		populateList();
		setListAdapter(adapter);

		
	}

	static final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	private void populateList() {
		// Goes into if statement if Phone activity has been activated earlier
		// on in the run of the app
		if (phoneListExists == 1) {
			// Clears the list of phone numbers
			list.clear();
		}
		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put("gameMode", "Addition");
		temp.put("description", "In this game mode you will have to answer addition questions from a chosen set");
		list.add(temp);
		HashMap<String, String> temp2 = new HashMap<String, String>();
		temp2.put("gameMode", "Subtraction");
		temp2.put("description", "In this game mode you will have to answer subtraction questions from a chosen set");
		list.add(temp2);
		HashMap<String, String> temp3 = new HashMap<String, String>();
		temp3.put("gameMode", "Multiplications");
		temp3.put("description", "In this game mode you will have to answer multiplication questions from a chosen set");
		list.add(temp3);
		HashMap<String, String> temp4 = new HashMap<String, String>();
		temp4.put("gameMode", "Division");
		temp4.put("description", "In this game mode you will have to answer division questions from a chosen set");
		list.add(temp4);
		HashMap<String, String> temp5 = new HashMap<String, String>();
		temp5.put("gameMode", "Mixup");
		temp5.put("description", "In this game mode you will have to answer a mixture of questions from a chosen set");
		list.add(temp5);
		
	}



	// Method for when a List Item is clicked
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		// Checks what item was clicked then sets the phone number corresponding
		// to that list item
		if (position == 0) {
			
			gameSelection = "Addition";

		} else if (position == 1) {
			gameSelection = "Subtraction";

		}
		//else if (position == 2) {
//			phone = "tel:+441314774000";
//			Log.d(PHONE_TAG, "Phone number is set too " + phone);
//			registerForContextMenu(getListView());
//			v.showContextMenu();
//			unregisterForContextMenu(getListView());
//
//		} else if (position == 3) {
//			phone = "tel:+441313332255";
//			Log.d(PHONE_TAG, "Phone number is set too " + phone);
//			registerForContextMenu(getListView());
//			v.showContextMenu();
//			unregisterForContextMenu(getListView());
//
//		} else if (position == 4) {
//			phone = "tel:+441315556363";
//			Log.d(PHONE_TAG, "Phone number is set too " + phone);
//			registerForContextMenu(getListView());
//			v.showContextMenu();
//			unregisterForContextMenu(getListView());
		Intent i = new Intent(GameSelection.this,ShowSets.class);
		startActivity(i);
		}
	
	
	public static String getSelection(){
		return gameSelection;
	}

}
