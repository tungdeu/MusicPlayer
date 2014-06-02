package com.tung.screen;

import java.util.List;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;
import com.tung.musicplayer.TabsPagerAdapter;
import com.tung.object.CreateList;

public class SongList extends SherlockFragmentActivity implements
		ActionBar.TabListener {
	private ActionBar.Tab Tab1, Tab2, Tab3, Tab4, Tab5, Tab6;
	private ViewPager mViewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	//SlidingMenu sMenu;
	List<OfflineSong> Songs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_list);
		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.get
		

		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the
						// corresponding tab.
						actionBar.setSelectedNavigationItem(position);
					}
				});
		mViewPager.setAdapter(mAdapter);

		// Add tabs, specifying the tab's text and TabListener
		Tab1 = actionBar.newTab().setText("Songs");
		Tab2 = actionBar.newTab().setText("Artists");
		Tab3 = actionBar.newTab().setText("Albums");
		Tab4 = actionBar.newTab().setText("Playlists");
		Tab5 = actionBar.newTab().setText("Download");
		Tab6 = actionBar.newTab().setText("Music Chart");
		

		Tab1.setTabListener(this);
		Tab2.setTabListener(this);
		Tab3.setTabListener(this);
		Tab4.setTabListener(this);
		Tab5.setTabListener(this);
		Tab6.setTabListener(this);

		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
		actionBar.addTab(Tab3);
		actionBar.addTab(Tab4);
		actionBar.addTab(Tab5);
		actionBar.addTab(Tab6);
		
		MediaPlayer mp = new MediaPlayer();
		CreateList.getInstance().setMediaPlayer(mp);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int itemId = item.getItemId();
	    switch (itemId) {
	    case android.R.id.home:
//	        sMenu.toggle();

	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;

	    }

	    return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.song_list, menu);
		return true;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
