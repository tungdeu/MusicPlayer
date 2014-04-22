package com.tung.screen;

import com.tung.musicplayer.R;
import com.tung.musicplayer.R.layout;
import com.tung.musicplayer.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PlaySong extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_song);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_song, menu);
		return true;
	}

}
