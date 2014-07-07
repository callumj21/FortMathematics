package com.example.fortmathematics;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class PlayerResults extends Activity{
	private TextView player;
	private TextView playerScore;
	private TextView correctQuestions;
	private TextView title;
	private TextView times;
	private TextView wrongQuestions;
	private TextView userAnswers;
	private TextView wrongTimes;
	
	private ScoreDbAdapter mScoreDbHelper;
	private Long mRowId;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		
		
		setContentView(R.layout.results_summary);
		
		mScoreDbHelper = new ScoreDbAdapter(this);
		mScoreDbHelper.open();

		player = (TextView) findViewById(R.id.player);

		playerScore = (TextView) findViewById(R.id.player_score);
		
		correctQuestions = (TextView) findViewById(R.id.questions);
		
		title = (TextView) findViewById(R.id.game);
		title.setGravity(Gravity.CENTER);
		
		times = (TextView) findViewById(R.id.times);
		
		wrongQuestions = (TextView) findViewById(R.id.w_questions);
		
		userAnswers = (TextView) findViewById(R.id.user_answers);
		
		wrongTimes = (TextView) findViewById(R.id.w_times);

		
		mRowId = null; 
        Bundle extras = getIntent().getExtras(); 
        mRowId = (bundle == null) ? null : (Long) bundle 
                .getSerializable(ScoreDbAdapter.KEY_ROWID); 
        if (extras != null) { 
            mRowId = extras.getLong(ScoreDbAdapter.KEY_ROWID); 
        } 
        
        populateFields(); 
		
		
	
	}
	
	
	private void populateFields() { 
        if (mRowId != null) { 
            Cursor score = mScoreDbHelper.fetchScore(mRowId); 
            startManagingCursor(score); 
            String cQuestions = "";
            String wQuestions = "";
            
            String cTimes = "";
            String wTimes = "";
            
            String user = "";
            
            String type = score.getString(score 
                    .getColumnIndexOrThrow(ScoreDbAdapter.KEY_TYPE));
            	
            String set = score.getString(score 
                    .getColumnIndexOrThrow(ScoreDbAdapter.KEY_SET));
            
            
            String questions = score.getString(score 
                    .getColumnIndexOrThrow(ScoreDbAdapter.KEY_QUESTIONS));
            String c [] = questions.split("\\n\n\n"); 
            
            String result = score.getString(score 
                    .getColumnIndexOrThrow(ScoreDbAdapter.KEY_RESULTS));
            
            String allTimes = score.getString(score 
                    .getColumnIndexOrThrow(ScoreDbAdapter.KEY_TIMES));
            String t [] = allTimes.split("\\n\n\n");
            
            String userA = score.getString(score 
                    .getColumnIndexOrThrow(ScoreDbAdapter.KEY_USER));
            String a [] = userA.split("\\n\n\n");
           
            String words[] = result.split("\\s+"); 
            for(int i = 0; i < words.length; i++){
            	if(words[i].equals("Correct")){
            		cQuestions = cQuestions + c[i] + "\n\n\n";
            		cTimes = cTimes + t[i] + "\n\n\n";	
            	} else { 
            		wQuestions = wQuestions + c[i] + "\n\n\n";
            		wTimes = wTimes + t[i] + "\n\n\n";
            		user = user + a[i] + "\n\n\n";
            	}
            }

  
            player.setText(score.getString(score 
                    .getColumnIndexOrThrow(ScoreDbAdapter.KEY_NAME))); 
            playerScore.setText(score.getString(score 
                    .getColumnIndexOrThrow(ScoreDbAdapter.KEY_SCORE))); 
            correctQuestions.setText(cQuestions);
            title.setText(set);
            times.setText(cTimes);
            wrongQuestions.setText(wQuestions);
            wrongTimes.setText(wTimes);
            userAnswers.setText(user);
            
         
            } 
        } 
	
	@Override
    protected void onDestroy() { 
        super.onDestroy(); 
        if (mScoreDbHelper != null) { 
            mScoreDbHelper.close(); 
        } 
    } 
  
    
}


