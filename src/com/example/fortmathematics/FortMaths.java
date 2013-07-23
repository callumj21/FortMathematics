package com.example.fortmathematics;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FortMaths extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fort_maths);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fort_maths, menu);
		return true;
	}

}
