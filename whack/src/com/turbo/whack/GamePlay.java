package com.turbo.whack;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GamePlay extends Activity {
	private static final int WH_BUTTON_MAP = 74;
	
	private Timer check_timer = new Timer();
	private Timer button_show_timer;
	private boolean button_pressed = false;
	private boolean exit = false;
	private long level = 100;
	private int score = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.activity_init(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		Button button[] = get_button_handles();
		
		// disable and hide all buttons
		for(int i = 10; i < 74; i+=10) {
			hide_button(button[i+1]);
			hide_button(button[i+2]);
			hide_button(button[i+3]);
		}
				
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(button_pressed) {
					button_pressed = false;
					exit = true;
					score++;
					// Cancel the timeout timer
					if(score % 3 == 0 && score > 0) {
						level /= ActivityHelper.WH_HARDNESS_FACTOR;
					}
					return;
				}
			}
		};
		check_timer.scheduleAtFixedRate(task, 0, ActivityHelper.WH_TIMER_CHECK_RATE);
		
		start_game(button);
	}
	
	private void start_game(Button[] b) {
		final Button[] button = b;
		button_show_timer = new Timer();
		
		if(level * ActivityHelper.WH_TIMER_MULTIPLIER < ActivityHelper.WH_TIMER_CHECK_RATE) {
			// The player managed to beat the game.
		}
		
		TimerTask button_show_task = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						show_random_button(button);
					}
				});
			}
		};
		button_show_timer.schedule(button_show_task, level * ActivityHelper.WH_TIMER_MULTIPLIER);
		
	}
	
	/**
	 * This method will display any random button
	 * @param button
	 * @return
	 */
	private int show_random_button(Button[] button) {
		int rand = ActivityHelper.get_random_number();
		show_button(button[rand]);
		return 0;
	}
	
	/**
	 * Callback for button click when you whack the mole.
	 * @param v View name
	 */
	public void game_button_clicked(View v) {
		button_pressed = true;
		Button b = (Button) findViewById(v.getId());
		hide_button(b);
	}
	
	/**
	 * Make the button pressable and visible.
	 * @param button The button in question
	 * @return An integer showing success
	 */
	private int show_button(Button button) {
		button.setEnabled(true);
		button.setVisibility(View.VISIBLE);
		return 0;
	}
	
	/**
	 * Hide the button completely.
	 * @param button
	 * @return An integer showing success
	 */
	private int hide_button(Button button) {
		button.setEnabled(false);
		button.setVisibility(View.INVISIBLE);
		return 0;
	}
	
	/**
	 * Clean up before exiting
	 */
	@Override
	public void onBackPressed() {
		exit = true;
		check_timer.cancel();
		this.finish();
	}
	
	/**
	 * Set all button ids so that it is easy to reference by index.
	 * @return An array of type Button with all the ids referenced
	 */
	private Button[] get_button_handles() {
		Button button_array[] = new Button[WH_BUTTON_MAP];
		for(int i = 0; i < 74; i++) {
			button_array[i] = null;
		}
		
		button_array[11] = (Button) findViewById(R.id.Button11);
		button_array[12] = (Button) findViewById(R.id.Button12);
		button_array[13] = (Button) findViewById(R.id.Button13);
		button_array[21] = (Button) findViewById(R.id.Button21);
		button_array[22] = (Button) findViewById(R.id.Button22);
		button_array[23] = (Button) findViewById(R.id.Button23);
		button_array[31] = (Button) findViewById(R.id.Button31);
		button_array[32] = (Button) findViewById(R.id.Button32);
		button_array[33] = (Button) findViewById(R.id.Button33);
		button_array[41] = (Button) findViewById(R.id.Button41);
		button_array[42] = (Button) findViewById(R.id.Button42);
		button_array[43] = (Button) findViewById(R.id.Button43);
		button_array[51] = (Button) findViewById(R.id.Button51);
		button_array[52] = (Button) findViewById(R.id.Button52);
		button_array[53] = (Button) findViewById(R.id.Button53);
		button_array[61] = (Button) findViewById(R.id.Button61);
		button_array[62] = (Button) findViewById(R.id.Button62);
		button_array[63] = (Button) findViewById(R.id.Button63);
		button_array[71] = (Button) findViewById(R.id.Button71);
		button_array[72] = (Button) findViewById(R.id.Button72);
		button_array[73] = (Button) findViewById(R.id.Button73);
		
		return button_array;
	}
}
