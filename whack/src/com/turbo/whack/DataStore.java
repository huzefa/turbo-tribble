package com.turbo.whack;

import java.util.HashSet;
import java.util.Set;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

public class DataStore {
	private String app_name = null;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	
	/**
	 * Constructor to retrieve a DataStore object.
	 * @param context The application context. Preferably this shouldn't be the activity.
	 * @param name The handle for the storage file.
	 */
	public DataStore(Context context, String name) {
		app_name = name;
		pref = context.getSharedPreferences(app_name, Context.MODE_PRIVATE);
		editor = pref.edit();
	}
	
	/**
	 * Add a new key or overwrite an existing int value
	 * @param key type string
	 * @param value type string
	 * @return False on failure, true otherwise.
	 */
	public boolean store_int_value(String key, int value) {
		editor.putInt(key, value);
		return editor.commit();
	}
	
	/**
	 * Add two values mapped to the same key. This is experimental.
	 * @param key
	 * @param value1
	 * @param value2
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public boolean store_multi_string(String key, String value1, String value2) {
		Set<String> set = new HashSet<String>(); ///< Set<type> is an interface
		set.add(value1);
		set.add(value2);
		editor.putStringSet(key, set);
		return editor.commit();
	}
	
	/**
	 * Add a new key or overwrite existing string value
	 * @param key type string
	 * @param value type string
	 * @return False on failure, true otherwise.
	 */
	public boolean store_string_value(String key, String value) {
		editor.putString(key, value);
		return editor.commit();
	}
	
	/**
	 * Retrieves an integer value from the stored table. Returns -1 on failure.
	 * @param key type string
	 * @return
	 */
	public int retrieve_int_value(String key) {
		return pref.getInt(key, -1);
	}
	
	/**
	 * Retrieves a string value from the stored table. Returns null on failure.
	 * @param key type string
	 * @return A string containing the value
	 */
	public String retrieve_string_value(String key) {
		return pref.getString(key, null);
	}
	
	/**
	 * Remove all entries that are stored.
	 * @return False on failure, true otherwise.
	 */
	public boolean remove_all() {
		editor.clear();
		return editor.commit();
	}
	
	/**
	 * Remove a specific entry that is mapped to the key.
	 * @param key type String
	 * @return False on failure, true otherwise.
	 */
	public boolean remove_key(String key) {
		editor.remove(key);
		return editor.commit();
	}
}
