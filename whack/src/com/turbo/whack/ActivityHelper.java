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
import android.view.Window;
import android.view.WindowManager;

public class ActivityHelper {
	
	public static String WH_APP_NAME;
	public static ContextWrapper context;
	
	/**
	 * This function must only be called on app init.
	 * @param activity
	 */
	public static void app_init(Activity activity) {
		WH_APP_NAME = activity.getApplicationContext().getPackageName();
		context = new ContextWrapper(activity.getApplicationContext());
	}
	
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
}