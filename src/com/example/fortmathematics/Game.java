package com.example.fortmathematics;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity {

	private EditText answerBox;
	private Button one;
	private Button two;
	private Button three;
	private Button four;
	private Button five;
	private Button six;
	private Button seven;
	private Button eight;
	private Button nine;
	private Button zero;
	private Button del;
	private Button enter;
	private GameDbAdapter mDbHelper;
	private Long mRowId;
	private TextView question;
	private ArrayList<String> questionsList = new ArrayList<String>();
	private ArrayList<String> answersList = new ArrayList<String>();
	private static int current = 0;

	
	

	String answer = "";

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mDbHelper = new GameDbAdapter(this);
		mDbHelper.open();
		
		setContentView(R.layout.game_screen);
		question = (TextView) findViewById(R.id.game_question);

		answerBox = (EditText) findViewById(R.id.answer);
		answerBox.setKeyListener(null);
		answerBox.setFocusable(false);

		zero = (Button) findViewById(R.id.zero);
		zero.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				zeroFunction();

			}

		});

		one = (Button) findViewById(R.id.one);
		one.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				oneFunction();

			}

		});

		two = (Button) findViewById(R.id.two);
		two.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				twoFunction();
			}

		});
		
		
		three = (Button) findViewById(R.id.three);
		three.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				threeFunction();

			}

		});

		four = (Button) findViewById(R.id.four);
		four.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fourFunction();
			}

		});
		
		five = (Button) findViewById(R.id.five);
		five.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fiveFunction();

			}

		});

		six = (Button) findViewById(R.id.six);
		six.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sixFunction();
			}

		});
		
		seven = (Button) findViewById(R.id.seven);
		seven.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sevenFunction();

			}

		});

		eight = (Button) findViewById(R.id.eight);
		eight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				eightFunction();
			}

		});
		
		nine = (Button) findViewById(R.id.nine);
		nine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nineFunction();

			}

		});

		
		
		del = (Button) findViewById(R.id.del);
		del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				delFunction();
			}

		});
		
		enter = (Button) findViewById(R.id.enter);
		enter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enterFunction();
			}

		});
		
		mRowId = null;
		Bundle extras = getIntent().getExtras();
		mRowId = (bundle == null) ? null : (Long) bundle
				.getSerializable(GameDbAdapter.KEY_A_ROWID);
		if (extras != null) {
			mRowId = extras.getLong(GameDbAdapter.KEY_A_ROWID);
		}

		prepareAdditionGame();
		
		

	}

	@SuppressWarnings("deprecation")
	private void prepareAdditionGame() {
		if (mRowId != null) {
			Cursor add = mDbHelper.fetchAddition(mRowId);
			startManagingCursor(add);
			
			String questions = add.getString(add
					.getColumnIndexOrThrow(GameDbAdapter.KEY_A_QUESTIONS));
			String answers = add.getString(add
					.getColumnIndexOrThrow(GameDbAdapter.KEY_A_ANSWERS));
			Log.d("hello",questions);
			String questionsArray[] = questions.split(",");
			
			for(int i = 0; i < questionsArray.length;i++){
				System.out.println(questionsArray[i]);
				questionsList.add(questionsArray[i]);
			}
			
			question.setText(questionsList.get(0));
			
			String answersArray[] = answers.split(",");
			
			for(int i = 0; i < answersArray.length;i++){
				System.out.println(answersArray[i]);
				answersList.add(answersArray[i]);
			}
			
			
		
			
		}
		
	}

	private void zeroFunction() {
		answer = answer + "0";
		answerBox.setText(answer);

	}

	private void oneFunction() {
		answer = answer + "1";
		answerBox.setText(answer);

	}

	private void twoFunction() {
		answer = answer + "2";
		answerBox.setText(answer);

	}

	private void threeFunction() {
		answer = answer + "3";
		answerBox.setText(answer);

	}

	private void fourFunction() {
		answer = answer + "4";
		answerBox.setText(answer);

	}

	private void fiveFunction() {
		answer = answer + "5";
		answerBox.setText(answer);

	}

	private void sixFunction() {
		answer = answer + "6";
		answerBox.setText(answer);

	}

	private void sevenFunction() {
		answer = answer + "7";
		answerBox.setText(answer);

	}

	private void eightFunction() {
		answer = answer + "8";
		answerBox.setText(answer);

	}

	private void nineFunction() {
		answer = answer + "9";
		answerBox.setText(answer);

	}

	private void delFunction() {
		if (!answer.equals("")) {
			answer = answer.substring(0, answer.length() - 1);
			answerBox.setText(answer);
		}

	}

	private void enterFunction() {
		System.out.println(answersList.get(current));
		if(answer.equals(answersList.get(current))){
			current = current + 1;
			System.out.println(current);
			question.setText(questionsList.get(current));
			answer = "";
			answerBox.setText(answer);
		}
		Toast toast = Toast.makeText(getApplicationContext(), "You have entered: " + answer,
				Toast.LENGTH_SHORT);
		toast.show();

	}
}
