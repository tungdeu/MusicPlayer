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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tung.Entities.PlayListE;
import com.tung.screen.PlayListDetail;
import com.tung.screen.PlaySong;

public class PlayListFragment extends Fragment {
	PlayListFragment fragment;
	List<PlayListE> Playlist;
	Intent intent;
	TextView txtAdd;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragment = this;
		 View view = inflater
				.inflate(R.layout.playlist_layout, container, false);
		txtAdd = (TextView)view.findViewById(R.id.playlist_layout_txtView);

		final String[] projection = { MediaStore.Audio.Playlists._ID,
				MediaStore.Audio.Playlists.NAME };
		final Cursor cursor = getActivity().getContentResolver().query(
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
		final ListView lst = (ListView)view.findViewById(R.id.playlist_layout_lstView);
		final CustomPlayListAdapter adapter = new CustomPlayListAdapter(getActivity(), Playlist);
		lst.setAdapter(adapter);

		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				long playListId = Playlist.get(arg2).getPlayListId();
				String name = Playlist.get(arg2).getPlayListName();
				intent.putExtra("name", name);
				intent.putExtra("id", playListId);
				startActivity(intent);
				
			}
		});
		
		txtAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(getActivity(),PlaySong.class);
				startActivity(intent1);
				// TODO Auto-generated method stub
//				View view1 = getActivity().getLayoutInflater().inflate(
//						R.layout.dialog_add_playlist, null);
//				AlertDialog.Builder dialogBuiler = new Builder(getActivity());
//				dialogBuiler.setTitle("New Playlist");
//				dialogBuiler.setView(view1);
//				dialogBuiler.setCancelable(true);
//				final EditText input_txt = (EditText)view1.findViewById(R.id.dialog_add_playlist_txt);
//				dialogBuiler.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						if(!input_txt.getText().toString().isEmpty()){
//					        ContentResolver cr = getActivity().getContentResolver();
//					        
//					        String playlistUri = "content://media/external/audio/playlists/";
//					        
//					        ContentValues cv = new ContentValues(1);
//
//							Uri urr = Uri.parse(playlistUri);
//							////cv.put(MediaStore.Audio.Playlists._ID, 1 + "");
//							cv.put(MediaStore.Audio.Playlists.NAME, input_txt.getText().toString());
//							
//							cr.insert(urr, cv);
//							PlayListE newplaylist = new PlayListE();
//							newplaylist.setPlayListName(input_txt.getText().toString());
//
//							Playlist.add(newplaylist);
//							adapter.setSoure(Playlist);
//							adapter.notifyDataSetChanged();
////							lst.setAdapter(adapter);
//							dialog.dismiss();
//							getActivity().recreate();
//						}
//						else dialog.dismiss();
//					}
//				
//				}); 
//				
//				dialogBuiler.setNegativeButton("Cancel", null);
//				
//				final Dialog dialog = dialogBuiler.create();
//
//				dialog.show();
			}
		});
		return view;
	}

}
