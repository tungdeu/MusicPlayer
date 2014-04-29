package com.tung.musicplayer;

import java.util.ArrayList;
import java.util.List;

import CustomAdapter.CustomPlayListAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tung.Entities.PlayListE;
import com.tung.screen.PlayListDetail;

public class PlayListFragment extends Fragment {
	List<PlayListE> Playlist;
	Intent intent;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.playlist_layout, container, false);
		String[] projection = { MediaStore.Audio.Playlists._ID,
				MediaStore.Audio.Playlists.NAME };
		Cursor cursor = getActivity().getContentResolver().query(
				MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, projection,
				null, null, MediaStore.Audio.Playlists.NAME);
		
		Playlist = new ArrayList<PlayListE>();
		
		if (cursor.getCount()!=0){
			cursor.moveToFirst();
			do {
				
				long playListId = cursor.getLong(0);
				String playListName = cursor.getString(1);
				
				PlayListE plist = new PlayListE();
				plist.setPlayListId(playListId);
				plist.setPlayListName(playListName);
				
				Playlist.add(plist);
				
			}while (cursor.moveToNext());
		}
		intent = new Intent(getActivity(),PlayListDetail.class);
		ListView lst = (ListView)view.findViewById(R.id.playlist_layout_lstView);
		CustomPlayListAdapter adapter = new CustomPlayListAdapter(getActivity(), Playlist);
		lst.setAdapter(adapter);

		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String playListName = Playlist.get(arg2).getPlayListName();
				intent.putExtra("name", playListName);
				startActivity(intent);
				
			}
		});
		return view;
	}

}
