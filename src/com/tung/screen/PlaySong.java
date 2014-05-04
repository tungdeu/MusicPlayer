package com.tung.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.tung.musicplayer.R;
import com.tung.object.CreateList;
import com.tung.object.Ultilities;

public class PlaySong extends Activity implements OnSeekBarChangeListener,
		OnCompletionListener {

	private ImageButton btnPlay;
	private ImageButton btnNext;
	private ImageButton btnPrevious;
	private ImageButton btnShuffle;
	private ImageButton btnRepeat;
	private SeekBar skbarSongProgress;
	private TextView txtFirst;
	private TextView txtLast;
	private ImageView imgCover;
	private MediaPlayer mp;
	Ultilities ulti;
	private Handler mHandler = new Handler();
	private int currentSongIndex = 0;
	private boolean isShuffle = false;
	private boolean isRepeat = false;
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private Intent intentPlay;
	private int listFlag;
	private String album;
	private String artist;
	private long playListId = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_song);
		btnPlay = (ImageButton) findViewById(R.id.media_control_play);
		btnNext = (ImageButton) findViewById(R.id.media_control_next);
		btnPrevious = (ImageButton) findViewById(R.id.media_control_previous);
		btnShuffle = (ImageButton) findViewById(R.id.media_control_shuffle);
		btnRepeat = (ImageButton) findViewById(R.id.media_control_repeat);
		skbarSongProgress = (SeekBar) findViewById(R.id.media_control_seekbar);
		txtFirst = (TextView) findViewById(R.id.media_control_txt_first);
		txtLast = (TextView) findViewById(R.id.media_control_txt_second);
		imgCover = (ImageView) findViewById(R.id.play_song_imgView);

		intentPlay = getIntent();
		listFlag = intentPlay.getIntExtra("flag", 1);
		album = intentPlay.getStringExtra("album");
		artist = intentPlay.getStringExtra("artist");
		playListId = intentPlay.getLongExtra("playlistId", 1);
				
		mp = new MediaPlayer();
		ulti = new Ultilities();
		CreateList creatList = new CreateList(this);
		
		switch (listFlag) {
		case 1:
			songsList = creatList.CreateAllSongList();
			break;
		case 2:
			songsList = creatList.CreateSongListFromArtist(artist);
			break;
		case 3:
			songsList = creatList.CreateSongListFromAlbum(album);
			break;
		case 4:
			songsList = creatList.CreateSongListFromPlayList(playListId);
		}
		
		skbarSongProgress.setOnSeekBarChangeListener(this);
		mp.setOnCompletionListener(this);

		btnPlay.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mp.isPlaying()) {
					if (mp != null) {
						mp.pause();
						// Change image button

					}
				} else {
					if (mp != null) {
						mp.start();
						// Change Image button

					}
				}
			}
		});

		 btnNext.setOnClickListener(new OnClickListener() {
		
		 @Override
		 public void onClick(View v) {
		 // TODO Auto-generated method stub
		 if (currentSongIndex < (songsList.size() - 1)) {
		 playSong(currentSongIndex + 1);
		 currentSongIndex = currentSongIndex + 1;
		 } else {
		 // play first song
		 playSong(0);
		 currentSongIndex = 0;
		 }
		
		 }
		 });

		 btnPrevious.setOnClickListener(new OnClickListener() {
		
		 @Override
		 public void onClick(View v) {
		 // TODO Auto-generated method stub
		 if (currentSongIndex > 0) {
		 playSong(currentSongIndex - 1);
		 currentSongIndex = currentSongIndex - 1;
		 } else {
		 // play last song
		 playSong(songsList.size() - 1);
		 currentSongIndex = songsList.size() - 1;
		 }
		 }
		 });

		btnShuffle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isShuffle) {
					isShuffle = false;
					// change image unfocus shuffle
					// btnShuffle.setImageResource(R.drawable.btn_shuffle);
				} else {
					// make repeat to true
					isShuffle = true;
					// Toast.makeText(getApplicationContext(), "Shuffle is ON",
					// Toast.LENGTH_SHORT).show();
					// make shuffle to false
					isRepeat = false;
					// change image : focus shuffle unfocus repeat
					// btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
					// btnRepeat.setImageResource(R.drawable.btn_repeat);
				}
			}
		});

		btnRepeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isRepeat) {
					isRepeat = false;
					// change image : unfocus repeat
					// btnRepeat.setImageResource(R.drawable.btn_repeat);
				} else {
					// make repeat to true
					isRepeat = true;

					// make shuffle to false
					isShuffle = false;
					// change image: focus repeat . unfocus shuffle
					// btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
					// btnShuffle.setImageResource(R.drawable.btn_shuffle);
				}
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 if (resultCode == 100) {
		 currentSongIndex = data.getExtras().getInt("songIndex");
		 // play selected song
		 playSong(currentSongIndex);
		 }
		
	}

	 public void playSong(int songIndex) {
	 // Play song
	 try {
	 mp.reset();
	 mp.setDataSource(songsList.get(songIndex).get("songPath"));
	 mp.prepare();
	 mp.start();
	 // Displaying Song title
	 //String songTitle = songsList.get(songIndex).get("songTitle");
	 //songTitleLabel.setText(songTitle);
	
	 // Changing Button Image to pause image
	 //btnPlay.setImageResource(R.drawable.btn_pause);
	
	 // set Progress bar values
	 skbarSongProgress.setProgress(0);
	 skbarSongProgress.setMax(100);
	
	 // Updating progress bar
	 updateProgressBar();
	 } catch (IllegalArgumentException e) {
	 e.printStackTrace();
	 } catch (IllegalStateException e) {
	 e.printStackTrace();
	 } catch (IOException e) {
	 e.printStackTrace();
	 }
	 }

	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			long totalDuration = mp.getDuration();
			long currentDuration = mp.getCurrentPosition();

			// Displaying Total Duration time
			txtLast.setText("" + ulti.milliSecondsToTimer(totalDuration));
			// Displaying time completed playing
			txtFirst.setText("" + ulti.milliSecondsToTimer(currentDuration));

			// Updating progress bar
			int progress = (int) (ulti.getProgressPercentage(currentDuration,
					totalDuration));
			// Log.d("Progress", ""+progress);
			skbarSongProgress.setProgress(progress);

			// Running this thread after 100 milliseconds
			mHandler.postDelayed(this, 100);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_song, menu);
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = mp.getDuration();
		int currentPosition = ulti.progressToTimer(seekBar.getProgress(),
				totalDuration);

		// forward or backward to certain seconds
		mp.seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		 // TODO Auto-generated method stub
		 // check for repeat is ON or OFF
		 if (isRepeat) {
		 // repeat is on play same song again
		 playSong(currentSongIndex);
		 } else if (isShuffle) {
		 // shuffle is on - play a random song
		 Random rand = new Random();
		 currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
		 playSong(currentSongIndex);
		 } else {
		 // no repeat or shuffle ON - play next song
		 if (currentSongIndex < (songsList.size() - 1)) {
		 playSong(currentSongIndex + 1);
		 currentSongIndex = currentSongIndex + 1;
		 } else {
		 // play first song
		 playSong(0);
		 currentSongIndex = 0;
		 }
		 }
	}

	public void onDestroy() {
		super.onDestroy();
		mp.release();
	}

}
