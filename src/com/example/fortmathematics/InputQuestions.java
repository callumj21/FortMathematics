package com.example.fortmathematics;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputQuestions extends Activity {

	private static String difficulty = "";
	private static String name = "";
	private static String type = "";
	private static String questions = "";
	private static String answers = "";
	private static int num = 0;
	private static int current = 1;
	private TextView question;
	private TextView symbol;
	private EditText one;
	private EditText two;
	private Button input;
	private GameDbAdapter dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions);

		dbHelper = new GameDbAdapter(this);
		dbHelper.open();

		difficulty = AddGame.getDifficulity();
		name = AddGame.getName();
		type = AddGame.getType();
		num = AddGame.getNumber();
		System.out.println(difficulty + " " + name + " " + type + " " + num);

		question = (TextView) findViewById(R.id.question);
		symbol = (TextView) findViewById(R.id.sym);

		one = (EditText) findViewById(R.id.box1);
		two = (EditText) findViewById(R.id.box2);
		one.setGravity(Gravity.CENTER);
		two.setGravity(Gravity.CENTER);

		

		input = (Button) findViewById(R.id.input);

		question.setText("Fill in boxes for Question : " + current);
		if (type.equals("Addition")) {
			symbol.setText("+");
		} else if (type.equals("Subtraction")) {
			symbol.setText("-");
		} else if (type.equals("Multiplication")) {
			symbol.setText("*");
		} else if (type.equals("Division")) {
			symbol.setText("/");
		} else if (type.equals("Mixup")) {
			Random rand = new Random();
			int x = rand.nextInt(3);
			if (x == 0) {
				symbol.setText("+");
			} else if (x == 1) {
				symbol.setText("-");
			} else if (x == 2) {
				symbol.setText("*");
			} else if (x == 3) {
				symbol.setText("/");
			}
		}

		input.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (one.getText().toString().equals("")
						|| two.getText().toString().equals("")) {
					

				} else {
					float answer;
					if (symbol.getText().toString().equals("+")) {
						questions = questions + one.getText().toString()
								+ " + " + two.getText().toString() + " , ";
						answer = Float.parseFloat(one.getText().toString())
								+ Float.parseFloat(two.getText().toString());
						answers = answers + answer + ",";
						one.setText("");
						two.setText("");
						current = current + 1;
						question.setText("Fill in boxes for Question : "
								+ current);
					} else if (symbol.getText().equals("-")) {
						questions = questions + one.getText().toString()
								+ " - " + two.getText().toString() + " , ";
						answer = Float.parseFloat(one.getText().toString())
								- Float.parseFloat(two.getText().toString());
						answers = answers + answer + ",";
						one.setText("");
						two.setText("");
						System.out.println(questions);
						System.out.println(answers);
						current = current + 1;
						question.setText("Fill in boxes for Question : "
								+ current);
					} else if (symbol.getText().equals("*")) {
						questions = questions + one.getText().toString()
								+ " * " + two.getText().toString() + " , ";
						answer = Float.parseFloat(one.getText().toString())
								* Float.parseFloat(two.getText().toString());
						answers = answers + answer + ",";
						one.setText("");
						two.setText("");
						System.out.println(questions);
						System.out.println(answers);
						current = current + 1;
						question.setText("Fill in boxes for Question : "
								+ current);
					} else if (symbol.getText().equals("/")) {
						int zero = Integer.parseInt(two.getText().toString());
						if(zero == 0){
						Toast warning =	Toast.makeText(getApplicationContext(), "Sorry Can't Divide by Zero!",
									   Toast.LENGTH_SHORT);
						warning.show();
						return;
						} else {
						questions = questions + one.getText().toString()
								+ " / " + two.getText().toString() + " , ";
						answer = Float.parseFloat(one.getText().toString())
								/ Float.parseFloat(two.getText().toString());
						answers = answers + answer + ",";
						one.setText("");
						two.setText("");
						System.out.println(questions);
						System.out.println(answers);
						current = current + 1;
						question.setText("Fill in boxes for Question : "
								+ current);
						}
					}
				}

				if (type.equals("Mixup")) {
					Random rand = new Random();
					int x = rand.nextInt(4);
					if (x == 0) {
						symbol.setText("+");
					} else if (x == 1) {
						symbol.setText("-");
					} else if (x == 2) {
						symbol.setText("*");
					} else if (x == 3) {
						symbol.setText("/");
					}
				}

				if (current > num) {
					populateDatabase();
				}

			}

		});

	}

	private void populateDatabase() {
		System.out.println(type);
		System.out.println(difficulty);
		System.out.println(name);
		questions = questions.substring(0, questions.length() - 2);

		System.out.println(questions);
		System.out.println(answers);
		dbHelper.createGame(type, name, difficulty, questions, answers);
		current = 1;
		questions = "";
		answers = "";
		
		final SharedPreferences mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor editor = mSharedPreference.edit();
		int value = mSharedPreference.getInt("key", 1);
		editor.putInt("key", value + 1);
		editor.commit();
        AddGame.setNumber(value + 1);

	
		
		finish();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				InputQuestions.this);
		builder.setCancelable(false);
		builder.setTitle("Options Menu").setItems(R.array.stop_array,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							// Return to adding game!
						} else if (which == 1) {
							current = 1;
							questions = "";
							answers = "";
							finish();
						}
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();

	}

}
