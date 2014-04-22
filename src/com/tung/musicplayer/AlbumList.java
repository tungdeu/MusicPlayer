package com.tung.musicplayer;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import CustomAdapter.CustomAlbumAdapter;
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
import android.widget.GridView;
import android.widget.ImageView;

import com.tung.Entities.OfflineSong;
import com.tung.object.BitmapProcess;
import com.tung.object.LoadImage;
import com.tung.object.MemoryLruCache;
import com.tung.screen.AlbumDetail;

public class AlbumList extends Fragment {
	List<OfflineSong> Album;
	Intent intent;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listalbum_layout, container,
				false);

		Cursor cursor = getActivity().getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				"LOWER(" + MediaStore.Audio.Media.ALBUM + ") ASC");
		Album = new ArrayList<OfflineSong>();
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

				OfflineSong album = new OfflineSong();

				album.setPath(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
				// tmp = cursor.getString(cursor
				// .getColumnIndex(MediaStore.MediaColumns.TITLE));
				// if (tmp.isEmpty())
				// tmp = cursor.getString(cursor
				// .getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
				// if (tmp.isEmpty())
				// tmp = cursor
				// .getString(cursor
				// .getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));

				tmp = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.ALBUM));
				if (tmp.isEmpty())
					tmp = cursor.getString(cursor
							.getColumnIndex(MediaStore.EXTRA_MEDIA_ALBUM));
				// song.setArtist(tmp);
				album.setAlbum(tmp);
				
				
				tmp = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.ARTIST));
				if (tmp.isEmpty())
					tmp = cursor.getString(cursor
							.getColumnIndex(MediaStore.EXTRA_MEDIA_ARTIST));
				album.setArtist(tmp);
				// tmp = cursor.getString(cursor
				// .getColumnIndex(MediaStore.Audio.Media.ALBUM));
				// if (tmp.isEmpty())
				// tmp = cursor.getString(cursor
				// .getColumnIndex(MediaStore.EXTRA_MEDIA_ALBUM));
				// song.setAlbum(tmp);

				boolean found = false;
				for (OfflineSong p : Album) {
					if (p.getAlbum().equals(album.getAlbum())) {
						found = true;
						break;
					}
				}
				if (!found)
					Album.add(album);
				// count++;
			}

		} while (cursor.moveToNext());
		// Locale vi = new Locale("vi_VN");
		// Collator viCollator = Collator.getInstance(vi);
		// Collections.sort(Album, viCollator);
		final GridView grd = (GridView) view.findViewById(R.id.gridView1);
		final CustomAlbumAdapter adapter = new CustomAlbumAdapter(
				getActivity(), Album);
		grd.setAdapter(adapter);
		LoadImage loadImage = new LoadImage(adapter, Album);
		loadImage.execute(grd.getFirstVisiblePosition());

		grd.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == 0) {
					LoadImage loadImage = new LoadImage(adapter, Album);
					loadImage.execute(grd.getFirstVisiblePosition());
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
		intent = new Intent(getActivity(),AlbumDetail.class);
		
		grd.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String album = Album.get(arg2).getAlbum();
				String artist = Album.get(arg2).getArtist();
				String path = Album.get(arg2).getPath();
				intent.putExtra("album", album);
				intent.putExtra("artist",artist);
				intent.putExtra("path", path);
				
				startActivity(intent);
				
			}
			
			
		});

		return view;
	}

}
