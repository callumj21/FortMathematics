package com.example.fortmathematics;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameSummary extends Activity {
	private TextView gQuestions;
	private TextView gResults;
	private TextView gTimes;
	private Button save;
	private TextView summary;
	ArrayList<String> qList = new ArrayList<String>();
	ArrayList<String> rList = new ArrayList<String>();
	ArrayList<Float> tList = new ArrayList<Float>();
	private float totalTime = 0;
	private int correctAnswers = 0;

	private String qString = "";
	private String rString = "";
	private String tString = "";

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.summary);

		summary = (TextView) findViewById(R.id.message);

		gQuestions = (TextView) findViewById(R.id.quest);
		gResults = (TextView) findViewById(R.id.question_result);
		gTimes = (TextView) findViewById(R.id.question_times);

		qList = Game.getQuestions();
		rList = Game.getResults();
		tList = Game.getTimes();

		for (int i = 0; i < qList.size(); i++) {
			if (i != qList.size() - 1) {
				tString = tString + String.format("%.2f", tList.get(i))
						+ "\n\n\n";
				rString = rString + rList.get(i) + "\n\n\n";

				qString = qString + qList.get(i) + "\n\n\n";
				if (rList.get(i).equals("Correct")) {
					correctAnswers++;
				}
				totalTime = totalTime + tList.get(i);

			} else {
				qString = qString + qList.get(i) + "\n";
				rString = rString + rList.get(i) + "\n";
				if (rList.get(i).equals("Correct")) {
					correctAnswers++;
				}
				tString = tString + String.format("%.2f", tList.get(i)) + "\n";
				totalTime = totalTime + tList.get(i);
			}
		}
		gQuestions.setText(qString);
		gResults.setText(rString);
		gTimes.setText(tString);
		summary.setText(GameSelection.getSelection()
				+ " "
				+ Game.getSet()
				+ " , you answered "
				+ correctAnswers
				+ "/"
				+ qList.size()
				+ " correct. Your game lasted "
				+ Game.getTotalTime()
				+ ". For more information visit the score section of the app and find your score. Scroll down to " +
				"enter your name");

		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(GameSummary.this, FortMaths.class);
				Game.resetCurrent();
				startActivity(i);
				finish();
			}

		});

	}
	@Override
	public void onBackPressed(){
		Intent i = new Intent(GameSummary.this,FortMaths.class);
		startActivity(i);
		finish();
	}

}
