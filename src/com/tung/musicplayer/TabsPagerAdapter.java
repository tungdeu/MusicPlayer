package com.tung.musicplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			return new SongListFragment();
		case 1:
			return new ArtistListFragment();
		case 2:
			return new AlbumList();
		case 3:
			return new PlayListFragment();
		case 4: 
			return new MusicChartFragment();

		}
		return null;
	}

	@Override
	public int getCount() {
		return 5;
	}
}
