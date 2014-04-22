package com.tung.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.tung.musicplayer.R;
import com.tung.musicplayer.R.id;
import com.tung.musicplayer.R.layout;
import com.tung.musicplayer.R.menu;

public class Home extends SherlockActivity {

	ActionBar actionBar;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		actionBar = getSupportActionBar();
		intent = new Intent(this, SongList.class);
		
		RelativeLayout row1 = (RelativeLayout)findViewById(R.id.relativeLayout1);
		row1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Click row 1", Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
