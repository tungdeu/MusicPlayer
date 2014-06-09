package com.tung.object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.tung.Entities.OfflineSong;

public class CreateList {
	private static CreateList mInstance = null;

	private ArrayList<OfflineSong> songList = new ArrayList<OfflineSong>();
	private MediaPlayer mediaPlayer = new MediaPlayer();
	private int currentPos;
	private boolean isRepeat = false;
	private boolean isShuffle = false;
	private boolean waiter = false;

	public boolean isWaiter() {
		return waiter;
	}

	public void setWaiter(boolean waiter) {
		this.waiter = waiter;
	}

	public boolean isRepeat() {
		return isRepeat;
	}

	public void setRepeat(boolean isRepeat) {
		this.isRepeat = isRepeat;
	}

	public boolean isShuffle() {
		return isShuffle;
	}

	public void setShuffle(boolean isShuffle) {
		this.isShuffle = isShuffle;
	}

	public void playSong(int songIndex) {
		// Play song
		try {
			currentPos = songIndex;
			mediaPlayer.reset();

			mediaPlayer.setDataSource(songList.get(currentPos).getPath());
			mediaPlayer.prepare();
			// currentPos = songIndex;
			// Displaying Song title
			// String songTitle =
			// CreateList.getInstance().getSongList().get(songIndex).getTitle();
			// songTitleLabel.setText(songTitle);

			// Changing Button Image to pause image
			// btnPlay.setImageResource(R.drawable.btn_pause);

			// set Progress bar values

			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					waiter = true;
					if (isRepeat) {
						// repeat is on play same song again
						playSong(currentPos);
					} else if (isShuffle) {
						// shuffle is on - play a random song
						Random rand = new Random();
						currentPos = rand.nextInt((songList.size() - 1) - 0 + 1) + 0;
						playSong(currentPos);
					} else if (currentPos < (songList.size() - 1)) {
						// no repeat or shuffle ON - play next song
						currentPos = currentPos + 1;
						playSong(currentPos);

					} else {
						// play first song
						currentPos = 0;
						playSong(0);

					}
				}
			});

			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getCurrentPos() {
		return currentPos;
	}

	public void setCurrentPos(int currentPos) {
		this.currentPos = currentPos;
	}

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
