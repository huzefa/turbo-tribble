package com.turbo.whack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
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
		
		//Initialize stuff
		hScore_list = new ArrayList<HashMap>();
		listView_hScore = (ListView) findViewById(R.id.listView_high_score);
		hScore_hash = new HashMap<String, String>();
		scores = new ArrayList<String>();
		names = new ArrayList<String>();
		HighScore hScoreInst = new HighScore();
		
		// HF: This is for testing purposes only.
		hScoreInst.hs_add("P1", 5);
		hScoreInst.hs_add("P2", 9);
		hScoreInst.hs_add("P3", 7);
		hScoreInst.hs_add("P4", 4);
		hScoreInst.hs_add("P5", 2);
		hScoreInst.hs_add("P6", 10);
		hScoreInst.hs_add("P7", 3);
		hScoreInst.hs_add("P9", 8);
		hScoreInst.hs_add("P10", 6);
		hScoreInst.hs_add("P11", 20);
		hScoreInst.hs_add("Dhairya", 1);
		
		// TODO: HF: See if everything under this can be tightened up
		List<Map<String, String>> hs_map = hScoreInst.hs_get_all();
		for(int i = 0; i < hs_map.size(); i++) {
			Map<String, String> m = (HashMap<String, String>) hs_map.get(i);
			if(m.get("name").compareTo("empty") == 0) {
				break;
			}
			names.add(m.get("name"));
			scores.add(m.get("score"));
		}
		
		//Populate List
		int rows = 0;
		for (int i = 0; i < names.size(); ++i){
			++rows;
			hScore_hash = new HashMap<String, String>();
			hScore_hash.put("Name", names.get(i));
			hScore_hash.put("Score", scores.get(i));
			hScore_list.add(hScore_hash);
		}
		if(rows == 0){
			hScore_hash.put("Name", "");
			hScore_hash.put("Score", "");
			hScore_list.add(hScore_hash);
		}
		
		HighScoreListViewAdapter hScore_adapter = new HighScoreListViewAdapter(this,hScore_list);
		listView_hScore.setAdapter(hScore_adapter);	
	}
}
