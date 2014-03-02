package com.turbo.whack;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class GamePlay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.activity_init(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		
	}
	
	/**
	 * Callback for button click when you whack the mole.
	 * @param v View name
	 */
	public void game_button_clicked(View v) {
		
	}
}
