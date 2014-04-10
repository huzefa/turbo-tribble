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
		Button settings = (Button) findViewById(R.id.settings_button);
		
		OnClickListener clickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				Intent intent;
				switch(id) {
				case R.id.start_button:
					intent = new Intent(FullscreenActivity.this, GamePlayActivity.class);
					FullscreenActivity.this.startActivity(intent);
					break;
				case R.id.high_score_button:
					intent = new Intent(FullscreenActivity.this, HighScoreActivity.class);
					FullscreenActivity.this.startActivity(intent);
					break;
				case R.id.settings_button:
					intent = new Intent(FullscreenActivity.this, SettingsActivity.class);
					FullscreenActivity.this.startActivity(intent);
					break;
				default:
					break;
				}
			}
		};
		
		start_button.setOnClickListener(clickListener);
		high_score.setOnClickListener(clickListener);
		settings.setOnClickListener(clickListener);
	}
}
