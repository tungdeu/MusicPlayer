package com.tung.screen;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

import CustomAdapter.CustomListSongListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tung.Entities.OfflineSong;
import com.tung.musicplayer.R;
import com.tung.object.CreateList;

public class PlayListDetail extends Activity {
	public ArrayList<OfflineSong> Songs;
	public Intent intentPlay;
	public long playListId;
	public String playListName;
	public boolean isEdit = false;
	private TextView btn_edit;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist_detail);
		btn_edit = (TextView)findViewById(R.id.playlist_detail_btnEdit);
		Intent intent = getIntent();
		playListId = intent.getLongExtra("id", -1);
		playListName = intent.getStringExtra("name");
		setTitle(" " + playListId);

		Cursor cursor = null;
		String[] projection = {
				MediaStore.Audio.Playlists.Members.AUDIO_ID,
				MediaStore.Audio.Playlists.Members.ARTIST,
				MediaStore.Audio.Playlists.Members.TITLE,
				MediaStore.Audio.Playlists.Members.DATA };
		cursor = this.getContentResolver().query(
				MediaStore.Audio.Playlists.Members.getContentUri("external", playListId), projection,
				MediaStore.Audio.Media.IS_MUSIC +" != 0 ", null,
				null);

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
		
		CreateList.getInstance().setSongList(Songs);
		final ListView lst = (ListView) findViewById(R.id.listView1);
		final CustomListSongListAdapter adapter = new CustomListSongListAdapter(this, Songs,isEdit,playListId);
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
				CreateList.getInstance().playSong(audioID);

			}

		});
		
		btn_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isEdit){
					isEdit=false;
					btn_edit.setText("OK");
					CustomListSongListAdapter adapter = new CustomListSongListAdapter(getApplicationContext(), Songs,isEdit,playListId);
					lst.setAdapter(adapter);
					}
				else {
					isEdit=true;
					btn_edit.setText("Edit");
					CustomListSongListAdapter adapter = new CustomListSongListAdapter(getApplicationContext(), Songs,isEdit,playListId);
					lst.setAdapter(adapter);
				}
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
