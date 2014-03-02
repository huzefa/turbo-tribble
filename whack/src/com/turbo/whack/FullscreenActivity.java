package com.turbo.whack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// Full screen activity
public class FullscreenActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.activity_init(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		
		Button start_button = (Button) findViewById(R.id.start_button);
		Button instr_button = (Button) findViewById(R.id.instructions_button);
		
		start_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO something with start button
				
			}	
		});
		
		instr_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO something with instr button
				
			}
		});
	}
}
