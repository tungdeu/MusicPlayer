package com.tung.object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.app.DownloadManager;
import android.media.AudioManager;
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
	public void playSong(String link){
		try {
			//String source="http://mp3.zing.vn/xml/load-song/"+link;
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.reset();
			//String url ="MjAxNCUyRjAyJTJGMTIlMkYwJTJGYSUyRjBhOGIzZWExOTIzNjc1ZjhkN2M2MWNmY2JlY2JlNTRlLm1wMyU3QzI";
			//mediaPlayer.setDataSource(songList.get(currentPos).getPath());
			mediaPlayer.setDataSource(link);
			mediaPlayer.prepare();

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

	public void playSong(int songIndex) {
		// Play song
		try {
			currentPos = songIndex;
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.reset();
			//String url = "http://mp3.zing.vn/xml/load-song/MjAxNCUyRjAyJTJGMTIlMkYwJTJGYSUyRjBhOGIzZWExOTIzNjc1ZjhkN2M2MWNmY2JlY2JlNTRlLm1wMyU3QzI";
			mediaPlayer.setDataSource(songList.get(currentPos).getPath());
			//mediaPlayer.setDataSource("http://mp3.zing.vn/xml/load-song/MjAxMSUyRjA2JTJGMTQlMkZhJTJGMSUyRmExYzJmYzFiOThjYjZmMzgxNWUyODM3ZDE2NWI5NzYzLm1wMyU3QzI");
			mediaPlayer.prepare();

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



}
