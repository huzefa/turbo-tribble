package com.turbo.whack;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HighScoreListViewAdapter extends BaseAdapter{
	Activity activity;
	ArrayList<HashMap> list;
	
	public HighScoreListViewAdapter(Activity activity, ArrayList<HashMap> list){
		super();
		this.activity = activity;
		this.list = list;
	}
	
	private class ViewHolder {
        TextView name, score;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = activity.getLayoutInflater();
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.row_high_score, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.hsRow_name);
			holder.score = (TextView) convertView.findViewById(R.id.hsRow_score);
			convertView.setTag(holder);
		}
		else{
            holder = (ViewHolder) convertView.getTag();
        }
		HashMap<?, ?> map = list.get(position);
		holder.name.setText((CharSequence) map.get("Name"));
		holder.score.setText((CharSequence) map.get("Score"));
		return convertView;
	}	
	

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}



}
