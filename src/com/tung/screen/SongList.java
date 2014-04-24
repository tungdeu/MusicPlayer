package com.tung.screen;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tung.musicplayer.R;
import com.tung.musicplayer.TabsPagerAdapter;
import com.tung.musicplayer.R.dimen;
import com.tung.musicplayer.R.drawable;
import com.tung.musicplayer.R.id;
import com.tung.musicplayer.R.layout;
import com.tung.musicplayer.R.menu;

public class SongList extends SherlockFragmentActivity implements
		ActionBar.TabListener {
	private ActionBar.Tab Tab1, Tab2, Tab3, Tab4, Tab5;
	private ViewPager mViewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	List<String> Songs;
	SlidingMenu sMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_list);
		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
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

		// Add 3 tabs, specifying the tab's text and TabListener
		Tab1 = actionBar.newTab().setText("Songs");
		Tab2 = actionBar.newTab().setText("Artists");
		Tab3 = actionBar.newTab().setText("Albums");
		Tab4 = actionBar.newTab().setText("Playlists");
		Tab5 = actionBar.newTab().setText("Download");
		

		Tab1.setTabListener(this);
		Tab2.setTabListener(this);
		Tab3.setTabListener(this);
		Tab4.setTabListener(this);
		Tab5.setTabListener(this);

		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
		actionBar.addTab(Tab3);
		actionBar.addTab(Tab4);
		actionBar.addTab(Tab5);
		
		
		sMenu = new SlidingMenu(this);
		sMenu.setMode(SlidingMenu.LEFT);
		sMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        sMenu.setShadowWidthRes(R.dimen.shadow_width);
        sMenu.setShadowDrawable(R.drawable.shadow);
        sMenu.setBehindOffset(150);
        sMenu.setFadeDegree(0.35f);
        sMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        sMenu.setMenu(R.layout.activity_home);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int itemId = item.getItemId();
	    switch (itemId) {
	    case android.R.id.home:
	        sMenu.toggle();

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