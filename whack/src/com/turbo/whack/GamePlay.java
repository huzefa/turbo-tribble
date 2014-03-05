package com.turbo.whack;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class GamePlay extends Activity {
	private static final int WH_BUTTON_MAP = 74;
	
	private Timer check_timer = new Timer();
	private Timer countdown_timer = new Timer();
	private boolean button_pressed = false;
	private boolean game_over = false;
	private long level = 100;
	private int score = 0;
	private long time_remaining = 0;
	private int button_in_focus = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.activity_init(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		final Button button[] = get_button_handles();
		final TextView time_view = (TextView) findViewById(R.id.time_remaining);
		
		// disable and hide all buttons
		for(int i = 10; i < WH_BUTTON_MAP; i+=10) {
			hide_button(button[i+1]);
			hide_button(button[i+2]);
			hide_button(button[i+3]);
		}
		
		// Poll for any button presses
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(game_over) {
					check_timer.cancel();
					countdown_timer.cancel(); 
					
					Log.i(ActivityHelper.WH_LOG_INFO, "Time Expired! Game over.");
					Log.i(ActivityHelper.WH_LOG_INFO, "Level: " + level);
					Log.i(ActivityHelper.WH_LOG_INFO, "Time given: " + (level * ActivityHelper.WH_TIMER_MULTIPLIER));
					// Show score screen
				}
				if(button_pressed) {
					button_pressed = false;
					score++;
					
					// Cancel the timeout timer
					countdown_timer.cancel();
					TimerTask t = new TimerTask() {
						@Override
						public void run() {
							game_over = true;
						}
					};
					countdown_timer = new Timer();
					countdown_timer.schedule(t, level * ActivityHelper.WH_TIMER_MULTIPLIER);
					time_remaining = level * ActivityHelper.WH_TIMER_MULTIPLIER;
					
					// Make it harder
					if(score % 3 == 0 && score > 0) {
						level /= ActivityHelper.WH_HARDNESS_FACTOR;
					}
					
					// Spawn a new random button
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							show_random_button(button);
						}
					});
				}
				
				// update textview
				runOnUiThread(new Runnable() {
					@Override
					public void run() { 
						time_remaining = time_remaining - 50;
						if(time_remaining < 0) {
							time_remaining = 0;
						}
						time_view.setText(Long.toString(time_remaining));
						if(game_over) {
							button[button_in_focus].setEnabled(false);
						}
					}
				});
			}
		};
		check_timer.scheduleAtFixedRate(task, 0, ActivityHelper.WH_TIMER_CHECK_RATE);
		
		TimerTask t = new TimerTask() {
			@Override
			public void run() {
				game_over = true;
			}
		};
		countdown_timer.schedule(t, level * ActivityHelper.WH_TIMER_MULTIPLIER);
		
		// Show the button the first time
		time_remaining = level * ActivityHelper.WH_TIMER_MULTIPLIER;
		show_random_button(button);
	}
	
	/**
	 * This method will display any random button
	 * @param button
	 * @return
	 */
	private int show_random_button(Button[] button) {
		button_in_focus = ActivityHelper.get_random_number();
		show_button(button[button_in_focus]);
		return 0;
	}
	
	/**
	 * Callback for button click when you whack the mole.
	 * @param v View name
	 */
	public void game_button_clicked(View v) {
		button_pressed = true;
		final Button b = (Button) findViewById(v.getId());
		Log.i(ActivityHelper.WH_LOG_INFO, "SCORE: " + score);
		
		Animation vanish = AnimationUtils.loadAnimation(this, R.anim.vanish);
		vanish.setAnimationListener(new Animation.AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) {}			
			@Override
			public void onAnimationRepeat(Animation animation) {}			
			@Override
			public void onAnimationEnd(Animation animation) {			
				hide_button(b);
			}
		});
		b.startAnimation(vanish);
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
	private int hide_button(final Button button) {
		button.setEnabled(false);
		button.setVisibility(View.INVISIBLE);
		return 0;
	}
	
	/**
	 * Clean up before exiting
	 */
	@Override
	public void onBackPressed() {
		game_over = true;
		check_timer.cancel();
		countdown_timer.cancel();
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
