package com.turbo.whack;

public class Constants {
	// Internal app log constants
	public static final String WH_LOG_INFO = "WH_INFO";
	public static final String WH_LOG_WARN = "WH_WARN";
	public static final String WH_LOG_ERRO = "WH_ERRO";
	public static final String WH_LOG_DEBUG = "WH_DEBUG";
	
	// Internal app names
	public static final String WH_HS_DATA_NAME = "wh_hs_data";
	public static final String WH_SET_DATA_NAME = "wh_set_data";
	
	// Internal constants
	public static long WH_TIMER_CHECK_RATE = 50;
	public static long WH_TIMER_MULTIPLIER = 100;
	public static double WH_LEVEL_DECAY_FACTOR = -0.1;
	public static int WH_MAX_HIGHSCORES = 10;
	public static long WH_VIBRATE_SHORT = 20;
	public static long WH_VIBRATE_LONG = 200;
	public static int WH_MAX_SOUND_STREAMS = 4;
	
	// App settings. They must be restored during runtime.
	public static int WH_SET_SOUNDS = 1;
	public static int WH_SET_VIBRATE = 1;
}
