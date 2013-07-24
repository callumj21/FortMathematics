package com.example.fortmathematics;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowSets extends ListActivity {
	private GameDbAdapter dbHelper;

	private Cursor cursor;
	public static int flag = 0;
	private String game_tag = "Game";
	private int addFile = R.xml.add;
	private int subFile = R.xml.subtraction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new GameDbAdapter(this);
		dbHelper.open();

		setContentView(R.layout.addition_list);
		this.getListView().setDividerHeight(2);

		// Create database Adapter and then open it

		// If the addition table is just created or has been updated and it is
		// the
		// first time in this activity add the pre made hotels
		if (flag == 0 && GameDbHelper.addTableCreation == 1
				&& GameDbHelper.subTableCreation == 1) {
			flag = 1;
			try {
				addPopulate();
				subPopulate();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			final ProgressDialog progressDialog = ProgressDialog.show(this,
					"Downloading all sets...", "please wait", true, false);

			new Thread() {
				public void run() {
					try {
						sleep(5000);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						progressDialog.dismiss();
					}
				}
			}.start();

			// dbHelper.createAdd(
			// "Set 1",
			// "Easy",
			// " 10 + 27, 15 + 13, 28 + 23, 21 + 33, 44 + 22,8 + 29, 16+18 , 103 + 17, 115 + 26, 46 + 33",
			// "37,28,51,44,66,37,34,120,141,79");
			// dbHelper.createAdd(
			// "Set 2",
			// "Easy",
			// " 34 + 21, 66 + 22, 15 + 23 , (8 + 2) + 29 , (6 + 3) + 22 , 16 + 60 , 57 + 13 , 72 + 29, 14 + 43 , (19 + 2) + 20",
			// "55,88,38,39,31,76,70,99,57,41");

		}
		if (GameSelection.getSelection().equals("Addition")) {
			fillDataAdd();
		} else {
			System.out.println("here");
			fillDataSub();
		}

		registerForContextMenu(getListView());
	}

	private void subPopulate() throws XmlPullParserException, IOException {
		Resources res = this.getResources();
		XmlResourceParser xpp = res.getXml(subFile);
		xpp.next();
		int eventType = xpp.getEventType();
		String set = "";
		String des = "";
		String questions = "";
		String answers = "";
		while (eventType != XmlPullParser.END_DOCUMENT) {

			if (eventType == XmlPullParser.START_DOCUMENT) {
			} else if (eventType == XmlPullParser.START_TAG
					&& xpp.getName().equals("set")) {
				set = xpp.nextText();
			} else if (eventType == XmlPullParser.START_TAG
					&& xpp.getName().equals("description")) {
				des = xpp.nextText();
			} else if (eventType == XmlPullParser.START_TAG
					&& xpp.getName().equals("questions")) {
				questions = xpp.nextText();
			} else if (eventType == XmlPullParser.START_TAG
					&& xpp.getName().equals("answers")) {
				answers = xpp.nextText();
			}
			System.out.println("questions");
			eventType = xpp.next();
			if (!answers.equals("") && !set.equals("") && !des.equals("")
					&& !questions.equals("")) {
				dbHelper.createSub(set, des, questions, answers);
				set = des = questions = answers = "";
			}
		}

	}

	private void addPopulate() throws XmlPullParserException, IOException {
		Resources res = this.getResources();
		XmlResourceParser xpp = res.getXml(addFile);
		xpp.next();
		int eventType = xpp.getEventType();
		String set = "";
		String des = "";
		String questions = "";
		String answers = "";
		while (eventType != XmlPullParser.END_DOCUMENT) {

			if (eventType == XmlPullParser.START_DOCUMENT) {
			} else if (eventType == XmlPullParser.START_TAG
					&& xpp.getName().equals("set")) {
				set = xpp.nextText();
			} else if (eventType == XmlPullParser.START_TAG
					&& xpp.getName().equals("description")) {
				des = xpp.nextText();
			} else if (eventType == XmlPullParser.START_TAG
					&& xpp.getName().equals("questions")) {
				questions = xpp.nextText();
			} else if (eventType == XmlPullParser.START_TAG
					&& xpp.getName().equals("answers")) {
				answers = xpp.nextText();
			}
			eventType = xpp.next();
			if (!answers.equals("") && !set.equals("") && !des.equals("")
					&& !questions.equals("")) {
				dbHelper.createAdd(set, des, questions, answers);
				set = des = questions = answers = "";
			}
		}

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

	// Deals with putting the required information in the list
	@SuppressWarnings("deprecation")
	private void fillDataAdd() {

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

	// Deals with putting the required information in the list
	@SuppressWarnings("deprecation")
	private void fillDataSub() {

		cursor = dbHelper.fetchAllSubs();
		startManagingCursor(cursor);

		// Items to go in the cursor adapter
		String[] from = new String[] { GameDbAdapter.KEY_S_SET,
				GameDbAdapter.KEY_S_DESCRIPTION };
		// Positions to store the items
		int[] to = new int[] { R.id.label, R.id.label1 };

		// Now create an array adapter and set it to display using our row
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter hotel = new SimpleCursorAdapter(this,
				R.layout.addition_row, cursor, from, to);
		setListAdapter(hotel);
		Log.d(game_tag, "Displayed Subs");
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
