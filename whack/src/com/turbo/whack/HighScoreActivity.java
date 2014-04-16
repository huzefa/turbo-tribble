package com.turbo.whack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class HighScoreActivity extends Activity {
	private ArrayList<HashMap> hScore_list;	
	private ListView listView_hScore;
	private HashMap<String, String> hScore_hash;
	private List<Map<String, String>> hs_map;
	
	private ArrayList<String> scores;
	private ArrayList<String> names;
	
	private String highScoreName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityHelper.activity_init(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);
		
		final Intent i = getIntent();
		Log.d("MSG", ""+i.getBooleanExtra("FROM_GAME_PLAY_ACTIVITY", false));
		
		if(i.getBooleanExtra("FROM_GAME_PLAY_ACTIVITY", false)){
<<<<<<< HEAD
			
=======
>>>>>>> 5008d27ebd08b739246b8708765f0a527313b5b3
			Dialog enterNameDialog = onCreateDialog(this);
			enterNameDialog.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					final int score = i.getIntExtra("score", -1);
					Log.d("MSG", ""+score);
//					new Thread() {
//						public void run() {
//							HighScore h = new HighScore();
//							h.hs_add(highScoreName, score);
//							h.hs_close();
//						}
//					}.start();
//					
					HighScore h = new HighScore();
					h.hs_add(highScoreName, score);
					h.hs_close();
					
					// Display high scores
					HighScoreComputationTask t = new HighScoreComputationTask(HighScoreActivity.this);
					t.execute();
				}
			});
			enterNameDialog.show();
			
		}
		else{
			// Display high scores
			HighScoreComputationTask t = new HighScoreComputationTask(this);
			t.execute();
		}
	}
	
	public Dialog onCreateDialog(Context context) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    // Get the layout inflater
	    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	    final View v = inflater.inflate(R.layout.dialog_name_inputter, null);
	    

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(v)
	    // Add action buttons
	           .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   try{
<<<<<<< HEAD
	            		   EditText inputEditText = ((EditText) v.findViewById(R.id.edit_text_highscore_name_input));
	            		   highScoreName = inputEditText.getText().toString();
	            		   
	 	            	   if(highScoreName == null)
=======
	           			   EditText inputEditText = (EditText) findViewById(R.id.edit_text_highscore_name_input);
	            		   String temp = inputEditText.getText().toString();
	            		   highScoreName = temp;
	            		   Log.d("MSG", "highScoreName"+ highScoreName);
	 	            	   if(highScoreName != null)
>>>>>>> 5008d27ebd08b739246b8708765f0a527313b5b3
	 	            		   highScoreName = "NONAME";
	 	            	   
	            	   }catch(Exception e){
	            		   highScoreName = "NONAME";
	            		   e.printStackTrace();
	            	   }
	            	   
	            	   
	            	   
	               }
	           })
	           .setTitle("Enter name for high score:");      
	    return builder.create();
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
			Log.i(Constants.WH_LOG_INFO, "Finished computing highscores.");
		}
	}
}
