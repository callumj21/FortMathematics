package com.example.fortmathematics;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FortMaths extends Activity {

	private Button enterFort;
	private Button scores;
	private Button addGame;
	private Button about;
	private Cursor cursor;
	public static int flag = 0;
	private String game_tag = "Game";
	private int addFile = R.xml.add;
	private int subFile = R.xml.subtraction;
	private int multFile = R.xml.multiplication;
	private int divFile = R.xml.division;
	private int mixupFile = R.xml.mixup;
	private GameDbAdapter dbHelper;
	Context context = FortMaths.this;

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
		
		about = (Button) findViewById(R.id.about);
		about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(FortMaths.this, About.class);
				startActivity(i);
			}

		});

		addGame = (Button) findViewById(R.id.add_game);
		addGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(FortMaths.this, AddGame.class);
				startActivity(i);
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

		if (flag == 0 && GameDbHelper.gameTableCreation == 1) {
			 AsyncTask task = new ParseXMLFile().execute();
		}
//			final ProgressDialog progressDialog = ProgressDialog.show(this,
//					"Downloading all sets...", "please wait", true, false);
//
//			new Thread() {
//				public void run() {
//					try {
//						sleep(5000);
//					} catch (Exception e) {
//						e.printStackTrace();
//					} finally {
//						progressDialog.dismiss();
//					}
//				}
//			}.start();
//		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.close();
			Log.d(game_tag, "Database Adapter Closed in " + game_tag);
		}
	}
	
	

	private class ParseXMLFile extends AsyncTask<Void, Void, Boolean> {
		private ProgressDialog progressDialog ;

		@Override
		protected Boolean doInBackground(Void... Void) {
			flag = 1;
			try {
				addPopulate();
				subPopulate();
				multPopulate();
				divPopulate();
				mixPopulate();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		}
		


		protected void onPreExecute() {
			 progressDialog = (ProgressDialog) ProgressDialog.show(context,
				"Downloading sets from xml files...", "please wait", true, false);
	
	    }
		    protected void onPostExecute(final Boolean success) {
		        if (progressDialog.isShowing()) {
		            progressDialog.dismiss();
		        }
		        }

		private void subPopulate() throws XmlPullParserException, IOException {
			Resources res = context.getResources();
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
				eventType = xpp.next();
				if (!answers.equals("") && !set.equals("") && !des.equals("")
						&& !questions.equals("")) {
					dbHelper.createGame("Subtraction",set, des, questions, answers);
					set = des = questions = answers = "";
				}
			}

		}

		private void addPopulate() throws XmlPullParserException, IOException {
			Resources res = context.getResources();
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
					dbHelper.createGame("Addition",set, des, questions, answers);
					set = des = questions = answers = "";
				}
			}

		}
		
		private void multPopulate() throws XmlPullParserException, IOException {
			Resources res = context.getResources();
			XmlResourceParser xpp = res.getXml(multFile);
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
					dbHelper.createGame("Multiplication",set, des, questions, answers);
					set = des = questions = answers = "";
				}
			}

		}
		
		private void divPopulate() throws XmlPullParserException, IOException {
			Resources res = context.getResources();
			XmlResourceParser xpp = res.getXml(divFile);
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
					dbHelper.createGame("Division",set, des, questions, answers);
					set = des = questions = answers = "";
				}
			}

		}
		
		
		private void mixPopulate() throws XmlPullParserException, IOException {
			Resources res = context.getResources();
			XmlResourceParser xpp = res.getXml(mixupFile);
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
					dbHelper.createGame("Mixup",set, des, questions, answers);
					set = des = questions = answers = "";
				}
			}

		}
	}
	
	
}
