package com.example.fortmathematics;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FortMaths extends Activity {

	private Button enterFort;
	private Button scores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fort_maths);

		enterFort = (Button) findViewById(R.id.entry);
		enterFort.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(FortMaths.this, GameSelection.class);
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
	}

}
