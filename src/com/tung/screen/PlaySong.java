package com.tung.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;
import com.tung.object.CreateList;
import com.tung.object.PlayBackService;
import com.tung.object.Ultilities;

public class PlaySong extends Activity implements OnCompletionListener, SeekBar.OnSeekBarChangeListener
		 {

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
		songPos = intentPlay.getIntExtra("id",2);
		mp = new MediaPlayer();
		ulti = new Ultilities();

		switch (listFlag) {
		case 1:
			songsList = CreateList.getInstance().CreateAllSongList(this);
			break;
		case 2:
			songsList = CreateList.getInstance().CreateSongListFromArtist(artist,this);
			break;
		case 3:
			songsList = CreateList.getInstance().CreateSongListFromAlbum(album,this);
			break;
		case 4:
			songsList = CreateList.getInstance().CreateSongListFromPlayList(playListId,this);
		}

		skbarSongProgress.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important
 

        // By default play first song
        playSong(songPos);
 
        /**
         * Play button click event
         * plays a song and changes button to pause image
         * pauses a song and changes button to play image
         * */
        btnPlay.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // check for already playing
                if(mp.isPlaying()){
                    if(mp!=null){
                        mp.pause();
                        // Changing button image to play button
                       // btnPlay.setImageResource(R.drawable.btn_play);
                    }
                }else{
                    // Resume song
                    if(mp!=null){
                        mp.start();
                        // Changing button image to pause button
                       // btnPlay.setImageResource(R.drawable.btn_pause);
                    }
                }
 
            }
        });
 
        /**
         * Forward button click event
         * Forwards song specified seconds
         * */

 
        /**
         * Next button click event
         * Plays next song by taking currentSongIndex + 1
         * */
        btnNext.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // check if next song is there or not
                if(currentSongIndex < (songsList.size() - 1)){
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                }else{
                    // play first song
                    playSong(0);
                    currentSongIndex = 0;
                }
 
            }
        });
 
        /**
         * Back button click event
         * Plays previous song by currentSongIndex - 1
         * */
        btnPrevious.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                if(currentSongIndex > 0){
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                }else{
                    // play last song
                    playSong(songsList.size() - 1);
                    currentSongIndex = songsList.size() - 1;
                }
 
            }
        });
 
        /**
         * Button Click event for Repeat button
         * Enables repeat flag to true
         * */
        btnRepeat.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                if(isRepeat){
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                   // btnRepeat.setImageResource(R.drawable.btn_repeat);
                }else{
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isShuffle = false;
                //    btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                //    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }
            }
        });
 
        /**
         * Button Click event for Shuffle button
         * Enables shuffle flag to true
         * */
        btnShuffle.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                if(isShuffle){
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                 //   btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }else{
                    // make repeat to true
                    isShuffle= true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                  //  btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                  //  btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
            }
        });
 
        /**
         * Button Click event for Play list click event
         * Launches list activity which displays list of songs
         * */
//        btnPlaylist.setOnClickListener(new View.OnClickListener() {
// 
//            @Override
//            public void onClick(View arg0) {
//                Intent i = new Intent(getApplicationContext(), PlayListActivity.class);
//                startActivityForResult(i, 100);
//            }
//        });
 
    }
 
    /**
     * Receiving song index from playlist view
     * and play the song
     * */
    @Override
    protected void onActivityResult(int requestCode,
                                     int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
             currentSongIndex = data.getExtras().getInt("songIndex");
             // play selected song
             playSong(currentSongIndex);
        }
 
    }
 
    /**
     * Function to play a song
     * @param songIndex - index of song
     * */
    public void  playSong(int songIndex){
        // Play song
        try {
            mp.reset();
            mp.setDataSource(songsList.get(songIndex).getPath());
            mp.prepare();
            mp.start();
            // Displaying Song title
            String songTitle = songsList.get(songIndex).getTitle();
      //      songTitleLabel.setText(songTitle);
 
            // Changing Button Image to pause image
           // btnPlay.setImageResource(R.drawable.btn_pause);
 
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
 
    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }   
 
    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
           public void run() {
               long totalDuration = mp.getDuration();
               long currentDuration = mp.getCurrentPosition();
 
               // Displaying Total Duration time
               txtLast.setText(""+ulti.milliSecondsToTimer(totalDuration));
               // Displaying time completed playing
               txtFirst.setText(""+ulti.milliSecondsToTimer(currentDuration));
 
               // Updating progress bar
               int progress = (int)(ulti.getProgressPercentage(currentDuration, totalDuration));
               //Log.d("Progress", ""+progress);
               skbarSongProgress.setProgress(progress);
 
               // Running this thread after 100 milliseconds
               mHandler.postDelayed(this, 100);
           }
        };
 
    /**
     *
     * */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
 
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
        int totalDuration = mp.getDuration();
        int currentPosition = ulti.progressToTimer(seekBar.getProgress(), totalDuration);
 
        // forward or backward to certain seconds
        mp.seekTo(currentPosition);
 
        // update timer progress again
        updateProgressBar();
    }
 
    /**
     * On Song Playing completed
     * if repeat is ON play same song again
     * if shuffle is ON play random song
     * */
    @Override
    public void onCompletion(MediaPlayer arg0) {
 
        // check for repeat is ON or OFF
        if(isRepeat){
            // repeat is on play same song again
            playSong(currentSongIndex);
        } else if(isShuffle){
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else{
            // no repeat or shuffle ON - play next song
            if(currentSongIndex < (songsList.size() - 1)){
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            }else{
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }
 
    @Override
     public void onDestroy(){
     super.onDestroy();
//     mHandler.removeCallbacks(mUpdateTimeTask);
     mp.release();
//        finish();
     }
 
}