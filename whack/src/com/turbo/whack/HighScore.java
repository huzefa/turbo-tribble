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
	
	// hs_check()
	
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
	
	@SuppressWarnings("unchecked")
	private String hs_get_lowest(int score) throws JSONException {
		boolean is_empty = true;
		boolean score_exists = false;
		final String record = "record";
		
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
		
		
		
		// This cannot be null.
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private int hs_get_score(String record) throws JSONException {
		int res;
		
		// HF: If the type cast fails, we're done for.
		Map<String, String> map = (HashMap<String, String>) jObj.get(record);
		try {
			res = Integer.parseInt(map.get("score"));
		} catch(NumberFormatException e) {
			return -1;
		}
		return res;
	}
	
	public Map<String, String>[] hs_get_all() {
		return null;	
	}
	
	public boolean close() {
		String s = jObj.toString();
		if(s == null) {
			Log.i(ActivityHelper.WH_LOG_ERRO, "String conversion failed.");
			return false;
		}
		return sd.store_string_value(ActivityHelper.WH_DATA_NAME, s);
	}
}
