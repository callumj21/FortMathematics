package com.example.fortmathematics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddGame extends Activity{

	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;

	private static String difficulty;
	private static int numQuestions;
	private static String name;
	private static String gameType;
	
	private EditText gameName;

	private Button contB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_game);

	
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		
		gameName = (EditText) findViewById(R.id.game_name);
		gameName.setText("New Set");

		
		contB = (Button) findViewById(R.id.input_button);
		contB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
					name = gameName.getText().toString();
				
				Intent i = new Intent(AddGame.this, InputQuestions.class);
				startActivity(i);
			}

		});
		
		
		
		
		ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"Addition", "Subtraction", "Multiplication", "Division", "Mixup"});
		adapterType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		ArrayAdapter<String> adapterType2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"Easy", "Medium", "Hard"});
		adapterType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<String> adapterType3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"10", "15", "20"});
		adapterType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapterType);
		spinner2.setAdapter(adapterType2);
		spinner3.setAdapter(adapterType3);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				gameType = spinner1.getSelectedItem().toString();


			}
			
			
			

			
		
		

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			difficulty	= spinner2.getSelectedItem().toString();


			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			numQuestions	= Integer.parseInt(spinner3.getSelectedItem().toString());


			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
	}

	
	
	public static String getType() {
		return gameType;
	}
	
	public static String getDifficulity() {
		return difficulty;
	}
	
	public static String getName() {
		return name;
	}
	
	public static int getNumber() {
		return numQuestions;
	}

}
