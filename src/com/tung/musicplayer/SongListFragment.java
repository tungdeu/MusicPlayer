package com.tung.musicplayer;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import CustomAdapter.CustomSongListAdapter;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.actionbarsherlock.widget.SearchView;
import com.tung.Entities.OfflineSong;
import com.tung.object.LoadImage;
import com.tung.screen.PlaySong;

public class SongListFragment extends Fragment {

	List<OfflineSong> Songs;
	Intent intentPlay;
	CustomSongListAdapter adapter;
	ListView lst;
	EditText txtSearch;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.simple_list, container, false);

		Cursor cursor = getActivity().getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				"LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
		Songs = new ArrayList<OfflineSong>();
		String tmp_ext = "";
		String tmp = "";
		String extension = "mp3";

		cursor.moveToFirst();
		do {
			tmp = cursor
					.getString(cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
			tmp_ext = tmp.substring(tmp.length() - 3);
			tmp = tmp.substring(0, tmp.length() - 4);
			if (tmp_ext.compareTo(extension) == 0
					&& !cursor
							.getString(
									cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
							.contains("Ringtone")) {

				OfflineSong song = new OfflineSong();

				song.setPath(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));

				tmp = cursor.getString(cursor
						.getColumnIndex(MediaStore.MediaColumns.TITLE));
				if (tmp.isEmpty())
					tmp = cursor.getString(cursor
							.getColumnIndex(MediaStore.EXTRA_MEDIA_TITLE));
				if (tmp.isEmpty())
					tmp = cursor
							.getString(cursor
									.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
				song.setTitle(tmp);

				Songs.add(song);

			}

		} while (cursor.moveToNext());
		Collections.sort(Songs, new TitleComparator());
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
				intentPlay.putExtra("path", path);

				startActivity(intentPlay);

			}

		});

		txtSearch = (EditText) view.findViewById(R.id.simple_list_searchBox);
		txtSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				adapter.setSource(Songs);
				adapter.getFilter().filter(s);
				LoadImage loadImage = new LoadImage(adapter);
				loadImage.execute(lst.getFirstVisiblePosition());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		}) ;
			
		ImageButton btnClear = (ImageButton) view.findViewById(R.id.imageButton1);
		btnClear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtSearch.setText("");
				adapter.setSource(Songs);
				adapter.notifyDataSetChanged();
				
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
