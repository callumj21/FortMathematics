package com.example.fortmathematics;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
	private Button clear;
	private Button dec;
	private GameDbAdapter mDbHelper;
	private Long mRowId;
	private TextView question;
	private static String set = "";
	private int dotCount = 0;

	private static ArrayList<String> questionsList = new ArrayList<String>();
	private ArrayList<String> answersList = new ArrayList<String>();
	private static ArrayList<String> correct = new ArrayList<String>();
	private static ArrayList<Float> timeList = new ArrayList<Float>();

	private static int current = 0;
	private Handler customHandler = new Handler();
	
	private TextView timer;
	private TextView scoreText;
	
	private long timeInMilliseconds = 0L;
	private long updatedTime = 0L;
	private long startTime = 0L;
	private long timeSwapBuff = 0L;	
	private int score = 0;
	
	private float questionTime = 0;
	private float questionFinish = 0;

	private String answer = "";
	private static String finalTime = "";
	private String usersAnswers = "";


	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mDbHelper = new GameDbAdapter(this);
		mDbHelper.open();

		setContentView(R.layout.game_screen);
		question = (TextView) findViewById(R.id.game_question);
		timer = (TextView) findViewById(R.id.time);
		scoreText = (TextView) findViewById(R.id.points);

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

		dec = (Button) findViewById(R.id.dec);
		dec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				decFunction();
			}

		});

		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearFunction();
			}

		});

		mRowId = null;
		Bundle extras = getIntent().getExtras();
		mRowId = (bundle == null) ? null : (Long) bundle
				.getSerializable(GameDbAdapter.KEY_ROWID);
		if (extras != null) {
			mRowId = extras.getLong(GameDbAdapter.KEY_ROWID);
		}

		prepareGame();

		// Declare the timer
		startTime = SystemClock.uptimeMillis();
		customHandler.postDelayed(updateTimerThread, 0);

	}

	private Runnable updateTimerThread = new Runnable() {

		public void run() {
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			updatedTime = timeSwapBuff + timeInMilliseconds;
			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			secs = secs % 60;
			int milliseconds = (int) (updatedTime % 1000);
			timer.setText("" + mins + ":" + String.format("%02d", secs) + ":"
					+ String.format("%03d", milliseconds));
			customHandler.postDelayed(this, 0);

		}

	};

	@SuppressWarnings("deprecation")
	private void prepareGame() {
		answersList = new ArrayList<String>();
		questionsList = new ArrayList<String>();
		correct = new ArrayList<String>();
		String questions = "";
		String answers = "";
		set = "";
		timeList = new ArrayList<Float>();
		if (mRowId != null) {
			Cursor gameCursor = null;
		
				gameCursor = mDbHelper.fetchGame(mRowId);
				startManagingCursor(gameCursor);
				set = gameCursor.getString(gameCursor
						.getColumnIndexOrThrow(GameDbAdapter.KEY_SET));

				questions = gameCursor.getString(gameCursor
						.getColumnIndexOrThrow(GameDbAdapter.KEY_QUESTIONS));
				answers = gameCursor.getString(gameCursor
						.getColumnIndexOrThrow(GameDbAdapter.KEY_ANSWERS));
			

			}

			Log.d("hello", questions);
			String questionsArray[] = questions.split(",");

			for (int i = 0; i < questionsArray.length; i++) {
				System.out.println(questionsArray[i]);
				questionsList.add(questionsArray[i]);
			}

			question.setText(questionsList.get(0));

			String answersArray[] = answers.split(",");

			for (int i = 0; i < answersArray.length; i++) {
				System.out.println(answersArray[i]);
				answersList.add(answersArray[i]);
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

	private void decFunction() {
		answer = answer + ".";
		answerBox.setText(answer);
		dotCount++;

	}

	private void clearFunction() {
		answer = "";
		answerBox.setText(answer);

	}

	private void enterFunction() {
		float actualAnswer;
		float userAnswer;
		if (answer.length() == 0 || dotCount > 1) {
			actualAnswer = Float.parseFloat(answersList.get(current));
			userAnswer = -1;
		} else {
			actualAnswer = Float.parseFloat(answersList.get(current));
			userAnswer = Float.parseFloat(answer);
			System.out.println(userAnswer);

		}

			questionTime = ((timeSwapBuff + timeInMilliseconds) - questionFinish) / 1000L;
			questionFinish = (float) (timeSwapBuff + timeInMilliseconds);
			System.out.println("Time was: " + questionTime + " , was finished at: " + questionFinish);
			timeList.add(questionTime);


			questionTime = questionTime * 1000;


		current = current + 1;

		if (current == questionsList.size()) {
			finishGame(actualAnswer, userAnswer);

		} else {
			question.setText(questionsList.get(current));
			answer = "";
			answerBox.setText(answer);
			if (actualAnswer == userAnswer) {
				correct.add("Correct");

				addscore();
			} else {
				correct.add("Wrong");
			}

			dotCount = 0;
			// Toast toast = Toast.makeText(getApplicationContext(),
			// "You have entered: " + answer, Toast.LENGTH_SHORT);
			// toast.show();

		}
	}

	private void finishGame(float actualAnswer, float userAnswer) {
		enter.setClickable(false);
		customHandler.removeCallbacks(updateTimerThread);
		updatedTime = timeSwapBuff + timeInMilliseconds;
		int secs = (int) (updatedTime / 1000);
		int mins = secs / 60;
		secs = secs % 60;
		int milliseconds = (int) (updatedTime % 1000);
		finalTime = ("" + mins + ":" + String.format("%02d", secs) + ":"
				+ String.format("%03d", milliseconds));


		if (actualAnswer == userAnswer) {
			correct.add("Correct");

			addscore();
		} else {
			correct.add("Wrong");
		}
		Intent i = new Intent(Game.this, GameSummary.class);
		startActivity(i);
		finish();

	}

	
	private void addscore() {
		score = score + 1;
		scoreText.setText(Integer.toString(score));

	}
	
	public static String getTotalTime(){
		return finalTime;
		
	}

	public static ArrayList<String> getQuestions() {
		return questionsList;
	}

	public static ArrayList<String> getResults() {
		return correct;
	}

	public static String getSet() {
		return set;
	}

	public static ArrayList<Float> getTimes() {
		return timeList;
	}

	public static void resetCurrent() {
		current = 0;

	}

	@Override
	public void onBackPressed() {
		timeSwapBuff += timeInMilliseconds;
		customHandler.removeCallbacks(updateTimerThread);

		AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
		builder.setTitle("Pause Menu").setItems(R.array.pause_array,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							startTime = SystemClock.uptimeMillis();
							customHandler.postDelayed(updateTimerThread, 0);

						} else if (which == 1) {
							Game.resetCurrent();
							
								Intent i = new Intent(Game.this,FortMaths.class);
								startActivity(i);
							finish();
						}
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}
	
}
