package com.turbo.whack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class SettingsActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.activity_init(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		SimpleDataStore datastore = new SimpleDataStore(ActivityHelper.WH_APP_NAME);	
		Switch sounds = (Switch) findViewById(R.id.game_sounds);
		Switch vibrations = (Switch) findViewById(R.id.vibrations);
		Button resetHighScores = (Button) findViewById(R.id.reset_high_scores);
		Button resetEverything = (Button) findViewById(R.id.reset_everything);
		
		// Populate default view states
		sounds.setChecked(Constants.WH_SET_SOUNDS == 1);
		vibrations.setChecked(Constants.WH_SET_VIBRATE == 1);
		
		OnClickListener clickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				switch(id) {
				case R.id.reset_everything:
					reset_highscores();
					reset_settings();
					ActivityHelper.show_toast("The application has been reset to default state.");
					break;
				case R.id.reset_high_scores: 
					reset_highscores();
					ActivityHelper.show_toast("The highscores have been reset.");
					break;
				default:
					break;
				}
			}
		};
		
		OnCheckedChangeListener checkedListener = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				int id = arg0.getId();				
				switch(id) {
				case R.id.game_sounds:
					Constants.WH_SET_SOUNDS = arg1 ? 1 : 0;
					break;
				case R.id.vibrations:
					Constants.WH_SET_VIBRATE = arg1 ? 1 : 0;
					break;
				default:
					break;
				}
			}
		};
		
		resetHighScores.setOnClickListener(clickListener);
		resetEverything.setOnClickListener(clickListener);
		sounds.setOnCheckedChangeListener(checkedListener);
		vibrations.setOnCheckedChangeListener(checkedListener);
		
		//HF: I don't like this way but its quick
		// Save settings to memory for retrieval later
		datastore.store_int_value(Constants.WH_SET_DATA_NAME + "_sounds", Constants.WH_SET_SOUNDS);
		datastore.store_int_value(Constants.WH_SET_DATA_NAME + "_vibrate", Constants.WH_SET_VIBRATE);
	}
	
	/**
	 * This function resets all the highscores stored in persistent memory.
	 * Returns 0 on success, less than zero otherwise.
	 * @return An integer that represents success
	 */
	private int reset_highscores() {
		SimpleDataStore data = new SimpleDataStore(ActivityHelper.WH_APP_NAME);
		if (data.remove_key(Constants.WH_HS_DATA_NAME)) {
			return 0;
		}
		return -1;
	}
	
	/**
	 * This function resets all the highscores stored in persistent memory.
	 * Returns 0 on success, less than zero otherwise.
	 * @return An integer that represents success
	 */
	private int reset_settings() {
		SimpleDataStore data = new SimpleDataStore(ActivityHelper.WH_APP_NAME);
		if(data.remove_key(Constants.WH_SET_DATA_NAME + "_sounds") && data.remove_key(Constants.WH_SET_DATA_NAME + "_vibrate")) {
			return 0;
		}
		return -1;
	}
}
