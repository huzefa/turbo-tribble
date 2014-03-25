package com.turbo.whack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	 */
	public HighScore() {
		sd = new SimpleDataStore(ActivityHelper.WH_APP_NAME);
		String data = sd.retrieve_string_value(Constants.WH_DATA_NAME);
		try {
			if(data != null && data.compareTo("empty") != 0) {
				// Simply load the object from memory
				jObj = new JSONObject(data);
			}
			else {
				// Create a new copy and initialize
				Log.i(Constants.WH_LOG_WARN, "Possibly failed to retrive data. Creating new record.");
				sd.store_string_value(Constants.WH_DATA_NAME, "empty");
				jObj = new JSONObject();
				for(int i = 1; i <= Constants.WH_MAX_HIGHSCORES; i++) {
					Map<String, String> m = new HashMap<String, String>();
					m.put("name", "empty");
					m.put("score", "empty");
					jObj.put("record" + Integer.toString(i), m);
				}
			}
		} catch(JSONException j) {
			Log.e(Constants.WH_LOG_ERRO, "Failed to create JSON object. Fatal error.");
			j.printStackTrace();
			// Fail silently. Cannot recover.
		}
	};
	
	/**
	 * This function adds the data if the high score deserves to be added.
	 * @param name The name associated with the highscore. Cannot be null.
	 * @param score The score associated with the highscore. Must be greater than zero.
	 * @return Zero on success. Non-zero on failure.
	 */
	public int hs_add(String name, int score) {
		// You messed up. You're not supposed to pass invalid arguments.
		if(name == null || score <= 0) {
			Log.i(Constants.WH_LOG_INFO, "Invalid name or score.");
			return -1;
		}
		
		// Where can I put the score?
		String record = null;
		try {
			record = hs_get_lowest(name, score);
		} catch(JSONException j) {
			j.printStackTrace();
		}
		// We error'ed out somewhere. Cannot tell where the failure occured. Will silently fail.
		if(record == null) {
			Log.e(Constants.WH_LOG_ERRO, "[hs_add()]:Something wicked happened." +
					" Failed to get lowest record.");
			return -1;
		}
		if(record.compareTo("exists") == 0) {
			Log.i(Constants.WH_LOG_INFO, "Sorry " + name + "! No more room.");
			return 0;
		}
		if(record.compareTo("notHS") == 0) {
			Log.i(Constants.WH_LOG_INFO, name + " didn't make the highscore charts " +
					"with their miserly score of " + Integer.toString(score));
			return 0;
		}
		
		// HF: It may be possible to optimize this.
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("score", Integer.toString(score));
		try {
			jObj.put(record, map);
		} catch(JSONException j) {
			j.printStackTrace();
		}
		return 0;
	}
		
	/**
	 * A API that returns a sorted array of size WH_MAX_HIGHSCORES with the highscores. Make sure you cast the
	 * object stored to HashMap<String, String> before use.
	 * @return An array of type List<Map<String, String>>. Returns null on failure.
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> hs_get_all() {
		List<Map<String, String>> map = new ArrayList<Map<String, String>>();
		for(int i = 0; i < Constants.WH_MAX_HIGHSCORES; i++) {
			try {
				String r = "record" + Integer.toString(i+1);
				Map<String, String> tmp = (HashMap<String, String>) jObj.get(r);
				map.add(tmp);
			} catch(JSONException j) {
				j.printStackTrace();
				return null;
			}
		}
		return map;
	}
	
	/**
	 * A function that must be called to save the highscores to memory to be loaded next time.
	 * Make sure that you call this function before losing the object or there is no guarantee that
	 * the scores will be recorded.
	 * 
	 * @return False on failure. True otherwise.
	 */
	public boolean hs_close() {
		String s = jObj.toString();
		if(s == null) {
			Log.e(Constants.WH_LOG_ERRO, "JSON to string conversion failed.");
			return false;
		}
		return sd.store_string_value(Constants.WH_DATA_NAME, s);
	}
	
	/**
	 * A function that gets the record holding the lowest score when compared to the current score.
	 * @param score The current score
	 * @return A string that is the record name. Null if not a highscore.
	 * @throws JSONException If we catch this, there is very little possibility of recovery.
	 */
	private String hs_get_lowest(String name, int score) throws JSONException {
		boolean is_empty = true;
		int index = 0;
		String rec = null;
		
		// Check if empty or exists or is a duplicate
		for(int i = 1; i <= Constants.WH_MAX_HIGHSCORES; i++) {
			rec = "record" + Integer.toString(i);
			int a = hs_get_score(rec);
//			String iname = hs_get_name(rec);
//			
//			if(iname.compareTo(name) == 0 && a < score) {
//				Log.i(Constants.WH_LOG_INFO, "Duplicate record found.");
//			}			
			if(score == a) {
				is_empty = false;
				int j = i;
				while(hs_get_score("record" + Integer.toString(j)) == a) {
					j++;
					if(j == 11) break;
				}
				if(j == Constants.WH_MAX_HIGHSCORES) return "exists";
				hs_push_down(j);
				return "record" + Integer.toString(j);
			}
			if(a != -1) {
				is_empty = false;
			}
		}
		if(is_empty) {
			Log.i(Constants.WH_LOG_INFO, "The score table was empty! You made it.");
			return "record1";
		}
		// Figure out where to put it and adjust.
		for(int i = index > 0 ? index : 1; i <= Constants.WH_MAX_HIGHSCORES; i++) {
			rec = "record" + Integer.toString(i);
			int s = hs_get_score(rec);
			if(s < 0) {
				Log.w(Constants.WH_LOG_WARN, "[hs_get_lowest()]:We should never have gotten here. " +
						"It has been safely handled though.");
				return rec;
			}
			if(score > s) {
				hs_push_down(i);
				return rec;
			}
		}
		// The score is not a high score.
		return "notHS";
	}
	
	/**
	 * Pushes down all the scores by 1 to make room for another.
	 * @param rank The rank where you want to insert.
	 * @return zero on success
	 * @throws JSONException Non-recoverable.
	 */
	private int hs_push_down(int rank) {
		Map<String, String> tmp;
		String r;
		try {
			for(int i = Constants.WH_MAX_HIGHSCORES; i >= rank; i--) {
				r = "record" + Integer.toString(i - 1);
				tmp = (HashMap<String, String>) jObj.get(r);
				jObj.put("record" + Integer.toString(i), tmp);
			}		
			jObj.put("record" + Integer.toString(rank), null);
		} catch(JSONException j) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * A private function that gets the score from a record.
	 * @param record A string
	 * @return An integer that represents the score. -1 on failure.
	 * @throws JSONException
	 */
	private int hs_get_score(String record) throws JSONException {
		int res;
		@SuppressWarnings("unchecked") // HF: The compiler is being pedantic about unchecked casting.
		Map<String, String> map = (HashMap<String, String>) jObj.get(record);
		try {
			res = Integer.parseInt(map.get("score"));
		} catch(NumberFormatException e) {
			return -1;
		}
		return res;
	}
	
	/**
	 * A private function that gets the name from a record.
	 * @param record A string
	 * @return A string that represents the name.
	 * @throws JSONException
	 */
	private String hs_get_name(String record) throws JSONException {
		String res;
		@SuppressWarnings("unchecked")
		Map<String, String> map = (HashMap<String, String>) jObj.get(record);
		res = map.get("name");
		return res;
	}
}
