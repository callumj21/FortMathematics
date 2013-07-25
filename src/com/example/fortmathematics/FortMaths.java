package com.example.fortmathematics;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FortMaths extends Activity {

	private Button enterFort;
	private Button scores;

	private Cursor cursor;
	public static int flag = 0;
	private String game_tag = "Game";
	private int addFile = R.xml.add;
	private int subFile = R.xml.subtraction;
	private GameDbAdapter dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fort_maths);
		dbHelper = new GameDbAdapter(this);
		dbHelper.open();

		enterFort = (Button) findViewById(R.id.entry);
		enterFort.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(FortMaths.this, GameSelection.class);
				startActivity(i);
				finish();

			}

		});

		scores = (Button) findViewById(R.id.score);
		scores.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(FortMaths.this, ScoreSelectionMenu.class);
				startActivity(i);
			}

		});

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
		}
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.close();
			Log.d(game_tag, "Database Adapter Closed in " + game_tag);
		}
	}
}
