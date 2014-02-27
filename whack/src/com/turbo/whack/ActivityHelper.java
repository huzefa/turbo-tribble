/*
 * This is a helper class. Add any functions that would be required across multiple activities here.
 * 
 */
package com.turbo.whack;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;

public class ActivityHelper {
	/*
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
}