package com.turbo.whack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// Full screen activity
public class FullscreenActivity extends Activity {
	
// HF: I don't even know if we need these definitions...? I've left this just in case.
//	private static final boolean AUTO_HIDE = true;				// Hide system UI
//	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;		// Time in ms before the system UI is hidden
//	private static final boolean TOGGLE_ON_CLICK = true;		// Toggle system UI on interaction
//	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;	// Hide nav bar in api 19+

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
