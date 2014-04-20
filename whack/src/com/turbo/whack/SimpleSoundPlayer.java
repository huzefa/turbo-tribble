package com.turbo.whack;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

public class SimpleSoundPlayer {
	public static SparseIntArray soundController = new SparseIntArray();
	private SoundPool sp;
	private AudioManager am;
	private int streams = 1;
	private float volume = 0;
	
	public SimpleSoundPlayer() {
		sp = new SoundPool(streams, AudioManager.STREAM_MUSIC, 0);
		am = (AudioManager) ActivityHelper.appContext.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public SimpleSoundPlayer(int streams) {
		this.streams = streams;
		sp = new SoundPool(streams, AudioManager.STREAM_MUSIC, 0);
		am = (AudioManager) ActivityHelper.appContext.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public int load(int res_id) {
		int value = sp.load(ActivityHelper.appContext, res_id, 1);
		soundController.append(res_id, value);
		return 0;
	}
	
	public int play(int res_id) {
		int file = soundController.get(res_id);
		sp.stop(file);
		volume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		sp.play(file, volume, volume, 1, 0, 1f);
		return 0;
	}
	
	public int stop(int res_id) {
		int file = soundController.get(res_id);
		sp.stop(file);
		return 0;
	}
}
