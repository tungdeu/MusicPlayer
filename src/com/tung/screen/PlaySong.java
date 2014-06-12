package com.tung.screen;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;
import com.tung.object.BitmapProcess;
import com.tung.object.CreateList;
import com.tung.object.PlayBackService;
import com.tung.object.Ultilities;

public class PlaySong extends Activity implements OnCompletionListener,
		SeekBar.OnSeekBarChangeListener {

	private ImageView btnPlay;
	private ImageView btnNext;
	private ImageView btnPrevious;
	private ImageView btnShuffle;
	private ImageView btnRepeat;
	private SeekBar skbarSongProgress;
	private TextView txtFirst;
	private TextView txtLast;
	private ImageView imgCover;
	private TextView txtTitle;
	private TextView txtArtist;
	private ImageView btndownload;
	Ultilities ulti;
	private Handler mHandler = new Handler();
	private int currentSongIndex = 0;
	private boolean isShuffle = false;
	private boolean isRepeat = false;
	private ArrayList<OfflineSong> songsList = new ArrayList<OfflineSong>();
	private Intent intentPlay;
	private int listFlag;
	private String album;
	private String artist;
	private long playListId = 1;
	private PlayBackService pbServ;
	private boolean musicBound = false;
	private Intent pIntent;
	private int songPos;
	private MediaPlayer mp;
public String Download_path ="http://mp3.zing.vn/xml/load-song/MjAxMSUyRjA2JTJGMTQlMkZhJTJGMSUyRmExYzJmYzFiOThjYjZmMzgxNWUyODM3ZDE2NWI5NzYzLm1wMyU3QzI";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_song);
		btnPlay = (ImageView) findViewById(R.id.media_control_play);
		btnNext = (ImageView) findViewById(R.id.media_control_next);
		btnPrevious = (ImageView) findViewById(R.id.media_control_previous);
		btnShuffle = (ImageView) findViewById(R.id.media_control_shuffle);
		btnRepeat = (ImageView) findViewById(R.id.media_control_repeat);
		skbarSongProgress = (SeekBar) findViewById(R.id.media_control_seekbar);
		txtFirst = (TextView) findViewById(R.id.media_control_txt_first);
		txtLast = (TextView) findViewById(R.id.media_control_txt_second);
		imgCover = (ImageView) findViewById(R.id.play_song_imgView);
		txtTitle = (TextView) findViewById(R.id.play_song_title);
		txtArtist = (TextView)findViewById(R.id.play_song_artist);
		//btndownload =(ImageView)findViewById(R.id.media_control_download);
		intentPlay = getIntent();
		listFlag = intentPlay.getIntExtra("flag", 1);
		album = intentPlay.getStringExtra("album");
		artist = intentPlay.getStringExtra("artist");
		playListId = intentPlay.getLongExtra("playlistId", 1);
		ulti = new Ultilities();
		
		songsList = CreateList.getInstance().getSongList();
		reUseView();

		btnPlay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// check for already playing
				if (CreateList.getInstance().getMediaPlayer().isPlaying()) {
					if (CreateList.getInstance().getMediaPlayer() != null) {
						CreateList.getInstance().getMediaPlayer().pause();
						// Changing button image to play button
						btnPlay.setImageResource(R.drawable.play_white);
					}
				} else {
					// Resume song
					if (CreateList.getInstance().getMediaPlayer() != null) {
						CreateList.getInstance().getMediaPlayer().start();
						mHandler.postDelayed(mUpdateTimeTask, 1000);
						// Changing button image to pause button
						btnPlay.setImageResource(R.drawable.pause_white);
					}
				}

			}
		});


		btnNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// check if next song is there or not
				if (songPos < (songsList.size() - 1)) {
					songPos = songPos + 1;					
					CreateList.getInstance().playSong(songPos);
					
					reUseView();
				} else {
					// play first song
					songPos = 0;
					CreateList.getInstance().playSong(songPos);
					
					reUseView();
				}

			}
		});

		/**
		 * Back button click event Plays previous song by currentSongIndex - 1
		 * */
		btnPrevious.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (songPos > 0) {
					songPos = songPos - 1;
					CreateList.getInstance().playSong(songPos);
					
					reUseView();
				} else {
					// play last song
					songPos = songsList.size() - 1;
					CreateList.getInstance().playSong(songsList.size());
					
					reUseView();
				}

			}
		});

		/**
		 * Button Click event for Repeat button Enables repeat flag to true
		 * */
		btnRepeat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isRepeat) {
					isRepeat = false;
					Toast.makeText(getApplicationContext(), "Repeat is OFF",
							Toast.LENGTH_SHORT).show();
					btnRepeat.setImageResource(R.drawable.repeat_white);
				} else {
					// make repeat to true
					isRepeat = true;
					Toast.makeText(getApplicationContext(), "Repeat is ON",
							Toast.LENGTH_SHORT).show();
					// make shuffle to false
					isShuffle = false;
					btnRepeat.setImageResource(R.drawable.repeat_blue);
					btnShuffle.setImageResource(R.drawable.shuffle_white);
				}
			}
		});

		/**
		 * Button Click event for Shuffle button Enables shuffle flag to true
		 * */
		btnShuffle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isShuffle) {
					isShuffle = false;
					Toast.makeText(getApplicationContext(), "Shuffle is OFF",
							Toast.LENGTH_SHORT).show();
					btnShuffle.setImageResource(R.drawable.shuffle_white);
				} else {
					// make repeat to true
					isShuffle = true;
					Toast.makeText(getApplicationContext(), "Shuffle is ON",
							Toast.LENGTH_SHORT).show();
					// make shuffle to false
					isRepeat = false;
					btnShuffle.setImageResource(R.drawable.shuffle_blue);
					// btnRepeat.setImageResource(R.drawable.btn_repeat);
				}
			}
		});

		/**
		 * Button Click event for Play list click event Launches list activity
		 * which displays list of songs
		 * */
		// btnPlaylist.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent i = new Intent(getApplicationContext(),
		// PlayListActivity.class);
		// startActivityForResult(i, 100);
		// }
		// });

//		btndownload.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				if(listFlag==9){
//				SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(PlaySong.this);
//				DownloadManager dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//				
//			    Uri Download_Uri = Uri.parse(Download_path);
//			    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
//			    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//			    request.setAllowedOverRoaming(false);
//			    request.setTitle(intentPlay.getStringExtra("title"));
//			    request.setDestinationInExternalFilesDir(PlaySong.this,Environment.DIRECTORY_DOWNLOADS,intentPlay.getStringExtra("title") + ".mp3");
//			    dm.enqueue(request);
//				}
//			}
//		});
	}

	/**
	 * Receiving song index from playlist view and play the song
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 100) {
			currentSongIndex = data.getExtras().getInt("songIndex");
			// play selected song
			CreateList.getInstance().playSong(currentSongIndex);
		}

	}


	public void updateProgressBar() {
		if(CreateList.getInstance().isWaiter())
			reUseView();
		mHandler.postDelayed(mUpdateTimeTask, 1000);
	}


	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			if (CreateList.getInstance().getMediaPlayer().isPlaying()) {
				long totalDuration = CreateList.getInstance().getMediaPlayer()
						.getDuration();
				long currentDuration = CreateList.getInstance()
						.getMediaPlayer().getCurrentPosition();

				// Displaying Total Duration time
				txtLast.setText("" + ulti.milliSecondsToTimer(totalDuration));
				// Displaying time completed playing
				txtFirst.setText("" + ulti.milliSecondsToTimer(currentDuration));

				// Updating progress bar
				int progress = (int) (ulti.getProgressPercentage(
						currentDuration, totalDuration));
				// Log.d("Progress", ""+progress);
				skbarSongProgress.setProgress(progress);

				// Running this thread after 100 milliseconds
				mHandler.postDelayed(this, 1000);
			}
		}
	};

	/**
     *
     * */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {

	}

	/**
	 * When user starts moving the progress handler
	 * */
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// remove message Handler from updating progress bar
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	/**
	 * When user stops moving the progress hanlder
	 * */
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = CreateList.getInstance().getMediaPlayer()
				.getDuration();
		int currentPosition = ulti.progressToTimer(seekBar.getProgress(),
				totalDuration);

		// forward or backward to certain seconds
		CreateList.getInstance().getMediaPlayer().seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();
	}

	/**
	 * On Song Playing completed if repeat is ON play same song again if shuffle
	 * is ON play random song
	 * */
	@Override
	public void onCompletion(MediaPlayer arg0) {

		// check for repeat is ON or OFF
		if (isRepeat) {
			// repeat is on play same song again
			CreateList.getInstance().playSong(currentSongIndex);
		} else if (isShuffle) {
			// shuffle is on - play a random song
			Random rand = new Random();
			currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
			CreateList.getInstance().playSong(currentSongIndex);
		} else if (currentSongIndex < (songsList.size() - 1)) {
			// no repeat or shuffle ON - play next song

			CreateList.getInstance().playSong(currentSongIndex + 1);
			currentSongIndex = currentSongIndex + 1;
		} else {
			// play first song
			CreateList.getInstance().playSong(0);
			currentSongIndex = 0;
		}
		reUseView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mUpdateTimeTask);
		// CreateList.getInstance().getMediaPlayer().release();
		finish();
	}
	void reUseView(){

		CreateList.getInstance().setWaiter(false);
		songPos = CreateList.getInstance().getCurrentPos();
		if(listFlag==9){
			txtTitle.setText(intentPlay.getStringExtra("title"));
			txtArtist.setText("");
			imgCover.setImageResource(R.drawable.default_artwork);
		}
		else{
			txtTitle.setText(songsList.get(songPos).getTitle());
			txtArtist.setText(songsList.get(songPos).getArtist());
			MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
			metaRetriver.setDataSource(songsList.get(songPos).getPath());
			try {
				byte[] imageData = metaRetriver.getEmbeddedPicture();
				if(imageData != null){
				Bitmap bitmap = BitmapProcess.decodeSampledBitmap(imageData, 500,
						500);
				imgCover.setImageBitmap(bitmap);}
				else
				imgCover.setImageResource(R.drawable.default_artwork);
			} catch (Exception e) {
				e.printStackTrace();
			}
			;
		}

		skbarSongProgress.setOnSeekBarChangeListener(this); // Important
		CreateList.getInstance().getMediaPlayer().setOnCompletionListener(this); // Important

		// By default play first song
		// set Progress bar values
		skbarSongProgress.setProgress(0);
		skbarSongProgress.setMax(100);

		// Updating progress bar
		updateProgressBar();

	}

}