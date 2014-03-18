package com.turbo.whack;

import static com.turbo.whack.Constants.NAME;
import static com.turbo.whack.Constants.SCORE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class HighScoreActivity extends Activity {
	private ArrayList<HashMap> hScore_list;	
	private ListView listView_hScore;
	private HashMap<String, String> hScore_hash;
	
	private ArrayList<String> scores;
	private ArrayList<String> names;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);
		
		//Initialize Shtuff
		hScore_list = new ArrayList<HashMap>();
		listView_hScore = (ListView) findViewById(R.id.listView_high_score);
		hScore_hash = new HashMap<String, String>();
		scores = new ArrayList<String>();
		names = new ArrayList<String>();
		
		//Get high scores
		HighScore hScoreInst = null;
		Map<String, String>[] hScoreMap;
		try {
			hScoreInst = new HighScore();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// This gives me a null pointer exception... I don't think it's returning anything.
//		synchronized(hScoreInst) {
//			hScoreMap = hScoreInst.hs_get_all();
//			Log.d("HSCORE MAP LENGTH", hScoreMap.length + "");
//		}		
		
		names.add("Dhairya");
		names.add("Huzefa");
		
		scores.add("1000");
		scores.add("1");
		
		
		//Populate List
		int rows = 0;
		for (int i = 0; i < names.size(); ++i){
			++rows;
			hScore_hash = new HashMap<String, String>();
			hScore_hash.put(NAME, names.get(i));
			hScore_hash.put(SCORE, scores.get(i));
			hScore_list.add(hScore_hash);
		}
		if(rows == 0){
			hScore_hash.put(NAME, "");
			hScore_hash.put(SCORE, "");
			hScore_list.add(hScore_hash);
		}
		
		HighScoreListViewAdapter hScore_adapter = new HighScoreListViewAdapter(this,hScore_list);
		listView_hScore.setAdapter(hScore_adapter);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score, menu);
		return true;
	}

}
