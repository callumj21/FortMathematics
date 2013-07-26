package com.example.fortmathematics;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameSummary extends Activity {
	private TextView gQuestions;
	private TextView gResults;
	private TextView gTimes;
	private Button save;
	private TextView summary;
	private EditText nameBox;
	ArrayList<String> qList = new ArrayList<String>();
	ArrayList<String> rList = new ArrayList<String>();
	ArrayList<Float> tList = new ArrayList<Float>();
	private float totalTime = 0;
	private int correctAnswers = 0;
	private String score = "";

	private String qString = "";
	private String rString = "";
	private String tString = "";
	
	private ScoreDbAdapter mScoreDbHelper;
	private Long mRowId;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		
		
		setContentView(R.layout.summary);
		
		mScoreDbHelper = new ScoreDbAdapter(this);
		mScoreDbHelper.open();

		summary = (TextView) findViewById(R.id.message);

		gQuestions = (TextView) findViewById(R.id.quest);
		gResults = (TextView) findViewById(R.id.question_result);
		gTimes = (TextView) findViewById(R.id.question_times);

		nameBox = (EditText) findViewById(R.id.name_entry);

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
			
			score = correctAnswers + "/" + qList.size();

		}
		gQuestions.setText(qString);
		gResults.setText(rString);
		gTimes.setText(tString);
		summary.setText(GameSelection.getSelection()
				+ " "
				+ Game.getSet()
				+ " , you answered "
				+ score 
				+ " correct. Your game lasted "
				+ Game.getTotalTime()
				+ ". For more information visit the score section of the app and find your score. Scroll down to "
				+ "enter your name");

		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				saveState();
				

				Intent i = new Intent(GameSummary.this, FortMaths.class);
				Game.resetCurrent();
				startActivity(i);
				finish();
			}

		});

	}
	
	private void saveState(){
		String userName = "";
		String uAnswersStr = "";
		String answersStr = "";
		if (!nameBox.getText().toString().equals("")) {
			userName = nameBox.getText().toString();
		} else {
			System.out.println("here");
			userName = "abc";
		}
		ArrayList<String> uAnswers = Game.getUsersAnswers();
		for(int i = 0; i < uAnswers.size(); i++ ){
			if (i != uAnswers.size() - 1) {
			uAnswersStr = uAnswersStr + uAnswers.get(i) + "\n\n\n";
			} else { 
				uAnswersStr = uAnswersStr + uAnswers.get(i) + "\n";

			}
		}
		
		ArrayList<String> answers = Game.getAnswers();
		for(int i = 0; i < answers.size(); i++ ){
			if (i != answers.size() - 1) {
			answersStr = answersStr + answers.get(i) + "\n\n\n";
			} else { 
				answersStr = answersStr + answers.get(i) + "\n";

			}
		}
		
		if (mRowId == null) {
			long id = mScoreDbHelper.createScore(userName, score, GameSelection.getSelection(), Game.getSet(),
					qString,  answersStr, tString, Game.getTotalTime(), rString, uAnswersStr);
			
			Log.d("Score", "Score " + id + " created, values are");
			Log.d("Score", "Name = " + userName);
			Log.d("Score", "Score = " + score);
			Log.d("Score", "Type = " + GameSelection.getSelection());
			Log.d("Score", "Set = " + Game.getSet());
			Log.d("Score", "Questions = " + qString);
			Log.d("Score", "Answers = " + answersStr);
			Log.d("Random", "Times = " + tString);
			Log.d("Score", "Total = " + Game.getTotalTime());
			Log.d("Score", "Results = " + rString);
			Log.d("Score", "UsersAnswers = " + uAnswersStr);

			
			if (id > 0) {
				mRowId = id;

			}
		}
		
	
		
		
	}

	@Override
	public void onBackPressed() {
		return;
//		Intent i = new Intent(GameSummary.this, FortMaths.class);
//		startActivity(i);
//		finish();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		if (mScoreDbHelper != null) {
			Log.d("Score", "Databse Adapter closed");
			mScoreDbHelper.close();
		}
	}

}
