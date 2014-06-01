package com.tung.screen;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tung.musicplayer.R;
import com.tung.object.API;


public class MusicChart extends Activity{
	private ListView lst;
	private ArrayList<String> arrCate = new ArrayList<String>();
	private ArrayAdapter<String> adapter = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chart_layout, container, false);
		lst = (ListView) view.findViewById(R.id.chart_layout_lstView);
		API api = new API();
		arrCate = api.getListArtist();
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrCate);
		lst.setAdapter(adapter);
		
		return view;
	}
}

