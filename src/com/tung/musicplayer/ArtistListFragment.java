package com.tung.musicplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import CustomAdapter.CustomArtistListAdapter;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tung.Entities.OfflineSong;
import com.tung.object.BitmapProcess;
import com.tung.object.LoadImage;
import com.tung.object.MemoryLruCache;
import com.tung.screen.ArtistDetail;
import com.tung.screen.MusicChart;

public class ArtistListFragment extends Fragment {

	List<OfflineSong> Artist;
	CustomArtistListAdapter adapter;
	ListView lst;
	Intent intent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.simple_list, container, false);

		Cursor cursor = getActivity().getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				"LOWER(" + MediaStore.Audio.Media.ARTIST + ") ASC");
		Artist = new ArrayList<OfflineSong>();
		String tmp_ext = "";
		String tmp = "";
		String extension = "mp3";

		cursor.moveToFirst();
		do {
			tmp = cursor
					.getString(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
			tmp_ext = tmp.substring(tmp.length() - 3);
			tmp = tmp.substring(0, tmp.length() - 4);
			if (tmp_ext.compareTo(extension) == 0
					&& !cursor
							.getString(
									cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
							.contains("Ringtone")) {

				OfflineSong artist = new OfflineSong();

				artist.setPath(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));

				tmp = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.ARTIST));
				if (tmp.isEmpty())
					tmp = cursor.getString(cursor
							.getColumnIndex(MediaStore.EXTRA_MEDIA_ARTIST));

				artist.setArtist(tmp);
				artist.setAudioId(cursor.getLong(cursor
						.getColumnIndex(MediaStore.MediaColumns._ID)));
				boolean found = false;
				for (OfflineSong p : Artist) {
					if (p.getArtist().equals(artist.getArtist())) { 
						found = true;
						break;
					}
				}
				if (!found)
					Artist.add(artist);
				// count++;
			}

		} while (cursor.moveToNext());
		Collections.sort(Artist, new ArtistComparator());
		lst = (ListView) view.findViewById(R.id.listView1);
		adapter = new CustomArtistListAdapter(getActivity(), Artist);
		lst.setAdapter(adapter);

		LoadImage loadImage = new LoadImage(adapter, Artist);
		loadImage.execute(lst.getFirstVisiblePosition());

		lst.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == 0) {
					LoadImage loadImage = new LoadImage(adapter, Artist);
					loadImage.execute(lst.getFirstVisiblePosition());
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});

		intent = new Intent(getActivity(), ArtistDetail.class);

		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String artist = Artist.get(arg2).getArtist();
				String path = Artist.get(arg2).getPath();
				long audioId = Artist.get(arg2).getAudioId();
				intent.putExtra("id", audioId);
				intent.putExtra("artist", artist);
				intent.putExtra("path", path);
				startActivity(intent);

			}
		});

		return view;
	}

}
