package com.turbo.whack;

import static com.turbo.whack.Constants.NAME;
import static com.turbo.whack.Constants.SCORE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
		ActivityHelper.activity_init(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);
		
		//Initialize Shtuff
		hScore_list = new ArrayList<HashMap>();
		listView_hScore = (ListView) findViewById(R.id.listView_high_score);
		hScore_hash = new HashMap<String, String>();
		scores = new ArrayList<String>();
		names = new ArrayList<String>();
		
		//Get high scores
		HighScore hScoreInst = new HighScore();
		Map<String, String>[] hScoreMap;
		HashMap<String, String> temp;
		
		hScoreMap = hScoreInst.hs_get_all();
		if(hScoreMap == null) {
			Log.e(Constants.WH_LOG_ERRO, "Failed to retrieve HighScores.");
			assert(false);
		}
		for(int i = 0; i < Constants.WH_MAX_HIGHSCORES; i++) {
			temp = (HashMap<String, String>) hScoreMap[i];
		}
		
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
}
