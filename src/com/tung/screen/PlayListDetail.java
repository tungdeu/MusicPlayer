package com.tung.screen;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;

public class PlayListDetail extends Activity{
	public ArrayList<OfflineSong> Songs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist_detail);
		
		Intent intent = getIntent();
		String playListName = intent.getStringExtra("name");
		
		
		Cursor cursor = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Audio.Playlists.NAME +" ='"+playListName+"'", null,
				"LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
		
		Songs = new ArrayList<OfflineSong>();
		String tmp_ext = "";
		String tmp = "";
		String extension = "mp3";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	

}
