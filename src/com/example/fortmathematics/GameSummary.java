package com.example.fortmathematics;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GameSummary extends Activity {
	private TextView gQuestions;
	private TextView gResults;
	private TextView gTimes;
	ArrayList<String> qList = new ArrayList<String>();
	ArrayList<String> rList = new ArrayList<String>();
	ArrayList<Float> tList = new ArrayList<Float>();

	private String qString = "";
	private String rString = "";
	private String tString = "";


	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.summary);
		
		gQuestions = (TextView) findViewById(R.id.quest);
		gResults = (TextView) findViewById(R.id.question_result);
		gTimes = (TextView) findViewById(R.id.question_times);

		qList = Game.getQuestions();
		rList = Game.getResults();
		tList = Game.getTimes();

		for(int i = 0; i < qList.size(); i++){
			qString = qString + qList.get(i) + "\n\n\n";
			rString = rString + rList.get(i) + "\n\n\n";
			tString = tString +  String.format("%.2f", tList.get(i)) + "\n\n\n";
		}
		gQuestions.setText(qString);
		gResults.setText(rString);
		gTimes.setText(tString);

	}

}
