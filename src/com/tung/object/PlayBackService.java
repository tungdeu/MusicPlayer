package com.tung.object;

import java.util.ArrayList;
import java.util.Random;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;
import com.tung.screen.PlaySong;

public class PlayBackService extends Service implements
		MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
		MediaPlayer.OnCompletionListener {

	private MediaPlayer media;
	private ArrayList<OfflineSong> song;
	private int songPos;
	private final IBinder musicBind = new MusicBinder();
	private String songTitle = "";
	private static final int NOTIFY_ID = 1;
	private boolean shuffle = false;
	private boolean repeat = false;
	private Random rand;

	public void onCreate() {
		super.onCreate();
		songPos = 0;
		rand = new Random();
		media = new MediaPlayer();
		initMusicPlayer();
	}

	private void initMusicPlayer() {
		// TODO Auto-generated method stub
		media.setWakeMode(getApplicationContext(),
				PowerManager.PARTIAL_WAKE_LOCK);
		media.setAudioStreamType(AudioManager.STREAM_MUSIC);
		media.setOnPreparedListener(this);
		media.setOnCompletionListener(this);
		media.setOnErrorListener(this);
	}

	public void setList(ArrayList<OfflineSong> theSongs) {
		song = theSongs;
	}

	public class MusicBinder extends Binder {
		public PlayBackService getService() {
			return PlayBackService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return musicBind;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		media.stop();
		media.release();
		return false;
	}

	public void playSong() {
		// play
		media.reset();
		// get song
		OfflineSong playSong = song.get(songPos);
		// get title
		songTitle = playSong.getTitle();
		// get id
		long currSong = playSong.getAudioId();
		// set uri
		Uri trackUri = ContentUris.withAppendedId(
				android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				currSong);
		// set the data source
		try {
			media.setDataSource(getApplicationContext(), trackUri);
		} catch (Exception e) {
			Log.e("MUSIC SERVICE", "Error setting data source", e);
		}
		media.prepareAsync();
	}

	public void setSong(int songIndex) {
		songPos = songIndex;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		if (media.getCurrentPosition() > 0) {
			mp.reset();
			playNext();
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int arg1, int arg2) {
		// TODO Auto-generated method stub
		Log.v("MUSIC PLAYER", "Playback Error");
		mp.reset();
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		// start playback
		mp.start();
		// notification
		Intent notIntent = new Intent(this, PlaySong.class);
		notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendInt = PendingIntent.getActivity(this, 0, notIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder builder = new Notification.Builder(this);

		builder.setContentIntent(pendInt).setSmallIcon(R.drawable.play)
				.setTicker(songTitle).setOngoing(true)
				.setContentTitle("Playing").setContentText(songTitle);
		Notification not = builder.build();
		startForeground(NOTIFY_ID, not);
	}

	public int getPos() {
		return media.getCurrentPosition();
	}

	public int getDur() {
		return media.getDuration();
	}

	public boolean isPlaying() {
		return media.isPlaying();
	}

	public void pausePlayer() {
		media.pause();
	}

	public void seek(int posn) {
		media.seekTo(posn);
	}

	public void go() {
		media.start();
	}

	public void playPrev() {
		songPos--;
		if (songPos < 0)
			songPos = song.size() - 1;
		playSong();
	}

	public void playNext() {
		if (repeat) {
			int newSong = songPos;
			songPos = newSong;
		} 
		else if (shuffle) {
			int newSong = songPos;
			while (newSong == songPos) {
				newSong = rand.nextInt(song.size());
			}
			songPos = newSong;
			}
			else {
				songPos++;
				if (songPos >= song.size())
				songPos = 0;
		}
		playSong();
	}

	@Override
	public void onDestroy() {
		stopForeground(true);
	}

	public void setShuffle() {
		if (shuffle)
			shuffle = false;
		else {
			shuffle = true;
			repeat = false;
		}
	}

	public void setRepeat() {
		if (repeat)
			repeat = false;
		else {
			repeat = true;
			shuffle = false;
		}
	}

}
