/*
 * This is a helper class. Add any functions that would be required across multiple activities here.
 * It would be best to keep most functions static here.
 * 
 */
package com.turbo.whack;

import java.util.Random;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;

public class ActivityHelper {
	
	public static final String WH_LOG_INFO = "WH_INFO";
	public static final String WH_LOG_WARN = "WH_WARN";
	
	public static long WH_TIMER_CHECK_RATE = 50;
	public static long WH_TIMER_MULTIPLIER = 100;
	public static double WH_HARDNESS_FACTOR = 1.5;
	
	/**
	 * This function must be called before we start doing anything in our activities.
	 */
	public static void activity_init(Activity activity) {
		
		// Set orientation to portrait and lock
		// HF: There are compatibility issues with using the better SCREEN_ORIENTATION_LOCKED
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		
		// Go full screen
		// HF: We should consider hiding the nav bar as well
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
		// HF: I don't like the idea of typecasting but it may be necessary.
		res = (int) num;
		if(res % 10 == 0) {
			res = get_random_number();
		}
		return res;
	}
}