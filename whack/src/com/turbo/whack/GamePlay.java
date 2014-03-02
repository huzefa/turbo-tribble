package com.turbo.whack;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class GamePlay extends Activity {
	private static final int BUTTON_MAP = 74;
	
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
	}
	
	/**
	 * Callback for button click when you whack the mole.
	 * @param v View name
	 */
	public void game_button_clicked(View v) {
		
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
		button.setVisibility(View.VISIBLE);
		return 0;
	}
	
	/**
	 * Set all button ids so that it is easy to reference by index.
	 * @return An array of type Button with all the ids referenced
	 */
	private Button[] get_button_handles() {
		Button button_array[] = new Button[BUTTON_MAP];
		
		// init
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
