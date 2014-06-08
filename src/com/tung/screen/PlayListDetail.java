package com.tung.screen;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import CustomAdapter.CustomSongListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;
import com.tung.object.CreateList;

public class PlayListDetail extends Activity {
	public ArrayList<OfflineSong> Songs;
	public Intent intentPlay;
	public long playListId;
	public String playListName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist_detail);

		Intent intent = getIntent();
		playListId = intent.getLongExtra("id", -1);
		playListName = intent.getStringExtra("name");
		setTitle(" " + playListName);

		Cursor cursor = null;
		String[] projection = {
				MediaStore.Audio.Playlists.Members.AUDIO_ID,
				MediaStore.Audio.Playlists.Members.ARTIST,
				MediaStore.Audio.Playlists.Members.TITLE,
				MediaStore.Audio.Playlists.Members.DATA };
		cursor = this.getContentResolver().query(
				MediaStore.Audio.Playlists.Members.getContentUri("external", playListId), projection,
				MediaStore.Audio.Media.IS_MUSIC +" != 0 ", null,
				"LOWER(" + MediaStore.Audio.Playlists.Members.TITLE + ") ASC");

		Songs = new ArrayList<OfflineSong>();

		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			do {

				OfflineSong song = new OfflineSong();
				song.setAudioId(cursor.getLong(0));
				song.setTitle(cursor.getString(2));
				song.setArtist(cursor.getString(1));
				song.setPath(cursor.getString(3));

				Songs.add(song);

			} while (cursor.moveToNext());
			
		}
		Collections.sort(Songs, new TitleComparator());
		CreateList.getInstance().setSongList(Songs);
		ListView lst = (ListView) findViewById(R.id.listView1);
		CustomSongListAdapter adapter = new CustomSongListAdapter(this, Songs);
		lst.setAdapter(adapter);

		intentPlay = new Intent(this, PlaySong.class);

		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String path = Songs.get(arg2).getPath();
				long id = playListId;
				int audioID = arg2;
				intentPlay.putExtra("id", audioID);
				intentPlay.putExtra("playlistId", id);
				intentPlay.putExtra("path", path);
				startActivity(intentPlay);

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	public class TitleComparator implements Comparator<OfflineSong> {
		@Override
		public int compare(OfflineSong o1, OfflineSong o2) {
			Locale vietnam = new Locale("vi_VN");
			Collator vietnamCollator = Collator.getInstance(vietnam);
			return vietnamCollator.compare(o1.getTitle(), o2.getTitle());
		}
	}

}
