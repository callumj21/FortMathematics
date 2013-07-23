package com.example.fortmathematics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FortMaths extends Activity {
	
	private Button enterFort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fort_maths);
		
		enterFort = (Button) findViewById(R.id.entry);
		enterFort.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(FortMaths.this,Game.class);
				startActivity(i);
				
				
			}
			
		});
	}



}
