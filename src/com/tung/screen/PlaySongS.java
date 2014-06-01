package com.tung.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;
import com.tung.object.PlayBackService;
import com.tung.object.PlayBackService.MusicBinder;

public class PlaySongS extends Activity implements MediaPlayerControl{

	//song list variables
	private ArrayList<OfflineSong> songList;
	private ListView songView;

	//service
	private PlayBackService musicSrv;
	private Intent playIntent;
	//binding
	private boolean musicBound=false;

	//controller
	private MusicController controller;

	//activity and playback pause flags
	private boolean paused=false, playbackPaused=false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_song);

		//retrieve list view
		songView = (ListView)findViewById(R.id.song_list);
		//instantiate list
		songList = new ArrayList<OfflineSong>();
		//get songs from device
		getSongList();
		//sort alphabetically by title
		Collections.sort(songList, new Comparator<OfflineSong>(){
			public int compare(Song a, Song b){
				return a.getTitle().compareTo(b.getTitle());
			}
		});
		//create and set adapter
		SongAdapter songAdt = new SongAdapter(this, songList);
		songView.setAdapter(songAdt);

		//setup controller
		setController();
	}
	
	private ServiceConnection musicConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MusicBinder binder = (MusicBinder)service;
			//get service
			musicSrv = binder.getService();
			//pass list
			musicSrv.setList(songList);
			musicBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			musicBound = false;
		}
	};
	
	protected void onStart() {
		super.onStart();
		if(playIntent==null){
			playIntent = new Intent(this, PlayBackService.class);
			bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
			startService(playIntent);
		}
	}
	
	public void songPicked(View view){
		musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
		musicSrv.playSong();
		if(playbackPaused){
			setController();
			playbackPaused=false;
		}
		controller.show(0);
	}
	
	
	
	private void setController() {
		// TODO Auto-generated method stub
		controller = new MusicController(this);
		//set previous and next button listeners
		controller.setPrevNextListeners(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playNext();
			}

		}, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playPrev();
			}
		});
		//set and show
		controller.setMediaPlayer(this);
		controller.setAnchorView(findViewById(R.id.song_list));
		controller.setEnabled(true);
	}
	private void playNext(){
		musicSrv.playNext();
		if(playbackPaused){ 
			setController();
			playbackPaused=false;
		}
		controller.show(0);
	}

	private void playPrev(){
		musicSrv.playPrev();
		if(playbackPaused){
			setController();
			playbackPaused=false;
		}
		controller.show(0);
	}
	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getAudioSessionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		if(musicSrv!=null && musicBound && musicSrv.isPlaying())
			return musicSrv.getPos();
		else return 0;
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		if(musicSrv!=null && musicBound && musicSrv.isPlaying())
			return musicSrv.getDur();
		else return 0;
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		if(musicSrv!=null && musicBound)
			return musicSrv.isPlaying();
		return false;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		playbackPaused=true;
		musicSrv.pausePlayer();
	}

	@Override
	public void seekTo(int arg0) {
		// TODO Auto-generated method stub
		musicSrv.seek(arg0);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		musicSrv.go();
	}

	@Override
	protected void onPause(){
		super.onPause();
		paused=true;
	}

	@Override
	protected void onResume(){
		super.onResume();
		if(paused){
			setController();
			paused=false;
		}
	}

	@Override
	protected void onStop() {
		controller.hide();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		stopService(playIntent);
		musicSrv=null;
		super.onDestroy();
	}
}
