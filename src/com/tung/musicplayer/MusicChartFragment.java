package com.tung.musicplayer;

import com.tung.screen.MusicChart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MusicChartFragment extends Fragment {

private TextView txtSearch;
private TextView txtDownload;
private TextView txtChart;
private Intent intentSearch;
private Intent intentDownload;
private Intent intentChart;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.online_layout, container, false);
		txtSearch = (TextView)view.findViewById(R.id.online_txtSeach);
		txtDownload = (TextView)view.findViewById(R.id.online_txtDownload);
		txtChart = (TextView)view.findViewById(R.id.online_txtChart);
		
//		intentSearch = new Intent(getActivity(), OnlineSearch.class);
//		intentDownload = new Intent(getActivity(), Download.class);
		
		txtSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startActivity(intentSearch);
			}
		});
		
		txtDownload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startActivity(intentDownload);
			}
		});
		
		txtChart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intentChart = new Intent(getActivity(), MusicChart.class);
				startActivity(intentChart);
			}
		});
		return view;
	}

	

}
