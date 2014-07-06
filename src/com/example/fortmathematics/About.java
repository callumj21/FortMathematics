package com.example.fortmathematics;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends Activity {
	
	TextView about;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		about = (TextView) findViewById(R.id.about_text);
		about.setText("This game was developed by Callum Jamieson!" + "\n\n" + "Development took place during July 2013, but never completed due to starting out at a new job. The game was then revived in July 2014 to the product you see today." + "\n\n" + "I hope you enjoy playing, the purpose is very similar to other brain working maths games with the added ability to provide your own questions to test yourself anyway you want." + "\n\n" + "Thanks for playing, Callum");
	
}
}
