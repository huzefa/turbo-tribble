package com.turbo.whack;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

// HF: This class is not thread-safe. See wiki.
public class HighScore {
	private SimpleDataStore sd;
	private JSONObject jObj;
	
	/**
	 * Highscore object constructor.
	 * @throws JSONException Non recoverable.
	 */
	public HighScore() throws JSONException {
		sd = new SimpleDataStore(ActivityHelper.WH_APP_NAME);
		String data = sd.retrieve_string_value(ActivityHelper.WH_DATA_NAME);
		if(data != null) {
			jObj = new JSONObject(data);
		}
		else {
			Log.i(ActivityHelper.WH_LOG_WARN, "Failed to retrive data. Creating new record.");
			sd.store_string_value(ActivityHelper.WH_DATA_NAME, "empty");
			jObj = new JSONObject();
			for(int i = 1; i <= 10; i++) {
				jObj.put("record" + i, null);
			}
		}
	};
	
	/**
	 * This function serializes the data to work with JSON and modifies the JSON file accordingly.
	 * @param name The name associated with the highscore. Cannot be null.
	 * @param score The score associated with the highscore. Must be greater than zero.
	 * @return Zero on success. Non-zero on failure.
	 * @throws JSONException
	 */
	public int hs_serialize(String name, int score) throws JSONException {
		if(name == null || score <= 0) {
			Log.i(ActivityHelper.WH_LOG_INFO, "Invalid name or score.");
			return -1;
		}
		
		String record = hs_get_lowest(score);
		if(record == null) {
			Log.i(ActivityHelper.WH_LOG_ERRO, "Something wicked happened." +
					" Failed to get lowest record.");
			return -1;
		}
		if(record.compareTo("exists") == 0) {
			return 0;
		}
		
		Map<String, String> map = new HashMap<String, String>(); ///< Optimization options available.
		map.put("name", name);
		map.put("score", Integer.toString(score));	
		jObj.put(record, map);
		return 0;
	}
	
	// hs_deserialize();
	
	/**
	 * A function that gets the record holding the lowest score when compared to the current score.
	 * @param score The current score
	 * @return A string that is the record name. Null if not a highscore.
	 * @throws JSONException If we catch this, there is very little possibility of recovery.
	 */
	@SuppressWarnings("unchecked")
	private String hs_get_lowest(int score) throws JSONException {
		boolean is_empty = true;
		boolean score_exists = false;
		final String record = null;
		
		// Check if empty or exists
		for(int i = 1; i <= 10; i++) {
			int a = hs_get_score("record" + i);
			if(score == a) {
				is_empty = false;
				score_exists = true;
			}
			if(a != -1) {
				is_empty = false;
			}
		}
		if(score_exists) {
			Log.i(ActivityHelper.WH_LOG_INFO, "Score exists. It has not been added.");
			return "exists";
		}
		if(is_empty) {
			return "record1";
		}
		
		// Figure out where to put it and adjust.
		for(int i = 1; i <= 10; i++) {
			String rec = "record" + i;
			int s = hs_get_score(rec);
			if(s < 0) {
				return rec;
			}
			if(i < score) {
				hs_push_down(i);
				return rec;
			}
		}
		// The score is not a high score.
		return null;
	}
	
	/**
	 * Pushes down all the scores by 1 to make room for another.
	 * @param rank The rank where you want to insert.
	 * @return zero on success
	 * @throws JSONException Non-recoverable.
	 */
	@SuppressWarnings("unchecked")
	private int hs_push_down(int rank) {
		Map<String, String> tmp;
		String r;
		try {
			for(int i = 10; i >= rank; i--) {
				r = "record" + (i - 1);
				tmp = (HashMap<String, String>) jObj.get(r);
				jObj.put("record" + i, tmp);
			}		
			jObj.put("record" + rank, null);
		} catch(JSONException j) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * A private function that gets the score from a record.
	 * @param record
	 * @return An integer that represents the score. -1 on failure.
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	private int hs_get_score(String record) throws JSONException {
		int res;
		Map<String, String> map = (HashMap<String, String>) jObj.get(record);
		
		try {
			res = Integer.parseInt(map.get("score"));
		} catch(NumberFormatException e) {
			return -1;
		}
		return res;
	}
	
	/**
	 * A API that returns a sorted array of size 10 with the highscores.
	 * @return An array of type Map<String, String>[] where the strings are name and score respectively
	 */
	public Map<String, String>[] hs_get_all() {
		return null;	
	}
	
	/**
	 * A function that must be called to save the highscores to memory to be loaded next time.
	 * Make sure that you call this function before losing the object or there is no guarantee that
	 * the scores will be recorded.
	 */
	public boolean hs_close() {
		String s = jObj.toString();
		if(s == null) {
			Log.i(ActivityHelper.WH_LOG_ERRO, "String conversion failed.");
			return false;
		}
		return sd.store_string_value(ActivityHelper.WH_DATA_NAME, s);
	}
}
