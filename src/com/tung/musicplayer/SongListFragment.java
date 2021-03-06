package com.tung.musicplayer;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import CustomAdapter.CustomSongListAdapter;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.tung.Entities.OfflineSong;
import com.tung.object.CreateList;
import com.tung.object.LoadImage;
import com.tung.screen.PlaySong;

public class SongListFragment extends Fragment {

	ArrayList<OfflineSong> Songs;
	Intent intentPlay;
	CustomSongListAdapter adapter;
	ListView lst;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.simple_list, container, false);

		String[] projection = { MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.ARTIST, 
				MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media._ID
				};
		Cursor cursor = getActivity().getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, 
				MediaStore.Audio.Media.IS_MUSIC +" != 0 ", null,
				"LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
		Songs = new ArrayList<OfflineSong>();

		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			do {
				OfflineSong song = new OfflineSong();
				song.setPath(cursor.getString(0));
				song.setArtist(cursor.getString(1));
				song.setAlbum(cursor.getString(2));
				song.setTitle(cursor.getString(3));
				song.setAudioId(cursor.getLong(4));
				Songs.add(song);

			} while (cursor.moveToNext());
		}

		Collections.sort(Songs, new TitleComparator());
		CreateList.getInstance().setSongList(Songs);
		lst = (ListView) view.findViewById(R.id.listView1);
		adapter = new CustomSongListAdapter(getActivity(), Songs);
		lst.setAdapter(adapter);

		intentPlay = new Intent(getActivity(), PlaySong.class);

		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String path = Songs.get(arg2).getPath();
				int audioId = arg2;
				CreateList.getInstance().setCurrentPos(audioId);
				intentPlay.putExtra("id", audioId);
				intentPlay.putExtra("path", path);
				//intentPlay.putExtra("flag", 1);
				startActivity(intentPlay);
				CreateList.getInstance().playSong(audioId);
				
			}

		});

		return view;

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
