package com.turbo.whack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class HighScoreActivity extends Activity {
	private ArrayList<HashMap> hScore_list;	
	private ListView listView_hScore;
	private HashMap<String, String> hScore_hash;
	private List<Map<String, String>> hs_map;
	
	private ArrayList<String> scores;
	private ArrayList<String> names;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.activity_init(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);
		
		// Display high scores
		HighScoreComputationTask t = new HighScoreComputationTask(this);
		t.execute();
	}
	
	// HF: Void != void
	private class HighScoreComputationTask extends AsyncTask<Void, Void, Void> {
		private Activity activity;
		
		public HighScoreComputationTask(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		protected void onPreExecute() {
			Log.i(Constants.WH_LOG_INFO, "Start computing highscores.");
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			Log.i(Constants.WH_LOG_INFO, "Computation started in background thread.");
			
			HighScore hScoreInst = new HighScore();		
			hs_map = hScoreInst.hs_get_all();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			Log.i(Constants.WH_LOG_INFO, "Finished computing highscores.");
			//Initialize stuff
			hScore_list = new ArrayList<HashMap>();
			listView_hScore = (ListView) findViewById(R.id.listView_high_score);
			
			hScore_hash = new HashMap<String, String>();
			scores = new ArrayList<String>();
			names = new ArrayList<String>();
			
			// TODO: HF: See if everything under this can be tightened up
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
			for (int i = 0; i < names.size(); ++i) {
				++rows;
				hScore_hash = new HashMap<String, String>();
				hScore_hash.put("Name", names.get(i));
				hScore_hash.put("Score", scores.get(i));
				hScore_list.add(hScore_hash);
			}
			if(rows == 0) {
				hScore_hash.put("Name", "");
				hScore_hash.put("Score", "");
				hScore_list.add(hScore_hash);
			}
			
			HighScoreListViewAdapter hScore_adapter = new HighScoreListViewAdapter(activity, hScore_list);
			listView_hScore.setAdapter(hScore_adapter);
		}
	}
}
