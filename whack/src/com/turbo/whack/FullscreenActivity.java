package com.turbo.whack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.ImageButton;

// Full screen activity
public class FullscreenActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.app_init(this);
		ActivityHelper.activity_init(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		
		Button start_button = (Button) findViewById(R.id.start_button);
		Button test = (Button) findViewById(R.id.test_button);
		Button high_score = (Button) findViewById(R.id.high_score_button);
		start_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FullscreenActivity.this, GamePlay.class);
				FullscreenActivity.this.startActivity(intent);
			}	
		});	
		high_score.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FullscreenActivity.this, HighScoreActivity.class);
				FullscreenActivity.this.startActivity(intent);				
			}
		});
	}
}
