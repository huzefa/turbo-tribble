/*
 * This is a helper class. Add any functions that would be required across multiple activities here.
 * All functions must be static in this class.
 * 
 */
package com.turbo.whack;

import java.util.Random;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class ActivityHelper {
	
	public static String WH_APP_NAME;
	public static ContextWrapper appContext;
	
	/**
	 * This function must only be called on app init. It sets up certain constants and
	 * loads settings from memory.
	 * @param activity
	 */
	public static void app_init(Activity activity) {
		SimpleDataStore data;
		int temp;
		
		WH_APP_NAME = activity.getApplicationContext().getPackageName();
		appContext = new ContextWrapper(activity.getApplicationContext());
		
		Log.i(Constants.WH_LOG_INFO, "Loading settings.");
		data = new SimpleDataStore(WH_APP_NAME);
		
		// Load sounds setting
		temp = data.retrieve_int_value(Constants.WH_SET_DATA_NAME + "_sounds");
		Constants.WH_SET_SOUNDS = temp >= 0 ? temp : Constants.WH_SET_SOUNDS;
		
		// Load vibrate setting
		temp = data.retrieve_int_value(Constants.WH_SET_DATA_NAME + "_vibrate");
		Constants.WH_SET_VIBRATE = temp >= 0 ? temp : Constants.WH_SET_VIBRATE;
		
		Log.i(Constants.WH_LOG_INFO, "Application init complete.");
	}
	
	/**
	 * This function must be called before we start doing anything in our activities.
	 */
	public static void activity_init(Activity activity) {
		
		// Set orientation to portrait and lock by disabling the sensor
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		
		// Go full screen
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Log.i(Constants.WH_LOG_INFO, "Activity init complete.");
	}
	
	/**
	 * A random number that can be used as an index for our buttons.
	 * @return
	 */
	public static int get_random_number() {		
		int[] randArray = { 11, 12, 13, 21, 22, 23, 31, 32, 33, 41, 42, 43};
		Random r = new Random();
		return randArray[r.nextInt(((randArray.length - 1) - 0) + 1)];
	}
	
	/**
	 * This returns a new internal level to multiply against.
	 * @param current_level The current internal level you're at
	 * @return
	 */
	public static int get_new_level(long current_level) {
		double result = 0;
		result = Math.ceil(current_level * Math.exp(Constants.WH_LEVEL_DECAY_FACTOR));
		return (int) result;
	}
	
	/**
	 * Shows a toast on the screen for LENGTH_SHORT duration. Always returns zero
	 * @param string The string that contains the text to show.
	 * @return Always zero
	 */
	public static int show_toast(String string) {
		Toast toast = Toast.makeText(appContext, string, Toast.LENGTH_SHORT);
		toast.show();
		return 0;
	}
}