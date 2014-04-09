package com.turbo.whack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// Full screen activity
public class FullscreenActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.app_init(this);
		ActivityHelper.activity_init(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		
		Button start_button = (Button) findViewById(R.id.start_button);
		Button high_score = (Button) findViewById(R.id.high_score_button);
		
		start_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FullscreenActivity.this, GamePlayActivity.class);
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
