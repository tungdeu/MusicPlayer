package com.tung.object;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

import com.tung.Entities.OfflineSong;

public class CreateList {

	private ArrayList<OfflineSong> songList = new ArrayList<OfflineSong>();
	private Activity activity;

	public CreateList(Activity act) {
		this.activity = act;

	}

	public ArrayList<OfflineSong> CreateAllSongList() {
		Cursor cursor = activity.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				"LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
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
				OfflineSong song = new OfflineSong();

				tmp = cursor.getString(cursor
						.getColumnIndex(MediaStore.MediaColumns.TITLE));
				if (tmp.isEmpty())
					tmp = cursor.getString(cursor
							.getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
				if (tmp.isEmpty())
					tmp = cursor
							.getString(cursor
									.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
				song.setTitle(tmp);
				song.setPath(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
				song.setAudioId(cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID)));
				songList.add(song);
			}
		} while (cursor.moveToNext());

		return songList;
	}

	public ArrayList<OfflineSong> CreateSongListFromArtist(String artist) {
		Cursor cursor = activity.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
				MediaStore.Audio.Media.ARTIST + " ='" + artist + "'", null,
				"LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

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

				OfflineSong song = new OfflineSong();

				tmp = cursor.getString(cursor
						.getColumnIndex(MediaStore.MediaColumns.TITLE));
				if (tmp.isEmpty())
					tmp = cursor.getString(cursor
							.getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
				if (tmp.isEmpty())
					tmp = cursor
							.getString(cursor
									.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
				song.setTitle(tmp);
				song.setPath(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
				song.setAudioId(cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID)));
				songList.add(song);
			}
		} while (cursor.moveToNext());
		return songList;
	}

	public ArrayList<OfflineSong> CreateSongListFromAlbum(String album) {
		Cursor cursor = activity.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
				MediaStore.Audio.Media.ALBUM + " ='" + album + "'", null,
				"LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

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

				OfflineSong song = new OfflineSong();

				tmp = cursor.getString(cursor
						.getColumnIndex(MediaStore.MediaColumns.TITLE));
				if (tmp.isEmpty())
					tmp = cursor.getString(cursor
							.getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
				if (tmp.isEmpty())
					tmp = cursor
							.getString(cursor
									.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
				song.setTitle(tmp);
				song.setPath(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
				song.setAudioId(cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID)));
				songList.add(song);
			}
		} while (cursor.moveToNext());
		return songList;
	}

	public ArrayList<OfflineSong> CreateSongListFromPlayList(long playListId) {
		Cursor cursor = activity.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
				MediaStore.Audio.Playlists._ID + " ='" + playListId + "'",
				null, "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

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

				OfflineSong song = new OfflineSong();

				tmp = cursor.getString(cursor
						.getColumnIndex(MediaStore.MediaColumns.TITLE));
				if (tmp.isEmpty())
					tmp = cursor.getString(cursor
							.getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
				if (tmp.isEmpty())
					tmp = cursor
							.getString(cursor
									.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
				song.setTitle(tmp);
				song.setPath(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
				song.setAudioId(cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID)));
				songList.add(song);
			}
		} while (cursor.moveToNext());
		return songList;
	}

}
