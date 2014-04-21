package com.turbo.whack;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.util.SparseIntArray;

public class SimpleSoundPlayer {
	public static SparseIntArray soundController = new SparseIntArray();
	private SoundPool sp;
	private AudioManager am;
	private int streams = 1;
	private float volume = 0;
	
	/**
	 * Constructor to create object that can play only one audio stream.
	 */
	public SimpleSoundPlayer() {
		sp = new SoundPool(streams, AudioManager.STREAM_MUSIC, 0);
		am = (AudioManager) ActivityHelper.appContext.getSystemService(Context.AUDIO_SERVICE);
	}
	
	/**
	 * Constructor specifying the number of audio streams to support.
	 * @param streams Number of streams
	 */
	public SimpleSoundPlayer(int streams) {
		this.streams = streams;
		sp = new SoundPool(streams, AudioManager.STREAM_MUSIC, 0);
		am = (AudioManager) ActivityHelper.appContext.getSystemService(Context.AUDIO_SERVICE);
	}
	
	/**
	 * All audio files must be loaded well in advance of playing. It is highly recommended that you
	 * load your audio files in the application init. 
	 * 
	 * Currently supported formats: ogg.
	 * 
	 * @param res_id The resource id of the tune
	 * @return Zero on success.
	 */
	public int load(int res_id) {
		int value = sp.load(ActivityHelper.appContext, res_id, 1);
		soundController.append(res_id, value);
		return 0;
	}
	
	/**
	 * Play a preloaded audio file.
	 * @param res_id The resource id of the tune.
	 * @return Zero on success.
	 */
	public int play(int res_id) {
		int file = soundController.get(res_id);
		sp.stop(file);
		volume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		sp.play(file, volume, volume, 1, 0, 1f);
		return 0;
	}
	
	/**
	 * Stop playing a audio file if its playing.
	 * @param res_id The resource id of the tune.
	 * @return Zero on success.
	 */
	public int stop(int res_id) {
		int file = soundController.get(res_id);
		sp.stop(file);
		return 0;
	}
	
	/**
	 * This is EXPERIMENTAL. It is not guaranteed to play anything. Not recommended to be used for fast
	 * recurring tunes since it loads the tune when called and has a 200ms delay. Under normal circumstances,
	 * there is no reason to call this.
	 * @param res_id The resource id of the tune.
	 * @return Zero on success.
	 */
	public int playNOW(int res_id) {
		int file = sp.load(ActivityHelper.appContext, res_id, 1);
		try {
			Thread.sleep(200);
		} catch(Exception e) {
			Log.w(Constants.WH_LOG_WARN, "The thread failed to sleep.");
			return -1;
		}
		sp.play(file, volume, volume, 1, 0, 1f);
		return 0;
	}
}
