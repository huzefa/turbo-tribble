/*
 * This is a helper class. Add any functions that would be required across multiple activities here.
 * It would be best to keep most functions static here.
 * 
 */
package com.turbo.whack;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;

public class ActivityHelper {
	
	public static final String WH_LOG_INFO = "WH_INFO";
	public static final String WH_LOG_WARN = "WH_WARN";
	
	public static long WH_TIMER_CHECK_RATE = 50;
	public static long WH_TIMER_MULTIPLIER = 100;
	public static double WH_LEVEL_DECAY_FACTOR = -0.1;
	public static String WH_APP_NAME;
	
	private static Context context;
	private static int WH_MAX_HIGHSCORES = 10;
	
	/**
	 * This function must be called before we start doing anything in our activities.
	 */
	public static void activity_init(Activity activity) {
		
		// Set orientation to portrait and lock
		// HF: There are compatibility issues with using the better SCREEN_ORIENTATION_LOCKED
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		
		// Go full screen
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		WH_APP_NAME = activity.getApplicationContext().getPackageName();
		context = activity.getApplicationContext();
	}
	
	/**
	 * A random number that can be used as an index for our buttons.
	 * @return
	 */
	public static int get_random_number() {
		double num = 0;
		int res;
		
		Random r = new Random();
		num = r.nextInt(75 - 10);
		num = Math.round((num + 5)/ 10.0) * 10.0;
		num = num +	(r.nextInt(75 - 10) % 4);
		res = (int) num;
		
		// HF: We should be able to filter out this case without doing this.
		if(res % 10 == 0) {
			res = get_random_number();
		}
		return res;
	}
	
	public static int get_new_level(long current_level) {
		double result = 0;
		result = Math.ceil(current_level * Math.exp(WH_LEVEL_DECAY_FACTOR));
		return (int) result;
	}
	
	public static int record_score(String name, int score) {
		DataStore storage = new DataStore(context, name);
		String record = "record";
		int err;
		
		for(int i = 0; i <= WH_MAX_HIGHSCORES; i++) {
			String iString = Integer.toString(i);
			err = storage.retrieve_int_value(record + iString);
			if(err == -1) {
				storage.store_string_value(name, iString);
				storage.store_int_value(iString, score);
				break;
			}
		}
		
		return 0;
	}
}