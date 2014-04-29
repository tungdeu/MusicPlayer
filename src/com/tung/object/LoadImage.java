package com.tung.object;

import java.util.List;

import CustomAdapter.CustomAlbumAdapter;
import CustomAdapter.CustomArtistListAdapter;
import CustomAdapter.CustomSongListAdapter;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;

import com.tung.Entities.OfflineSong;


public class LoadImage extends AsyncTask<Integer, Void, Void> {

	int typeAdapter;
	CustomSongListAdapter songAdapter;
	CustomAlbumAdapter albumAdapter;
	CustomArtistListAdapter artistAdapter;
	int firstVisiblePosition, lastVisiblePosition;
	String path;
	List<OfflineSong> Songs;
	OfflineSong song;
	Bitmap bitmap = null;
	MemoryLruCache memoryLruCache;
	byte[] imageData;

	public LoadImage(CustomSongListAdapter songAdapter) {
		super();
		this.songAdapter = songAdapter;
		this.Songs = songAdapter.getSource();
		memoryLruCache = new MemoryLruCache();
		typeAdapter = 3;
	}
	
	public LoadImage(CustomAlbumAdapter albumAdapter, List<OfflineSong> songs) {
		super();
		this.albumAdapter = albumAdapter;
		this.Songs = songs;
		memoryLruCache = new MemoryLruCache();
		typeAdapter = 1;
	}

	public LoadImage(CustomArtistListAdapter adapter, List<OfflineSong> artist) {
		super();
		this.artistAdapter = adapter;
		this.Songs = artist;
		memoryLruCache = new MemoryLruCache();
		typeAdapter = 2;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		switch (typeAdapter) {
		case 3:
			songAdapter.notifyDataSetChanged();
			break;
		case 2:
			artistAdapter.notifyDataSetChanged();
			break;
		case 1: 
			albumAdapter.notifyDataSetChanged();
			break;
			}
		super.onProgressUpdate(values);
	}

	@Override
	protected Void doInBackground(Integer... arg0) {
		lastVisiblePosition = arg0[0] + 15;
		for (int i = arg0[0]; i < lastVisiblePosition; i++) {
			if (i < Songs.size()) {
				song = Songs.get(i);
				if (song.getAlbum() != null)
					bitmap = memoryLruCache.loadBitmap(song.getAlbum());
				else
					bitmap = memoryLruCache.loadBitmap(song.getPath());
				if (bitmap == null) {
					MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();

					try {
						metaRetriver.setDataSource(song.getPath());
						imageData = metaRetriver.getEmbeddedPicture();
					} catch (Exception e) {
						imageData = null;
					}
					if (imageData != null) {
						bitmap = BitmapProcess.decodeSampledBitmap(imageData,
								100, 100);
						if (song.getAlbum() != null) {
							memoryLruCache.addBitmapToMemoryCache(
									song.getAlbum(), bitmap);
						} else {
							memoryLruCache.addBitmapToMemoryCache(
									song.getPath(), bitmap);
						}
						song.setAlbumArt(bitmap);
					}
				}
				song.setAlbumArt(bitmap);
				publishProgress();
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		switch (typeAdapter) {
		case 3:
			songAdapter.notifyDataSetChanged();
			break;
		case 2:
			artistAdapter.notifyDataSetChanged();
			break;
		case 1:
			albumAdapter.notifyDataSetChanged();
			break;
		}

	}

}
