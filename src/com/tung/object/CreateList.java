package com.tung.object;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.widget.MediaController.MediaPlayerControl;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.SongListFragment.TitleComparator;

public class CreateList {
	private static CreateList mInstance = null;

	private ArrayList<OfflineSong> songList = new ArrayList<OfflineSong>();
	private MediaPlayer mediaPlayer = new MediaPlayer();
	
	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	private CreateList() {

	}

	public ArrayList<OfflineSong> getSongList() {
		return songList;
	}

	public void setSongList(ArrayList<OfflineSong> songList) {
		this.songList = songList;
	}

	public static CreateList getInstance() {
		if (mInstance == null) {
			mInstance = new CreateList();
		}
		return mInstance;
	}

	// public ArrayList<OfflineSong> CreateAllSongList(Activity act) {
	// Cursor cursor = act.getContentResolver().query(
	// MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
	// "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
	// String tmp_ext = "";
	// String tmp = "";
	// String extension = "mp3";
	//
	// cursor.moveToFirst();
	// do {
	// tmp = cursor
	// .getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
	// tmp_ext = tmp.substring(tmp.length() - 3);
	// tmp = tmp.substring(0, tmp.length() - 4);
	// if (tmp_ext.compareTo(extension) == 0
	// && !cursor
	// .getString(
	// cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
	// .contains("Ringtone")) {
	// OfflineSong song = new OfflineSong();
	//
	// tmp = cursor.getString(cursor
	// .getColumnIndex(MediaStore.MediaColumns.TITLE));
	// if (tmp.isEmpty())
	// tmp = cursor.getString(cursor
	// .getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
	// if (tmp.isEmpty())
	// tmp = cursor
	// .getString(cursor
	// .getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
	// song.setTitle(tmp);
	// song.setPath(cursor.getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
	// song.setAudioId(cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID)));
	// songList.add(song);
	// }
	// } while (cursor.moveToNext());
	// Collections.sort(songList, new TitleComparator());
	// return songList;
	// }

	// public ArrayList<OfflineSong> CreateSongListFromArtist(String artist,
	// Activity act) {
	// Cursor cursor = act.getContentResolver().query(
	// MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
	// MediaStore.Audio.Media.ARTIST + " ='" + artist + "'", null,
	// "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
	//
	// String tmp_ext = "";
	// String tmp = "";
	// String extension = "mp3";
	//
	// cursor.moveToFirst();
	// do {
	// tmp = cursor
	// .getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
	// tmp_ext = tmp.substring(tmp.length() - 3);
	// tmp = tmp.substring(0, tmp.length() - 4);
	// if (tmp_ext.compareTo(extension) == 0
	// && !cursor
	// .getString(
	// cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
	// .contains("Ringtone")) {
	//
	// OfflineSong song = new OfflineSong();
	//
	// tmp = cursor.getString(cursor
	// .getColumnIndex(MediaStore.MediaColumns.TITLE));
	// if (tmp.isEmpty())
	// tmp = cursor.getString(cursor
	// .getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
	// if (tmp.isEmpty())
	// tmp = cursor
	// .getString(cursor
	// .getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
	// song.setTitle(tmp);
	// song.setPath(cursor.getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
	// song.setAudioId(cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID)));
	// songList.add(song);
	// }
	// } while (cursor.moveToNext());
	// Collections.sort(songList, new TitleComparator());
	// return songList;
	// }

	// public ArrayList<OfflineSong> CreateSongListFromAlbum(String album,
	// Activity act) {
	// Cursor cursor = act.getContentResolver().query(
	// MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
	// MediaStore.Audio.Media.ALBUM + " ='" + album + "'", null,
	// "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
	//
	// String tmp_ext = "";
	// String tmp = "";
	// String extension = "mp3";
	//
	// cursor.moveToFirst();
	// do {
	// tmp = cursor
	// .getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
	// tmp_ext = tmp.substring(tmp.length() - 3);
	// tmp = tmp.substring(0, tmp.length() - 4);
	// if (tmp_ext.compareTo(extension) == 0
	// && !cursor
	// .getString(
	// cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
	// .contains("Ringtone")) {
	//
	// OfflineSong song = new OfflineSong();
	//
	// tmp = cursor.getString(cursor
	// .getColumnIndex(MediaStore.MediaColumns.TITLE));
	// if (tmp.isEmpty())
	// tmp = cursor.getString(cursor
	// .getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
	// if (tmp.isEmpty())
	// tmp = cursor
	// .getString(cursor
	// .getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
	// song.setTitle(tmp);
	// song.setPath(cursor.getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
	// song.setAudioId(cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID)));
	// songList.add(song);
	// }
	// } while (cursor.moveToNext());
	// Collections.sort(songList, new TitleComparator());
	// return songList;
	// }

	// public ArrayList<OfflineSong> CreateSongListFromPlayList(long playListId,
	// Activity act) {
	// Cursor cursor = act
	// .getContentResolver().query(
	// MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
	// MediaStore.Audio.Playlists._ID + " ='" + playListId + "'",
	// null, "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
	//
	// String tmp_ext = "";
	// String tmp = "";
	// String extension = "mp3";
	// cursor.moveToFirst();
	// do {
	// tmp = cursor
	// .getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
	// tmp_ext = tmp.substring(tmp.length() - 3);
	// tmp = tmp.substring(0, tmp.length() - 4);
	// if (tmp_ext.compareTo(extension) == 0
	// && !cursor
	// .getString(
	// cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
	// .contains("Ringtone")) {
	//
	// OfflineSong song = new OfflineSong();
	//
	// tmp = cursor.getString(cursor
	// .getColumnIndex(MediaStore.MediaColumns.TITLE));
	// if (tmp.isEmpty())
	// tmp = cursor.getString(cursor
	// .getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
	// if (tmp.isEmpty())
	// tmp = cursor
	// .getString(cursor
	// .getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
	// song.setTitle(tmp);
	// song.setPath(cursor.getString(cursor
	// .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
	// song.setAudioId(cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID)));
	// songList.add(song);
	// }
	// } while (cursor.moveToNext());
	// Collections.sort(songList, new TitleComparator());
	// return songList;
	// }

	// public class TitleComparator implements Comparator<OfflineSong> {
	// @Override
	// public int compare(OfflineSong o1, OfflineSong o2) {
	// Locale vietnam = new Locale("vi_VN");
	// Collator vietnamCollator = Collator.getInstance(vietnam);
	// return vietnamCollator.compare(o1.getTitle(), o2.getTitle());
	// }
	// }

}
