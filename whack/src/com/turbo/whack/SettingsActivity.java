package com.turbo.whack;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.activity_init(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
	}
}
